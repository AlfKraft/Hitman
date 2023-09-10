package com.hitmanbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "mission_assignment")
public class MissionAssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "mission_id")
    @ManyToOne
    private MissionEntity mission;

    @JoinColumn(name = "player_id")
    @ManyToOne
    private TestAccountEntity player;

    private Boolean completed;

    public MissionAssignmentEntity(MissionEntity mission, TestAccountEntity player, Boolean completed) {
        this.mission = mission;
        this.player = player;
        this.completed = completed;
    }


    public MissionAssignmentEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MissionEntity getMission() {
        return mission;
    }

    public void setMission(MissionEntity mission) {
        this.mission = mission;
    }

    public TestAccountEntity getPlayer() {
        return player;
    }

    public void setPlayer(TestAccountEntity player) {
        this.player = player;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
