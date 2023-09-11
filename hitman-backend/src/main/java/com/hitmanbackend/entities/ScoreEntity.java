package com.hitmanbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "scores")
public class ScoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "player_id")
    @OneToOne(fetch = FetchType.LAZY)
    private PlayerDataEntity player;

    private Long score;


    public ScoreEntity() {
    }

    public ScoreEntity(PlayerDataEntity player, Long score) {
        this.player = player;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public PlayerDataEntity getPlayer() {
        return player;
    }
}
