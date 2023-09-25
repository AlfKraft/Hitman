package com.hitmanbackend.service;

import com.hitmanbackend.controllers.UserController;
import com.hitmanbackend.entities.*;
import com.hitmanbackend.repositories.*;
import com.hitmanbackend.requests.MissionCreationRequest;
import com.hitmanbackend.responses.MissionResponse;
import com.hitmanbackend.responses.MissionsResponse;
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
import java.util.*;

@Service
@Transactional
public class MissionService {

    @Autowired
    MissionRepository missionRepository;
    @Autowired
    ScoreRepository scoreRepository;
    @Autowired
    MissionAssignmentEntityRepository missionAssignmentEntityRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    RandomCode missionCode;
    @Autowired
    EliminationRepository eliminationRepository;

    @Autowired
    CheckpointService checkpointService;
    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    SimpleDateFormat outputFormat = new SimpleDateFormat("dd. MMMM yyyy '@' k:mm");

    Logger logger = LoggerFactory.getLogger(MissionService.class);

    public void createNewMission(MissionCreationRequest request) throws Exception {
        Instant nowUtc = Instant.now();
        DateTimeZone estonia = DateTimeZone.forID("Europe/Tallinn");
        DateTime nowEstonia = nowUtc.toDateTime(estonia);
        Date now = nowEstonia.plusHours(3).toDate();
        Date startTime = inputFormat.parse(request.getStartDateTime());
        Date endTime = inputFormat.parse(request.getEndDateTime());

        if (startTime.before(now) || startTime.after(endTime) || endTime.before(now)){
            throw new Exception("Can't create mission with these dates. Start: %s, End: %s".formatted(request
                    .getStartDateTime(), request.getEndDateTime()));
        }
        if (missionRepository.existsByMissionName(request.getMissionName())){
            throw new Exception("Duplicate mission name.");
        }

        if (request.getMissionCompletionCount() == null){
            request.setMissionCompletionCount(0L);
        }
        MissionEntity newMissionEntity = new MissionEntity(request.getMissionName(), request.getMissionInfo(),
                request.getStartDateTime(),request.getEndDateTime(),request.getMissionLocation(), request.getMissionCode()
                , Long.parseLong(request.getPoints()), request.getForEliminated(), request.getMissionCompletionCount());
        missionRepository.save(newMissionEntity);

        Optional<MissionEntity> missionEntity = missionRepository.findByMissionName(request.getMissionName());
        if(missionEntity.isPresent()){
            List<PlayerDataEntity> players = playerRepository.findByRoleEqualsAndApprovedTrue("USER");
            for (PlayerDataEntity player:
                 players) {
                missionAssignmentEntityRepository.save(new MissionAssignmentEntity(missionEntity.get(), player,
                        false));
            }

        }

    }


    public MissionsResponse getAllMissions() throws ParseException {
        MissionsResponse missionsResponse = new MissionsResponse(new ArrayList<>());
        List<MissionEntity> missionEntities = missionRepository.findAll();

        for (MissionEntity mission:
             missionEntities) {

            Date startTime = inputFormat.parse(mission.getStartTime());
            Date endTime = inputFormat.parse(mission.getEndTime());

            MissionResponse missionResponse = new MissionResponse();
            missionResponse.setId(mission.getId());
            missionResponse.setMissionName(mission.getMissionName());
            missionResponse.setMissionInfo(mission.getMissionInfo());
            missionResponse.setStartTime(outputFormat.format(startTime));
            missionResponse.setEndTime(outputFormat.format(endTime));
            missionResponse.setLocation(mission.getLocation());
            missionResponse.setPoints(mission.getPoints());
            missionResponse.setForEliminated(mission.getForEliminated());
            missionResponse.setMissionCompletionCount(mission.getMissionCompletionCount());
            missionResponse.setActive(true);
            missionResponse.setMissionCode(mission.getMissionCode());
            missionsResponse.getMissions().add(missionResponse);
        }
        return missionsResponse;
    }



    public MissionsResponse getMyMissions(String username) throws Exception {
        Instant nowUtc = Instant.now();
        DateTimeZone estonia = DateTimeZone.forID("Europe/Tallinn");
        DateTime nowEstonia = nowUtc.toDateTime(estonia);
        Date now = nowEstonia.plusHours(3).toDate();
        logger.info("%s queried missions at %s".formatted(username, outputFormat.format(now)));
        Optional<PlayerDataEntity> player = playerRepository.findAccountByUsername(username);
        MissionsResponse missionsResponse = new MissionsResponse();

        if (player.isPresent()){
            if (!player.get().getApproved()){
                throw new Exception("Player not approved.");
            }
            for (MissionAssignmentEntity mission:
                 player.get().getMissions()) {
                MissionResponse missionResponse = new MissionResponse();
                if(!mission.getCompleted()){

                    if (mission.getMission().getMissionCompletionCount() > 0){
                        long missionCompletedCount = missionAssignmentEntityRepository.countByMissionIdAndCompleted(
                                mission.getMission().getId(), true);
                        if (mission.getMission().getMissionCompletionCount() <= missionCompletedCount){
                            continue;
                        }
                    }

                    if(mission.getMission().getForEliminated() == player.get().getEliminated()) {
                        Date startTime = inputFormat.parse(mission.getMission().getStartTime());
                        Date endTime = inputFormat.parse(mission.getMission().getEndTime());

                        if (now.after(startTime) && now.before(endTime)) {
                            missionResponse.setId(mission.getMission().getId());
                            missionResponse.setMissionName(mission.getMission().getMissionName());
                            missionResponse.setMissionInfo(mission.getMission().getMissionInfo());
                            missionResponse.setStartTime(outputFormat.format(startTime));
                            missionResponse.setEndTime(outputFormat.format(endTime));
                            missionResponse.setLocation(mission.getMission().getLocation());
                            missionResponse.setPoints(mission.getMission().getPoints());
                            missionResponse.setForEliminated(mission.getMission().getForEliminated());

                            missionResponse.setActive(true);

                            missionsResponse.getMissions().add(missionResponse);
                        } else if (now.before(startTime)) {
                            missionResponse.setId(mission.getMission().getId());
                            missionResponse.setMissionName(mission.getMission().getMissionName());
                            missionResponse.setMissionInfo("");
                            missionResponse.setStartTime(outputFormat.format(startTime));
                            missionResponse.setEndTime(outputFormat.format(endTime));
                            missionResponse.setLocation("");
                            missionResponse.setPoints(mission.getMission().getPoints());
                            missionResponse.setForEliminated(mission.getMission().getForEliminated());
                            missionResponse.setActive(false);

                            missionsResponse.getMissions().add(missionResponse);
                        }
                    }
                }
                else{
                    missionResponse.setId(mission.getMission().getId());
                    missionResponse.setMissionName(mission.getMission().getMissionName());
                    missionResponse.setPoints(mission.getMission().getPoints());
                    missionsResponse.getCompletedMissions().add(missionResponse);
                }

            }
        }
        return missionsResponse;
    }

    public void completeMission(String username, Long missionId, String missionCode) throws Exception {
        Optional<PlayerDataEntity> player = playerRepository.findAccountByUsername(username);
        Instant nowUtc = Instant.now();
        DateTimeZone estonia = DateTimeZone.forID("Europe/Tallinn");
        DateTime nowEstonia = nowUtc.toDateTime(estonia);
        Date now = nowEstonia.plusHours(3).toDate();

        if (player.isPresent()){
            if (!player.get().getApproved()){
                throw new Exception("Player not approved.");
            }
            Optional<MissionAssignmentEntity> missionAssignment = missionAssignmentEntityRepository.findByMissionIdAndPlayerId(missionId, player.get().getId());
            if (missionAssignment.isPresent() && !missionAssignment.get().getCompleted()) {
                Date startTime = inputFormat.parse(missionAssignment.get().getMission().getStartTime());
                Date endTime = inputFormat.parse(missionAssignment.get().getMission().getEndTime());

                if (missionAssignment.get().getMission().getMissionCompletionCount() > 0) {
                    long missionCompletedCount = missionAssignmentEntityRepository.countByMissionIdAndCompleted(missionId, true);
                    if (missionAssignment.get().getMission().getMissionCompletionCount() <= missionCompletedCount) {
                        throw new Exception("Mission can't be completed. Count limit reached.");
                    }
                }
                if (now.after(startTime) && now.before(endTime)) {
                    if (missionAssignment.get().getMission().getMissionCode().equals(missionCode)) {
                        if (missionAssignment.get().getMission().getForEliminated() && player.get().getEliminated()) {
                            // If is bounty mission. Revive player and take points of top player
                            if (missionAssignment.get().getMission().getMissionName().contains("Bounty")){
                                Optional<EliminationEntity> gameData = eliminationRepository.findByEliminationCode(missionCode);
                                if (gameData.isPresent()){
                                    PlayerDataEntity topPlayer = gameData.get().getTarget();
                                    ScoreEntity topPlayerScore = topPlayer.getScoreEntity();
                                    if (topPlayerScore.getScore() >= 150){
                                        topPlayerScore.setScore(topPlayerScore.getScore() - 150);
                                        scoreRepository.save(topPlayerScore);
                                    }
                                    else {
                                        topPlayerScore.setScore(0L);
                                        scoreRepository.save(topPlayerScore);
                                    }
                                }
                            }
                            player.get().setEliminated(false);
                            playerRepository.save(player.get());
                            checkpointService.completePastCheckpointsWhenRevivingPlayer(player.get(), now);
                        } else {
                            Optional<ScoreEntity> score = scoreRepository.findByPlayerId(player.get().getId());
                            if (score.isPresent()) {
                                score.get().setScore(score.get().getScore() + missionAssignment.get().getMission().getPoints());
                                scoreRepository.save(score.get());
                            } else {
                                scoreRepository.save(new ScoreEntity(player.get(), missionAssignment.get().getMission().getPoints()));
                            }
                        }
                        missionAssignment.get().setCompleted(true);
                        missionAssignmentEntityRepository.save(missionAssignment.get());
                    }
                    else {
                        throw new Exception("Wrong mission code.");
                    }
                }
                else {
                    throw new Exception("Mission can't be completed at this time.");
                }
                }
            throw new Exception("You can't complete this mission");
            }



        }

    }


