package com.hitmanbackend.service;

import com.hitmanbackend.entities.*;
import com.hitmanbackend.repositories.CheckpointCompletionRepository;
import com.hitmanbackend.repositories.CheckpointRepository;
import com.hitmanbackend.repositories.EliminationRepository;
import com.hitmanbackend.repositories.PlayerRepository;
import com.hitmanbackend.requests.CheckpointCompletionRequest;
import com.hitmanbackend.requests.CheckpointCreationRequest;
import com.hitmanbackend.responses.CheckpointListResponse;
import com.hitmanbackend.responses.CheckpointResponse;
import com.hitmanbackend.responses.UserCheckpoint;
import jakarta.transaction.Transactional;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CheckpointService {

    @Autowired
    CheckpointRepository checkpointRepository;
    @Autowired
    CheckpointCompletionRepository checkpointCompletionRepository;
    @Autowired
    RandomCode randomCode;
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    EliminationRepository eliminationRepository;

    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

    SimpleDateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    SimpleDateFormat startFormat = new SimpleDateFormat("yyyy-MM-dd'T'01:00");
    SimpleDateFormat endFormat = new SimpleDateFormat("yyyy-MM-dd'T'23:59");
    SimpleDateFormat outputFormat = new SimpleDateFormat("dd. MMMM yyyy '@' k:mm");
    Logger logger = LoggerFactory.getLogger(CheckpointService.class);

    public void createNewCheckpoint(CheckpointCreationRequest request) throws Exception {
        Instant nowUtc = Instant.now();
        DateTimeZone estonia = DateTimeZone.forID("Europe/Tallinn");
        DateTime nowEstonia = nowUtc.toDateTime(estonia); 
        Date now = nowEstonia.plusHours(3).toDate();
        Date checkpointDate = inputFormat.parse(request.getCheckpointDate());
        if (checkpointDate.before(now)){
            throw new Exception("Can't create checkpoint with this date. %s".formatted(
                    outputFormat.format(checkpointDate)
            ));
        }
        if (checkpointRepository.existsByCheckpointNameIgnoreCase(request.getCheckpointName())){
            throw new Exception("Duplicate checkpoint creation!");
        }

        String checkpointCode = randomCode.randomAlphanumericString(9);
        String start = startFormat.format(checkpointDate);
        String end = endFormat.format(checkpointDate);

        checkpointRepository.save(new CheckpointEntity(request.getCheckpointName(), request.getCheckpointLocation(),
                start, end, checkpointCode));

        List<PlayerDataEntity> players = playerRepository.findByRoleEqualsAndApprovedTrue("USER");
        Optional<CheckpointEntity> checkpoint = checkpointRepository.findByCheckpointCode(checkpointCode);
        for (PlayerDataEntity playerData:
             players) {

            CheckpointCompletionEntity checkpointCompletionEntity = new CheckpointCompletionEntity(checkpoint.get()
                    , playerData, false);
            checkpointCompletionRepository.save(checkpointCompletionEntity);

        }
    }

    public CheckpointListResponse getCheckpoints() throws ParseException {

        List<CheckpointEntity> checkpointEntityList = checkpointRepository.findAll();
        CheckpointListResponse checkpointListResponse = new CheckpointListResponse();

        for (CheckpointEntity entity:
             checkpointEntityList) {
            Date startTime = databaseFormat.parse(entity.getStartTime());
            Date endTime = databaseFormat.parse(entity.getEndTime());
            CheckpointResponse checkpointResponse = new CheckpointResponse(entity.getId(), entity.getCheckpointName(),
                    entity.getLocation(), outputFormat.format(startTime), outputFormat.format(endTime), entity.getCheckpointCode());
            checkpointListResponse.getCheckpoints().add(checkpointResponse);

        }
        return checkpointListResponse;

    }

    public CheckpointListResponse getMyCheckpoints(String username) throws Exception {

        Optional<PlayerDataEntity> player = playerRepository.findAccountByUsername(username);
        Instant nowUtc = Instant.now();
        DateTimeZone estonia = DateTimeZone.forID("Europe/Tallinn");
        DateTime nowEstonia = nowUtc.toDateTime(estonia);
        Date now = nowEstonia.plusHours(3).toDate();

        if (player.isPresent()){
            if (!player.get().getApproved()){
                throw new Exception("Player not approved.");
            }
            if (!player.get().getEliminated()){
                Set<CheckpointCompletionEntity> playersCheckpoints = player.get().getCheckpoints();
                CheckpointListResponse notCompletedCheckpointList = new CheckpointListResponse();
                for (CheckpointCompletionEntity checkpointCompletedEnt:
                     playersCheckpoints) {
                    CheckpointEntity checkpointData = checkpointCompletedEnt.getCheckpoint();
                    Date startTime = databaseFormat.parse(checkpointData.getStartTime());
                    Date endTime = databaseFormat.parse(checkpointData.getEndTime());
                    if (now.before(endTime) && now.after(startTime) && !checkpointCompletedEnt.getCompleted()){
                        UserCheckpoint checkpointResponse = new UserCheckpoint(checkpointData.getId(),
                                checkpointData.getCheckpointName(), checkpointData.getLocation(), outputFormat.format(startTime),
                                outputFormat.format(endTime), true);
                        notCompletedCheckpointList.getUserCheckpoints().add(checkpointResponse);
                    }
                    else if(now.before(startTime) && !checkpointCompletedEnt.getCompleted()){
                        UserCheckpoint checkpointResponse = new UserCheckpoint(checkpointData.getId(),
                                checkpointData.getCheckpointName(), "Unknown", outputFormat.format(startTime),
                                outputFormat.format(endTime), false);
                        notCompletedCheckpointList.getUserCheckpoints().add(checkpointResponse);
                    }

                }
                return notCompletedCheckpointList;

            }
            return new CheckpointListResponse();

        }
        throw new Exception("Player doesn't exist: %s".formatted(username));
    }




    public void completeCheckpoint(String subject, CheckpointCompletionRequest request) throws Exception {

        Optional<PlayerDataEntity> player = playerRepository.findAccountByUsername(subject);
        if(player.isPresent()){
            if (!player.get().getApproved()){
                throw new Exception("Player not approved.");
            }
            if (request.getCheckpointCode() == null || request.getCheckpointId() == null || request.getCheckpointCode().isBlank()){
                throw new Exception("Checkpoint completion failed. Wrong data in the request.");
            }

            for (CheckpointCompletionEntity checkpointCompEnt:
                 player.get().getCheckpoints()) {

                if (checkpointCompEnt.getCheckpoint().getCheckpointCode().equals(request.getCheckpointCode())){
                    logger.info("%s passed checkpoint %d : '%s".formatted(subject, checkpointCompEnt.getCheckpoint().getId()
                            , checkpointCompEnt.getCheckpoint().getCheckpointName()));
                    checkpointCompEnt.setCompleted(true);
                    checkpointCompletionRepository.save(checkpointCompEnt);
                    return;
                }

            }
            throw new Exception("Checkpoint completion failed");
        }
        throw new Exception("Couldn't find player data");

    }


    public void completePastCheckpointsWhenRevivingPlayer(PlayerDataEntity player, Date now) throws ParseException {
        Set<CheckpointCompletionEntity> playersCheckpoints = player.getCheckpoints();
        for (CheckpointCompletionEntity checkpointCompletedEnt:
                playersCheckpoints) {
            CheckpointEntity checkpointData = checkpointCompletedEnt.getCheckpoint();
            Date endTime = databaseFormat.parse(checkpointData.getEndTime());
            if (now.after(endTime)){
                checkpointCompletedEnt.setCompleted(true);
                checkpointCompletionRepository.save(checkpointCompletedEnt);
            }
        }
    }


}
