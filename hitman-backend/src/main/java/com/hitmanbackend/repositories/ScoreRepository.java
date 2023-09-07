package com.hitmanbackend.repositories;

import com.hitmanbackend.entities.EliminationEntity;
import com.hitmanbackend.entities.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreEntity, Long> {

    Optional<ScoreEntity> findByPlayerId(Long id);
    List<ScoreEntity> findTop5ByOrderByScoreDesc();
}
