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
        logger.info("%s : Get my checkpoints.".formatted(username));
        Optional<PlayerDataEntity> player = playerRepository.findAccountByUsername(username);
        Instant nowUtc = Instant.now();
        DateTimeZone estonia = DateTimeZone.forID("Europe/Tallinn");
        DateTime nowEstonia = nowUtc.toDateTime(estonia);
        Date now = nowEstonia.plusHours(3).toDate();
        if (player.isPresent()){
            if (!player.get().getApproved()){
                logger.error("%s : Player not approved.".formatted(username));
                throw new Exception("Player not approved.");
            }
            if (!player.get().getEliminated()){
                logger.info("%s : Checkpoint logic: Player eliminated: false".formatted(username));
                Set<CheckpointCompletionEntity> playersCheckpoints = player.get().getCheckpoints();
                CheckpointListResponse notCompletedCheckpointList = new CheckpointListResponse();
                logger.info("%s : Find all checkpoints".formatted(username));
                for (CheckpointCompletionEntity checkpointCompletedEnt:
                     playersCheckpoints) {
                    CheckpointEntity checkpointData = checkpointCompletedEnt.getCheckpoint();
                    Date startTime = databaseFormat.parse(checkpointData.getStartTime());
                    Date endTime = databaseFormat.parse(checkpointData.getEndTime());
                    logger.info("%s : Check checkpoint. Id : %d. Name: %s. End time: %s. Completed: %b".formatted(
                            username,
                            checkpointCompletedEnt.getCheckpoint().getId(),
                            checkpointCompletedEnt.getCheckpoint().getCheckpointName(),
                            checkpointCompletedEnt.getCheckpoint().getEndTime(),
                            checkpointCompletedEnt.getCompleted()));
                    if (now.before(endTime) && now.after(startTime) && !checkpointCompletedEnt.getCompleted()){
                        logger.info("%s : Show player the checkpoint. Id : %d. Name: %s. End time: %s. Completed: %b".formatted(
                                username,
                                checkpointCompletedEnt.getCheckpoint().getId(),
                                checkpointCompletedEnt.getCheckpoint().getCheckpointName(),
                                checkpointCompletedEnt.getCheckpoint().getEndTime(),
                                checkpointCompletedEnt.getCompleted()));
                        UserCheckpoint checkpointResponse = new UserCheckpoint(checkpointData.getId(),
                                checkpointData.getCheckpointName(), checkpointData.getLocation(), outputFormat.format(startTime),
                                outputFormat.format(endTime), true);
                        notCompletedCheckpointList.getUserCheckpoints().add(checkpointResponse);
                    }
                    else if(now.before(startTime) && !checkpointCompletedEnt.getCompleted()){
                        logger.info("%s : Show player partly hidden checkpoint. Id : %d. Name: %s. End time: %s. Completed: %b".formatted(
                                username,
                                checkpointCompletedEnt.getCheckpoint().getId(),
                                checkpointCompletedEnt.getCheckpoint().getCheckpointName(),
                                checkpointCompletedEnt.getCheckpoint().getEndTime(),
                                checkpointCompletedEnt.getCompleted()));
                        UserCheckpoint checkpointResponse = new UserCheckpoint(checkpointData.getId(),
                                checkpointData.getCheckpointName(), "Unknown", outputFormat.format(startTime),
                                outputFormat.format(endTime), false);
                        notCompletedCheckpointList.getUserCheckpoints().add(checkpointResponse);
                    }

                }
                return notCompletedCheckpointList;

            }
            logger.info("%s : Checkpoint logic: Player eliminated: true".formatted(username));
            return new CheckpointListResponse();

        }
        throw new Exception("Player doesn't exist: %s".formatted(username));
    }




    public void completeCheckpoint(String subject, CheckpointCompletionRequest request) throws Exception {

        logger.info("%s : Start checkpoint completion logic.".formatted(subject));
        logger.info("%s : Code entered: %s".formatted(subject, request.getCheckpointCode()));
        Optional<PlayerDataEntity> player = playerRepository.findAccountByUsername(subject);
        if(player.isPresent()){
            if (!player.get().getApproved()){
                logger.info("%s : Player not approved.".formatted(subject));
                throw new Exception("Player not approved.");
            }
            if (request.getCheckpointCode() == null || request.getCheckpointId() == null || request.getCheckpointCode().isBlank()){
                logger.info("%s : Checkpoint completion failed. Wrong data in the request.".formatted(subject));
                throw new Exception("Checkpoint completion failed. Wrong data in the request.");
            }
            logger.info("%s : Find checkpoint by id. Id : %d".formatted(subject, request.getCheckpointId()));
            for (CheckpointCompletionEntity checkpointCompEnt:
                 player.get().getCheckpoints()) {

                if (checkpointCompEnt.getCheckpoint().getCheckpointCode().equals(request.getCheckpointCode())){
                    logger.info("%s passed checkpoint %d : '%s".formatted(subject, checkpointCompEnt.getCheckpoint().getId()
                            , checkpointCompEnt.getCheckpoint().getCheckpointName()));
                    checkpointCompEnt.setCompleted(true);
                    checkpointCompletionRepository.save(checkpointCompEnt);
                    logger.info("%s : Completed checkpoint completion logic: SUCCESS".formatted(subject));
                    return;
                }

            }
            logger.info("%s : Completed checkpoint completion logic: FAIL".formatted(subject));
            logger.error("%s : Checkpoint completion failed.".formatted(subject));
            throw new Exception("Checkpoint completion failed.");
        }
        logger.info("%s : Completed checkpoint completion logic: FAIL".formatted(subject));
        logger.error("%s : Couldn't find player data.".formatted(subject));
        throw new Exception("Couldn't find player data.");

    }


    public void completePastCheckpointsWhenRevivingPlayer(PlayerDataEntity player, Date now) throws ParseException {
        logger.info("%s : Start complete past checkpoints during revival logic.".formatted(player.getUsername()));
        Set<CheckpointCompletionEntity> playersCheckpoints = player.getCheckpoints();
        for (CheckpointCompletionEntity checkpointCompletedEnt:
                playersCheckpoints) {
            CheckpointEntity checkpointData = checkpointCompletedEnt.getCheckpoint();
            Date endTime = databaseFormat.parse(checkpointData.getEndTime());
            logger.info("%s : Check checkpoint. Id : %d. Name: %s. End time: %s. Completed: %b".formatted(player.getUsername(),
                    checkpointCompletedEnt.getCheckpoint().getId(),
                    checkpointCompletedEnt.getCheckpoint().getCheckpointName(),
                    checkpointCompletedEnt.getCheckpoint().getEndTime(),
                    checkpointCompletedEnt.getCompleted()));
            if (now.after(endTime)){
                logger.info("%s : Complete checkpoint. Id : %d. Name: %s".formatted(player.getUsername(),
                        checkpointCompletedEnt.getCheckpoint().getId(),
                        checkpointCompletedEnt.getCheckpoint().getCheckpointName()));
                checkpointCompletedEnt.setCompleted(true);
                checkpointCompletionRepository.save(checkpointCompletedEnt);
                logger.info("%s : Successful checkpoint completion. Id : %d. Name: %s".formatted(player.getUsername(),
                        checkpointCompletedEnt.getCheckpoint().getId(),
                        checkpointCompletedEnt.getCheckpoint().getCheckpointName()));
            }
        }
    }


}
