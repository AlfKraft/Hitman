package com.hitmanbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "current_game")
public class EliminationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "player_id")
    @OneToOne
    private TestAccountEntity player;
    private String eliminationCode;

    @JoinColumn(name = "target_id")
    @OneToOne
    private TestAccountEntity target;

    public EliminationEntity() {
    }

    public EliminationEntity(TestAccountEntity player, String eliminationCode, TestAccountEntity target) {
        this.player = player;
        this.eliminationCode = eliminationCode;
        this.target = target;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEliminationCode() {
        return eliminationCode;
    }

    public void setEliminationCode(String eliminationCode) {
        this.eliminationCode = eliminationCode;
    }

    public TestAccountEntity getPlayer() {
        return player;
    }

    public void setPlayer(TestAccountEntity player) {
        this.player = player;
    }

    public TestAccountEntity getTarget() {
        return target;
    }

    public void setTarget(TestAccountEntity target) {
        this.target = target;
    }
}
