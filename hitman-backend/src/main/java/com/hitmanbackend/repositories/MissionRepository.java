package com.hitmanbackend.repositories;

import com.hitmanbackend.entities.MissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<MissionEntity, Long> {

    Optional<MissionEntity> findByMissionName(String name);
    List<MissionEntity> findAllByForEliminated(Boolean forEliminated);
    Optional<MissionEntity> findFirstByOrderByIdDesc();

    List<MissionEntity> findAllByMissionNameContains(String name);

    Optional<MissionEntity> findByMissionCodeEquals(String code);

    Boolean existsByMissionName(String name);

}
