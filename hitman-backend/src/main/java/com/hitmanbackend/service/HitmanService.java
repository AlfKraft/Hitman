package com.hitmanbackend.service;

import com.hitmanbackend.entities.*;
import com.hitmanbackend.repositories.*;
import com.hitmanbackend.responses.EliminationDataResponse;
import com.hitmanbackend.responses.GameResponse;
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

    SimpleDateFormat compareDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    public GameResponse createANewGameCycle() throws Exception {

        logger.info("Started creating new game cycle.");
        Date now = getCurrentTime();
        eliminationRepository.deleteAll();
        logger.info("Successfully deleted previous data.");

        List<PlayerDataEntity> players = playerRepository.findByRoleEqualsAndEliminatedFalseAndApprovedTrue("USER");

        if (players.isEmpty()){
            throw new Exception("No active players found");
        }

        logger.info("Found all active players.");
        // Check who has completed checkpoints
        logger.info("Check who has completed checkpoints.");
        List<PlayerDataEntity> playersEliminatedByCheckpointNonCompletion = new ArrayList<>();
        for (PlayerDataEntity player:
             players) {

            Set<CheckpointCompletionEntity> playerCheckpoints = player.getCheckpoints();
            for (CheckpointCompletionEntity checkpoint:
                 playerCheckpoints) {
                if (checkpoint.getCompleted()){
                    continue;
                }
                Date endTime = compareDateFormat.parse(checkpoint.getCheckpoint().getEndTime());

                if (now.after(endTime)){
                    player.setEliminated(true);
                    playerRepository.save(player);
                    playersEliminatedByCheckpointNonCompletion.add(player);
                    logger.info("%s has been eliminated by not completing checkpoint: %s".formatted(player.getUsername(), checkpoint.getCheckpoint().getCheckpointName()));
                    break;
                }

            }
        }
        logger.info("Eliminate players who haven't completed checkpoints.");
        for (PlayerDataEntity eliminatedplayer:
             playersEliminatedByCheckpointNonCompletion) {
            players.remove(eliminatedplayer);

        }

        if (players.size() < 2){
            throw new Exception("Only one player left in the game");
        }
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
        logger.info("Game cycle created successfully.");
        GameResponse gameResponse = new GameResponse();
        for (EliminationEntity playerGameData:
             eliminationCodeEntities) {

            EliminationDataResponse eliminationDataResponse = new EliminationDataResponse("%s %s".formatted(
                    playerGameData.getPlayer().getFirstName(), playerGameData.getPlayer().getLastName()), playerGameData.getEliminationCode(),"%s %s".formatted(
                            playerGameData.getTarget().getFirstName(), playerGameData.getTarget().getLastName()));
            gameResponse.getGameData().add(eliminationDataResponse);
        }



        // Clear all previous bounty missions

        List<MissionEntity> missionsForDeletion = missionRepository.findAllByMissionNameContains("Bounty");
        for (MissionEntity missionForDeletion:
             missionsForDeletion) {
            missionAssignmentEntityRepository.deleteAllByMissionId(missionForDeletion.getId());
            missionRepository.delete(missionForDeletion);
        }

        return gameResponse;

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
        logger.info("%s : Started target elimination logic.".formatted(username));
        Optional<PlayerDataEntity> player = playerRepository.findAccountByUsername(username);
        if(player.isPresent()) {
            if (!player.get().getApproved()){
                logger.error("%s : Player is not approved.".formatted(username));
                throw new Exception("Player is not approved.");
            }
            Optional<EliminationEntity> eliminationEntity = eliminationRepository.findByPlayerId(player.get().getId());
            if (eliminationEntity.isPresent()) {
                logger.info("%s : Player is in the game cycle.".formatted(username));
                logger.info("%s : Entered elimination code : %s".formatted(username, eliminationCode));
                if (eliminationEntity.get().getEliminationCode().equals(eliminationCode)) {
                    logger.info("%s : Code match! Entered code: %s = %s : Game cycle code".formatted(username, eliminationCode,
                            eliminationEntity.get().getEliminationCode()));

                    // Check if player was in top bounty missions
                    Optional<MissionEntity> bountyMission = missionRepository.findByMissionCodeEquals(eliminationCode);
                    if (bountyMission.isPresent()){
                        logger.info("%s : Target '%s' has bounty on him/her".formatted(username,
                                eliminationEntity.get().getTarget().getFirstName() + " " +
                                eliminationEntity.get().getTarget().getLastName()));
                        logger.info("%s : Delete '%s' bounty".formatted(username, eliminationEntity.get().getTarget().getFirstName() + " " +
                                eliminationEntity.get().getTarget().getLastName()));
                        missionAssignmentEntityRepository.deleteAllByMissionId(bountyMission.get().getId());
                        missionRepository.delete(bountyMission.get());
                        logger.info("%s : Delete '%s' bounty completed".formatted(username, eliminationEntity.get().getTarget().getFirstName() + " " +
                                eliminationEntity.get().getTarget().getLastName()));
                    }
                    logger.info("%s : Get next target data.".formatted(username));
                    Optional<EliminationEntity> nextEliminationData = eliminationRepository.findByPlayerId(eliminationEntity
                            .get().getTarget().getId());
                    if (nextEliminationData.isPresent()) {
                        logger.info("%s : Next target data : %s %s - code : %s".formatted(username,
                                nextEliminationData.get().getTarget().getFirstName(),
                                nextEliminationData.get().getTarget().getLastName(),
                                nextEliminationData.get().getEliminationCode()));
                        Optional<ScoreEntity> score = scoreRepository.findByPlayerId(player.get().getId());
                        if(score.isPresent()){
                            logger.info("%s : Add elimination score.".formatted(username));
                            score.get().setScore(score.get().getScore() + 100);
                            logger.info("%s : New score: %d".formatted(username, score.get().getScore()));
                            scoreRepository.save(score.get());
                            logger.info("%s : Score saved.".formatted(username));
                        }
                        else {
                            logger.info("%s : New score: %d".formatted(username, 100L));
                            scoreRepository.save(new ScoreEntity(player.get(), 100L));
                            logger.info("%s : Score saved.".formatted(username));
                        }
                        PlayerDataEntity eliminated = eliminationEntity.get().getTarget();
                        eliminated.setEliminated(true);
                        playerRepository.save(eliminated);
                        logger.info("%s : %s eliminated his/her target %s with code : %s".formatted(username,player.get().getFirstName() + " " +
                                player.get().getLastName(), eliminated.getFirstName()+ " "+ eliminated.getLastName(),
                                eliminationCode));
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
                        logger.error("%s : Target %s doesn't have a next target assigned.".formatted(username, nextEliminationData.get().getPlayer().getUsername()));
                    }
                }
                else {
                    logger.error("%s : Elimination codes didn't match: Input: %s and Database: %s".formatted(username,eliminationCode, eliminationEntity.get().getEliminationCode()));
                }
            }
            else {
                logger.error("%s : Couldn't find %s's data in active game database".formatted(username, username));
            }
        }
        else {
            logger.error("%s : User not found in the database".formatted(username));
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
                playerCardData.setRank(getPlayerRank(player.get().getId()));
                playerCardData.setAliveAgents(getAliveAgents());
                return playerCardData;
            }
            if (score.isPresent()){
                return new PlayerCardData("%s %s".formatted(player.get().getFirstName(), player.get().getLastName()), ""
                        , score.get().getScore(), player.get().getEliminated(), getPlayerRank(player.get().getId()), getAliveAgents());
            }
        }
        throw new Exception("Couldn't retrieve player's data.");

    }

    private String getPlayerRank(Long id){
        List<ScoreEntity> playerLeaderBoard = scoreRepository.findAllByOrderByScoreDesc();
        int rank = 1;
        for (ScoreEntity score:
             playerLeaderBoard) {
            if (score.getPlayer().getId().equals(id)){
                StringBuilder rankToString = new StringBuilder();
                rankToString.append(rank);
                if (rankToString.toString().endsWith("11")){
                    rankToString.append("th");
                }
                else if (rankToString.toString().endsWith("12")){
                    rankToString.append("th");
                }
                else if (rankToString.toString().endsWith("13")){
                    rankToString.append("th");
                }
                else if(rankToString.toString().endsWith("3")){
                    rankToString.append("rd");
                }
                else if(rankToString.toString().endsWith("2")){
                    rankToString.append("nd");
                }
                else if(rankToString.toString().endsWith("1")){
                    rankToString.append("st");
                }
                else {
                    rankToString.append("th");
                }
                rankToString.append("/");
                long totalPlayers = playerRepository.countByApprovedTrueAndRoleEquals("USER");
                rankToString.append(totalPlayers);
                return rankToString.toString();
            }
            if(score.getPlayer().getApproved()){
                rank++;
            }

        }
        return "";
    }
    private Long getAliveAgents(){
        return playerRepository.countByApprovedTrueAndEliminatedFalseAndRoleEquals("USER");
    }

    private void eliminatePlayer(PlayerDataEntity player) {
        //Find players elimination data
        // player -> code -> target
        Optional<EliminationEntity> nextEliminationData = eliminationRepository.findByPlayerId(player.getId());
        // agent -> code - player
        Optional<EliminationEntity> backwardsData = eliminationRepository.findByTargetId(player.getId());

        if (nextEliminationData.isPresent() && backwardsData.isPresent()) {
            player.setEliminated(true);
            playerRepository.save(player);
            backwardsData.get().setEliminationCode(nextEliminationData.get().getEliminationCode());
            backwardsData.get().setTarget(nextEliminationData.get().getTarget());
            eliminationRepository.delete(nextEliminationData.get());
            eliminationRepository.save(backwardsData.get());
        }
        else {
            logger.error("Target %s doesn't have a next target assigned.".formatted(nextEliminationData.get()
                    .getPlayer().getUsername()));
        }
    }

    private Date getCurrentTime(){
        Instant nowUtc = Instant.now();
        DateTimeZone estonia = DateTimeZone.forID("Europe/Tallinn");
        DateTime nowEstonia = nowUtc.toDateTime(estonia);
        return nowEstonia.plusHours(3).toDate();
    }

    public GameResponse getGameData() {

        List<EliminationEntity> gamedataEnt = eliminationRepository.findAll();
        GameResponse gameResponse = new GameResponse();
        for (EliminationEntity ent:
             gamedataEnt) {
            gameResponse.getGameData().add(new EliminationDataResponse("%s %s".formatted(
                    ent.getPlayer().getFirstName(), ent.getPlayer().getLastName()), ent.getEliminationCode(),"%s %s".formatted(
                    ent.getTarget().getFirstName(), ent.getTarget().getLastName())));

        }
        return gameResponse;

    }

    public void createTop5BountyMissions(){
        Date now = getCurrentTime();

        // Create Top Player Bounty mission
        List<ScoreEntity> topScores = scoreRepository.findAllByOrderByScoreDesc();
        List<PlayerDataEntity> topPlayers = new ArrayList<>();
        List<Long> topPlayersIds = new ArrayList<>();
        int top5 = 0;
        for (ScoreEntity score:
                topScores) {
            if(top5 > 4){
                break;
            }
            if (!score.getPlayer().getEliminated() && score.getPlayer().getApproved() && score.getScore() > 0){
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
            List<PlayerDataEntity> players = playerRepository.findByRoleEqualsAndApprovedTrue("USER");
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
}
