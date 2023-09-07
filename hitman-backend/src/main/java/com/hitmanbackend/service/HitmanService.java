package com.hitmanbackend.service;

import com.hitmanbackend.controllers.UserController;
import com.hitmanbackend.entities.EliminationEntity;
import com.hitmanbackend.entities.ScoreEntity;
import com.hitmanbackend.entities.TestAccountEntity;
import com.hitmanbackend.repositories.EliminationRepository;
import com.hitmanbackend.repositories.ScoreRepository;
import com.hitmanbackend.repositories.TestAccountRepository;
import com.hitmanbackend.responses.PlayerCardData;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class HitmanService {

    @Autowired
    EliminationRepository eliminationRepository;

    @Autowired
    TestAccountRepository testAccountRepository;
    @Autowired
    RandomCode eliminationCode;

    @Autowired
    ScoreRepository scoreRepository;

    Logger logger = LoggerFactory.getLogger(HitmanService.class);


    public void createANewGameCycle(){
        eliminationRepository.deleteAll();

        List<TestAccountEntity> players = testAccountRepository.findByRoleEqualsAndEliminatedFalse("USER");
        if (players.isEmpty() || players.size() <= 5){
            return;
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
            Optional<TestAccountEntity> player = testAccountRepository.findById(link.getKey());
            Optional<TestAccountEntity> target = testAccountRepository.findById(link.getValue());

            if (player.isPresent() && target.isPresent()){
                EliminationEntity eliminationCodeEntity = new EliminationEntity(player.get(),code,target.get());
                eliminationCodeEntities.add(eliminationCodeEntity);
            }
        }

        eliminationRepository.saveAll(eliminationCodeEntities);
    }

    public Target getPlayerTarget(String username) throws Exception {
        Optional<TestAccountEntity> player = testAccountRepository.findAccountByUsername(username);
        if (player.isPresent()){
            Optional<EliminationEntity> eliminationInfo = eliminationRepository.findByPlayerId(player.get().getId());
            if (eliminationInfo.isPresent()){
                return new Target(eliminationInfo.get().getTarget().getName(), eliminationInfo.get().getTarget().getAboutInfo());

            }
        }
        throw new Exception("Couldn't find target data");
    }

    public Target eliminateTarget(String username, String eliminationCode) throws Exception {
        Optional<TestAccountEntity> player = testAccountRepository.findAccountByUsername(username);
        if(player.isPresent()) {
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
                        TestAccountEntity eliminated = eliminationEntity.get().getTarget();
                        eliminated.setEliminated(true);
                        testAccountRepository.save(eliminated);
                        eliminationEntity.get().setEliminationCode(nextEliminationData.get().getEliminationCode());
                        eliminationEntity.get().setTarget(nextEliminationData.get().getTarget());
                        eliminationRepository.save(eliminationEntity.get());
                        eliminationRepository.delete(nextEliminationData.get());
                        return new Target(eliminationEntity.get().getTarget().getName(), eliminationEntity.get()
                                .getTarget().getAboutInfo());
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
        List<TestAccountEntity> allPlayers = testAccountRepository.findAllByRoleEquals("USER");

        for (TestAccountEntity account:
             allPlayers) {
            scoreRepository.save(new ScoreEntity(account, 0L));
        }
    }

    public PlayerCardData getPlayerCardData(String username) throws Exception {
        PlayerCardData playerCardData = new PlayerCardData();
        Optional<TestAccountEntity> player = testAccountRepository.findAccountByUsername(username);
        if(player.isPresent()){
            Optional<EliminationEntity> eliminationData = eliminationRepository.findByTargetId(player.get().getId());
            Optional<ScoreEntity> score = scoreRepository.findByPlayerId(player.get().getId());
            if (eliminationData.isPresent() && score.isPresent()){
                playerCardData.setName(player.get().getName());
                playerCardData.setEliminationCode(eliminationData.get().getEliminationCode());
                playerCardData.setScore(score.get().getScore());
                return playerCardData;
            }
            if (score.isPresent()){
                return new PlayerCardData(player.get().getName(), "", score.get().getScore());
            }
        }
        throw new Exception("Couldn't retrieve player's data.");


    }

}
