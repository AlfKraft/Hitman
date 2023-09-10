package com.hitmanbackend.service;

import com.hitmanbackend.dto.Leader;
import com.hitmanbackend.dto.User;
import com.hitmanbackend.entities.*;
import com.hitmanbackend.repositories.*;
import com.hitmanbackend.requests.PlayerIdRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    TestAccountRepository testAccountRepository;

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    EliminationRepository eliminationRepository;


    public List<User> getAllPlayers() {

        List<User> players = new ArrayList<>();
        List<TestAccountEntity> users = testAccountRepository.findAllByRoleEquals("USER");

        for (TestAccountEntity entity:
             users) {
            Long points = 0L;
            if(entity.getScoreEntity() != null){
                points = entity.getScoreEntity().getScore();
            }
            players.add(new User(entity.getId(), entity.getUsername(), entity.getName(), points, entity.getEliminated()));
        }

        return players;

    }

    public void eliminatePlayer(PlayerIdRequest request) throws Exception {
        Optional<TestAccountEntity> player = testAccountRepository.findById(request.getId());
        if (player.isPresent()){
            if (player.get().getEliminated()){
                throw new Exception("Player is already eliminated.");
            }
            Optional<EliminationEntity> gameData = eliminationRepository.findByPlayerId(player.get().getId());
            if(gameData.isPresent()){
                Optional<EliminationEntity> gameDataOfWhoWasPlayerTargetBy = eliminationRepository.findByTargetId(player
                        .get().getId());
                if (gameDataOfWhoWasPlayerTargetBy.isPresent()){
                    gameDataOfWhoWasPlayerTargetBy.get().setEliminationCode(gameData.get().getEliminationCode());
                    gameDataOfWhoWasPlayerTargetBy.get().setTarget(gameData.get().getTarget());
                    eliminationRepository.save(gameDataOfWhoWasPlayerTargetBy.get());
                    eliminationRepository.delete(gameData.get());
                }
                else {
                    throw new Exception("Someone should be targeting user: %s".formatted(player.get().getUsername()));
                }
            }
            player.get().setEliminated(true);
            testAccountRepository.save(player.get());
            return;
        }
        throw new Exception("Couldn't find player form database");
    }

    public void revivePlayer(PlayerIdRequest request) throws Exception {
        Optional<TestAccountEntity> player = testAccountRepository.findById(request.getId());
        if (player.isPresent()){
            if (!player.get().getEliminated()){
                throw new Exception("Player is alive.");
            }
            player.get().setEliminated(false);
            testAccountRepository.save(player.get());
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
                leaders.add(new Leader(rank, score.getPlayer().getName(),score.getScore()));
                rank++;
            }
        }
        return leaders;
    }
}
