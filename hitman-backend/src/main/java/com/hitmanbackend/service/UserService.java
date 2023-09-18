package com.hitmanbackend.service;

import com.hitmanbackend.dto.Leader;
import com.hitmanbackend.dto.User;
import com.hitmanbackend.entities.*;
import com.hitmanbackend.repositories.*;
import com.hitmanbackend.requests.PlayerIdRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        Optional<PlayerDataEntity> player = playerRepository.findById(request.getId());
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
            playerRepository.save(player.get());
            return;
        }
        throw new Exception("Couldn't find player form database");
    }

    public void revivePlayer(PlayerIdRequest request) throws Exception {
        Optional<PlayerDataEntity> player = playerRepository.findById(request.getId());
        if (player.isPresent()){
            if (!player.get().getEliminated()){
                throw new Exception("Player is alive.");
            }
            player.get().setEliminated(false);
            playerRepository.save(player.get());
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
}
