package com.hitmanbackend.service;

import com.hitmanbackend.controllers.UserController;
import com.hitmanbackend.entities.*;
import com.hitmanbackend.repositories.*;
import com.hitmanbackend.responses.PlayerCardData;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class HitmanService {

    @Autowired
    EliminationRepository eliminationRepository;

    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    RandomCode eliminationCode;

    @Autowired
    ScoreRepository scoreRepository;
    @Autowired
    MissionRepository missionRepository;
    @Autowired
    MissionAssignmentEntityRepository missionAssignmentEntityRepository;

    Logger logger = LoggerFactory.getLogger(HitmanService.class);

    SimpleDateFormat startFormat = new SimpleDateFormat("yyyy-MM-dd'T'01:00");
    SimpleDateFormat endFormat = new SimpleDateFormat("yyyy-MM-dd'T'23:59");

    public void createANewGameCycle(){
        eliminationRepository.deleteAll();

        List<PlayerDataEntity> players = playerRepository.findByRoleEqualsAndEliminatedFalseAndApprovedTrue("USER");
        if (players.isEmpty() || players.size() < 3){
            return;
        }
        Instant nowUtc = Instant.now();
        DateTimeZone estonia = DateTimeZone.forID("Europe/Tallinn");
        DateTime nowEstonia = nowUtc.toDateTime(estonia);
        Date now = nowEstonia.plusHours(3).toDate();
        List<String> eliminationCodes = new ArrayList<>();
        List<EliminationEntity> eliminationCodeEntities = new ArrayList<>();


        Random random = new Random();
        Stack<Long> targetCodes = new Stack<>();
        int maxIteration = players.size();

        while (maxIteration > 0){
            int randomInt = random.nextInt(maxIteration);
            targetCodes.push(players.get(randomInt).getId());
            players.remove(randomInt);
            maxIteration--;
        }
        Map<Long, Long> targetCycleMap = new HashMap<>();

        Long firstCode = targetCodes.peek();
        while (!targetCodes.empty()){
            if (targetCodes.size() == 1){
                targetCycleMap.put(targetCodes.pop(), firstCode);
                break;
            }
            targetCycleMap.put(targetCodes.pop(), targetCodes.peek());
        }


        for (Map.Entry<Long,Long> link: targetCycleMap.entrySet()) {
            String code = "";
            while (true){
                code = eliminationCode.randomAlphanumericString(9);
                if (!eliminationCodes.contains(code)){
                    eliminationCodes.add(code);
                    break;
                }
            }
            Optional<PlayerDataEntity> player = playerRepository.findById(link.getKey());
            Optional<PlayerDataEntity> target = playerRepository.findById(link.getValue());

            if (player.isPresent() && target.isPresent()){
                EliminationEntity eliminationCodeEntity = new EliminationEntity(player.get(),code,target.get());
                eliminationCodeEntities.add(eliminationCodeEntity);
            }
        }

        eliminationRepository.saveAll(eliminationCodeEntities);

        // Clear all previous bounty missions

        List<MissionEntity> missionsForDeletion = missionRepository.findAllByMissionNameContains("Bounty");
        for (MissionEntity missionForDeletion:
             missionsForDeletion) {
            missionAssignmentEntityRepository.deleteAllByMissionId(missionForDeletion.getId());
            missionRepository.delete(missionForDeletion);
        }

        // Create Top Player Bounty mission
        List<ScoreEntity> top10Scores = scoreRepository.findAllByOrderByScoreDesc();
        List<PlayerDataEntity> topPlayers = new ArrayList<>();
        List<Long> topPlayersIds = new ArrayList<>();
        int top5 = 0;
        for (ScoreEntity score:
             top10Scores) {
            if(top5 > 4){
                break;
            }
            if (!score.getPlayer().getEliminated()){
                top5++;
                topPlayers.add(score.getPlayer());
                topPlayersIds.add(score.getPlayer().getId());
            }
        }

        int topCount = 1;
        for (PlayerDataEntity topPlayer:
             topPlayers) {
            MissionEntity missionEntity = new MissionEntity();
            missionEntity.setForEliminated(true);
            missionEntity.setMissionName("Bounty on top %d".formatted(topCount));
            missionEntity.setMissionInfo("Find %s. If you are the first to eliminate this target. You will get back in to the game."
                    .formatted(topPlayer.getFirstName() + " " + topPlayer.getLastName()));
            missionEntity.setLocation("Tartu");
            missionEntity.setMissionCompletionCount(1L);
            String startTime = startFormat.format(now);
            String endTime = endFormat.format(now);
            missionEntity.setStartTime(startTime);
            missionEntity.setEndTime(endTime);
            missionEntity.setPoints(0L);
            Optional<EliminationEntity> eliminationData = eliminationRepository.findByTargetId(topPlayer.getId());
            eliminationData.ifPresent(eliminationEntity -> missionEntity.setMissionCode(eliminationEntity.getEliminationCode()));
            missionRepository.save(missionEntity);
            Optional<MissionEntity> justCreatedMission = missionRepository.findFirstByOrderByIdDesc();
            players = playerRepository.findByRoleEqualsAndApprovedTrue("USER");
            for (PlayerDataEntity playerToAssignMission:
                    players) {
                if (!topPlayersIds.contains(playerToAssignMission.getId())){
                    missionAssignmentEntityRepository.save(new MissionAssignmentEntity(justCreatedMission.get(), playerToAssignMission,
                            false));
                }

            }
            topCount++;

        }

    }

    public Target getPlayerTarget(String username) throws Exception {
        Optional<PlayerDataEntity> player = playerRepository.findAccountByUsername(username);
        if (player.isPresent()){
            if (!player.get().getApproved()){
                throw new Exception("Player not approved.");
            }

            Optional<EliminationEntity> eliminationInfo = eliminationRepository.findByPlayerId(player.get().getId());
            if (eliminationInfo.isPresent()){
                return new Target("%s %s".formatted(eliminationInfo.get().getTarget().getFirstName(), eliminationInfo.get().getTarget().getLastName()),
                        eliminationInfo.get().getTarget().getFacebook(),
                        eliminationInfo.get().getTarget().getSchoolAndSpeciality(),
                        eliminationInfo.get().getTarget().getWorkPlace(),
                        eliminationInfo.get().getTarget().getHobbies(),
                        eliminationInfo.get().getTarget().getFavoritePlaces()
                        ,eliminationInfo.get().getTarget().getProfileImage());

            }
        }
        throw new Exception("Couldn't find target data");
    }

    public Target eliminateTarget(String username, String eliminationCode) throws Exception {
        Optional<PlayerDataEntity> player = playerRepository.findAccountByUsername(username);
        if(player.isPresent()) {
            if (!player.get().getApproved()){
                throw new Exception("Player not approved.");
            }
            Optional<EliminationEntity> eliminationEntity = eliminationRepository.findByPlayerId(player.get().getId());
            if (eliminationEntity.isPresent()) {
                if (eliminationEntity.get().getEliminationCode().equals(eliminationCode)) {
                    Optional<EliminationEntity> nextEliminationData = eliminationRepository.findByPlayerId(eliminationEntity
                            .get().getTarget().getId());
                    if (nextEliminationData.isPresent()) {
                        Optional<ScoreEntity> score = scoreRepository.findByPlayerId(player.get().getId());
                        if(score.isPresent()){
                            score.get().setScore(score.get().getScore() + 100);
                            scoreRepository.save(score.get());
                        }
                        else {
                            scoreRepository.save(new ScoreEntity(player.get(), 100L));
                        }
                        PlayerDataEntity eliminated = eliminationEntity.get().getTarget();
                        eliminated.setEliminated(true);
                        playerRepository.save(eliminated);
                        eliminationEntity.get().setEliminationCode(nextEliminationData.get().getEliminationCode());
                        eliminationEntity.get().setTarget(nextEliminationData.get().getTarget());
                        eliminationRepository.save(eliminationEntity.get());
                        eliminationRepository.delete(nextEliminationData.get());
                        return new Target("%s %s".formatted(eliminationEntity.get().getTarget().getFirstName(), eliminationEntity.get().getTarget().getLastName()),
                                eliminationEntity.get().getTarget().getFacebook(),
                                eliminationEntity.get().getTarget().getSchoolAndSpeciality(),
                                eliminationEntity.get().getTarget().getWorkPlace(),
                                eliminationEntity.get().getTarget().getHobbies(),
                                eliminationEntity.get().getTarget().getFavoritePlaces()
                                ,eliminationEntity.get().getTarget().getProfileImage());
                    }
                    else {
                        logger.error("Target %s doesn't have a next target assigned.".formatted(nextEliminationData.get().getPlayer().getUsername()));
                    }

                }
                else {
                    logger.error("Elimination codes didn't match: Input: %s and Database: %s".formatted(eliminationCode, eliminationEntity.get().getEliminationCode()));
                }
            }
            else {
                logger.error("Couldn't find %s's data in active game database".formatted(username));
            }
        }
        else {
            logger.error("%s was not found in the database".formatted(username));
        }

        throw new Exception("Elimination failed!");

    }

    public void nullifyAllScores(){
        List<PlayerDataEntity> allPlayers = playerRepository.findAllByRoleEquals("USER");

        for (PlayerDataEntity account:
             allPlayers) {
            scoreRepository.save(new ScoreEntity(account, 0L));
        }
    }

    public PlayerCardData getPlayerCardData(String username) throws Exception {
        PlayerCardData playerCardData = new PlayerCardData();
        Optional<PlayerDataEntity> player = playerRepository.findAccountByUsername(username);
        if(player.isPresent()){
            if (!player.get().getApproved()){
                throw new Exception("Player not approved.");
            }
            Optional<EliminationEntity> eliminationData = eliminationRepository.findByTargetId(player.get().getId());
            Optional<ScoreEntity> score = scoreRepository.findByPlayerId(player.get().getId());
            if (eliminationData.isPresent() && score.isPresent()){
                playerCardData.setName("%s %s".formatted(player.get().getFirstName(), player.get().getLastName()));
                playerCardData.setEliminationCode(eliminationData.get().getEliminationCode());
                playerCardData.setScore(score.get().getScore());
                playerCardData.setEliminated(player.get().getEliminated());
                return playerCardData;
            }
            if (score.isPresent()){
                return new PlayerCardData("%s %s".formatted(player.get().getFirstName(), player.get().getLastName()), "", score.get().getScore(), player.get().getEliminated());
            }
        }
        throw new Exception("Couldn't retrieve player's data.");

    }

}
