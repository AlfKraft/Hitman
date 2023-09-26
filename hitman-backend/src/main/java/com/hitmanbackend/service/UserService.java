package com.hitmanbackend.service;

import com.hitmanbackend.dto.Leader;
import com.hitmanbackend.dto.User;
import com.hitmanbackend.entities.*;
import com.hitmanbackend.repositories.*;
import com.hitmanbackend.requests.PlayerIdRequest;
import com.hitmanbackend.responses.PlayerCardDataForAdmin;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class UserService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    EliminationRepository eliminationRepository;

    @Autowired
    CheckpointService checkpointService;

    @Autowired
    UploadObject uploadObject;

    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat outputFormat = new SimpleDateFormat("dd. MMMM yyyy");

    Logger logger = LoggerFactory.getLogger(UserService.class);
    public List<User> getAllPlayers() {

        List<User> players = new ArrayList<>();
        List<PlayerDataEntity> users = playerRepository.findAllByRoleEquals("USER");

        for (PlayerDataEntity entity:
             users) {
            Long points = 0L;
            if(entity.getScoreEntity() != null){
                points = entity.getScoreEntity().getScore();
            }
            players.add(new User(entity.getId(), entity.getUsername(),"%s %s".formatted(entity.getFirstName()
                    , entity.getLastName()), points, entity.getEliminated(), entity.getEmail(), entity.getPhoneNumber()));
        }

        return players;

    }

    public void eliminatePlayer(PlayerIdRequest request) throws Exception {
        logger.info("Admin : Start player eliminate logic.");
        Optional<PlayerDataEntity> player = playerRepository.findById(request.getId());
        if (player.isPresent()){
            if (player.get().getEliminated()){
                throw new Exception("Player is already eliminated.");
            }
            logger.info("Admin : Find player by id : %d.".formatted(request.getId()));
            Optional<EliminationEntity> gameData = eliminationRepository.findByPlayerId(player.get().getId());
            if(gameData.isPresent()){
                logger.info("Admin : Player found. Id: %d. Name: %s %s".formatted(request.getId(),
                        player.get().getFirstName(), player.get().getLastName()));
                logger.info("Admin : Start eliminating player from game cycle.");
                logger.info("Admin : Find player who is targeting %s %s.".formatted(player.get().getFirstName(),
                        player.get().getLastName()));
                Optional<EliminationEntity> gameDataOfWhoWasPlayerTargetBy = eliminationRepository.findByTargetId(player
                        .get().getId());
                if (gameDataOfWhoWasPlayerTargetBy.isPresent()){
                    logger.info("Admin : %s %s is targeting %s %s".formatted(
                            gameDataOfWhoWasPlayerTargetBy.get().getPlayer().getFirstName(),
                            gameDataOfWhoWasPlayerTargetBy.get().getPlayer().getLastName(),
                            gameDataOfWhoWasPlayerTargetBy.get().getTarget().getFirstName(),
                            gameDataOfWhoWasPlayerTargetBy.get().getTarget().getLastName()));
                    logger.info("Admin : Set new target for %s %s".formatted(
                            gameDataOfWhoWasPlayerTargetBy.get().getPlayer().getFirstName(),
                            gameDataOfWhoWasPlayerTargetBy.get().getPlayer().getLastName()
                    ));
                    gameDataOfWhoWasPlayerTargetBy.get().setEliminationCode(gameData.get().getEliminationCode());
                    gameDataOfWhoWasPlayerTargetBy.get().setTarget(gameData.get().getTarget());
                    logger.info("Admin : %s %s's new target is %s %s. Elimination code: %s".formatted(
                            gameDataOfWhoWasPlayerTargetBy.get().getPlayer().getFirstName(),
                            gameDataOfWhoWasPlayerTargetBy.get().getPlayer().getLastName(),
                            gameData.get().getTarget().getFirstName(),
                            gameData.get().getTarget().getLastName(),
                            gameData.get().getEliminationCode()
                    ));
                    eliminationRepository.save(gameDataOfWhoWasPlayerTargetBy.get());
                    eliminationRepository.delete(gameData.get());
                    logger.info("Admin : Completed player eliminate logic.");
                }
                else {
                    throw new Exception("Someone should be targeting user: %s".formatted(player.get().getUsername()));
                }
            }
            player.get().setEliminated(true);
            playerRepository.save(player.get());
            return;
        }
        throw new Exception("Couldn't find player form database");
    }

    public void revivePlayer(PlayerIdRequest request) throws Exception {
        logger.info("Admin : Start player revive logic.");
        Instant nowUtc = Instant.now();
        DateTimeZone estonia = DateTimeZone.forID("Europe/Tallinn");
        DateTime nowEstonia = nowUtc.toDateTime(estonia);
        Date now = nowEstonia.plusHours(3).toDate();
        logger.info("Admin : Find player by id : %d.".formatted(request.getId()));
        Optional<PlayerDataEntity> player = playerRepository.findById(request.getId());
        if (player.isPresent()){
            logger.info("Admin : Player found. Id: %d. Name: %s %s".formatted(request.getId(),
                    player.get().getFirstName(), player.get().getLastName()));
            if (!player.get().getEliminated()){
                logger.error("Admin : Player id: %d. Can't revive already alive player.".formatted(request.getId()));
                throw new Exception("Player is alive.");
            }
            logger.info("Admin : Revive player. Id: %d".formatted(request.getId()));
            player.get().setEliminated(false);

            //Checkpoint check
            checkpointService.completePastCheckpointsWhenRevivingPlayer(player.get(), now);
            logger.info("Admin : Past checkpoints check completed.");
            playerRepository.save(player.get());
            logger.info("Admin : Completed player revive logic.");
            return;
        }
        throw new Exception("Couldn't find player form database");
    }

    public List<Leader> getTopPlayers(){
        List<ScoreEntity> scores = scoreRepository.findTop5ByOrderByScoreDesc();
        List<Leader> leaders = new ArrayList<>();
        Long rank = 1L;
        for (ScoreEntity score:
             scores) {
            if(score.getScore() > 0){
                leaders.add(new Leader(rank, "%s %s".formatted(score.getPlayer().getFirstName(),
                        score.getPlayer().getLastName()),score.getScore()));
                rank++;
            }
        }
        return leaders;
    }

    public PlayerCardDataForAdmin getPlayerData(PlayerIdRequest request) throws Exception {
        Optional<PlayerDataEntity> player = playerRepository.findById(request.getId());

        if(player.isPresent()){
            String name = player.get().getFirstName() + " " + player.get().getLastName();
            Date birthdate = inputFormat.parse(player.get().getBirthdate());
            String birthDateFormatted = outputFormat.format(birthdate);
            return new PlayerCardDataForAdmin(name,player.get().getProfileImage(),player.get().getEmail(),
                    birthDateFormatted, player.get().getFacebook(), player.get().getSchoolAndSpeciality(),
                    player.get().getWorkPlace(), player.get().getHobbies(), player.get().getFavoritePlaces(),
                    player.get().getPhoneNumber(), player.get().getApproved(), player.get().getEliminated());
        }
        throw new Exception("Couldn't find player's data.");



    }

    public void generateNewURLsForPlayersImages(){
        List<PlayerDataEntity> users = playerRepository.findAllByRoleEquals("USER");
        for (PlayerDataEntity player:
             users) {
            String newURL = uploadObject.generateNewUrlForPlayer(player.getFirstName(), player.getLastName());
            player.setProfileImage(newURL);
        }
        playerRepository.saveAll(users);

    }
}
