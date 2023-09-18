package com.hitmanbackend.repositories;

import com.hitmanbackend.entities.CheckpointEntity;
import com.hitmanbackend.entities.PlayerDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckpointRepository extends JpaRepository<CheckpointEntity, Long> {
        Optional<CheckpointEntity> findByCheckpointCode(String code);
        Boolean existsByCheckpointName(String name);
}
