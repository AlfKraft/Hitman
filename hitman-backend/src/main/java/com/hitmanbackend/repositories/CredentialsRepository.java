package com.hitmanbackend.repositories;

import com.hitmanbackend.entities.CredentialsEntity;
import com.hitmanbackend.entities.PlayerDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialsRepository  extends JpaRepository<CredentialsEntity, Long> {
    Boolean existsByUsername(String username);

    Optional<CredentialsEntity> findByUsername(String username);
}
