package com.hitmanbackend.repositories;

import com.hitmanbackend.entities.TestAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestAccountRepository extends JpaRepository<TestAccountEntity, Long> {

    Optional<TestAccountEntity> findAccountByUsername(String username);
    Boolean existsByUsername(String username);
    List<TestAccountEntity> findByRoleEquals(String role);

    List<TestAccountEntity> findByRoleEqualsAndEliminatedFalse(String role);
    List<TestAccountEntity> findAllByRoleEquals(String role);



}
