package com.hitmanbackend.repositories;

import com.hitmanbackend.entities.PlayerDataEntity;
import com.hitmanbackend.entities.TestAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PlayerRepository extends JpaRepository<PlayerDataEntity, Long> {
    Optional<PlayerDataEntity> findAccountByUsername(String username);
    Boolean existsByUsernameIgnoreCase(String username);
    List<PlayerDataEntity> findByRoleEqualsAndApprovedTrue(String role);

    Boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastname);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumberContaining(String number);

    List<PlayerDataEntity> findByRoleEqualsAndEliminatedFalseAndApprovedTrue(String role);
    List<PlayerDataEntity> findAllByRoleEquals(String role);

    List<PlayerDataEntity> findByEliminated(Boolean eliminated);
}
