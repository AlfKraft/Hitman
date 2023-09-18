package com.hitmanbackend.repositories;

import com.hitmanbackend.entities.CredentialsEntity;
import com.hitmanbackend.entities.PlayerDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository  extends JpaRepository<CredentialsEntity, Long> {
    Boolean existsByUsernameIgnoreCase(String username);

    Optional<CredentialsEntity> findByUsername(String username);
}
