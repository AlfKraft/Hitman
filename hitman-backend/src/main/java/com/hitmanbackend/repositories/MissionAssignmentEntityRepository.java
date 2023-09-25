package com.hitmanbackend.repositories;

import com.hitmanbackend.entities.MissionAssignmentEntity;
import com.hitmanbackend.entities.MissionEntity;
import com.hitmanbackend.entities.TestAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MissionAssignmentEntityRepository extends JpaRepository<MissionAssignmentEntity, Long> {

    long countByMissionIdAndCompleted(Long missionId, Boolean completed);

    void deleteAllByMissionId(Long id);

    Optional<MissionAssignmentEntity> findByMissionIdAndPlayerId(Long missionId, Long playerId);
}
