package com.hitmanbackend.repositories;

import com.hitmanbackend.entities.AdminControlValuesEntity;
import com.hitmanbackend.entities.CheckpointCompletionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminControlRepository extends JpaRepository<AdminControlValuesEntity, Long> {
    Optional<AdminControlValuesEntity> findById(Long id);

}
