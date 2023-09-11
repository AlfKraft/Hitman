package com.hitmanbackend.repositories;

import com.hitmanbackend.entities.PlayerDataEntity;
import com.hitmanbackend.entities.TestAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<PlayerDataEntity, Long> {
    Optional<PlayerDataEntity> findAccountByUsername(String username);
    Boolean existsByUsername(String username);
    List<PlayerDataEntity> findByRoleEquals(String role);

    List<PlayerDataEntity> findByRoleEqualsAndEliminatedFalse(String role);
    List<PlayerDataEntity> findAllByRoleEquals(String role);

    List<PlayerDataEntity> findByEliminated(Boolean eliminated);
}
