package com.hitmanbackend.repositories;

import com.hitmanbackend.entities.EliminationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EliminationRepository extends JpaRepository<EliminationEntity, Long> {
    Optional<EliminationEntity> findByPlayerId(Long playerId);
    Optional<EliminationEntity> findByTargetId(Long playerId);
}
