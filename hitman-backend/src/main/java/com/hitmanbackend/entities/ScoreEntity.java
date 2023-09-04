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
    private TestAccountEntity player;

    private Long score;

    public ScoreEntity() {
    }

    public ScoreEntity(TestAccountEntity player, Long score) {
        this.id = id;
        this.player = player;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TestAccountEntity getPlayer() {
        return player;
    }

    public void setPlayer(TestAccountEntity player) {
        this.player = player;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}