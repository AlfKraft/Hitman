package com.hitmanbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "checkpoint_completions")
public class CheckpointCompletionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "checkpoint_id")
    @ManyToOne
    private CheckpointEntity checkpoint;

    @JoinColumn(name = "player_id")
    @ManyToOne
    private PlayerDataEntity player;

    private Boolean completed;

    public CheckpointCompletionEntity(CheckpointEntity checkpoint, PlayerDataEntity player, Boolean completed) {
        this.checkpoint = checkpoint;
        this.player = player;
        this.completed = completed;
    }

    public CheckpointCompletionEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CheckpointEntity getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(CheckpointEntity checkpoint) {
        this.checkpoint = checkpoint;
    }

    public PlayerDataEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDataEntity player) {
        this.player = player;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
