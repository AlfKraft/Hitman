package com.hitmanbackend.repositories;

import com.hitmanbackend.entities.CheckpointCompletionEntity;
import com.hitmanbackend.entities.CheckpointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckpointCompletionRepository extends JpaRepository<CheckpointCompletionEntity, Long> {

}
