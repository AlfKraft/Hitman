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
    private PlayerDataEntity player;
    private String eliminationCode;

    @JoinColumn(name = "target_id")
    @OneToOne
    private PlayerDataEntity target;

    public EliminationEntity() {
    }

    public EliminationEntity(PlayerDataEntity player, String eliminationCode, PlayerDataEntity target) {
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

    public PlayerDataEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDataEntity player) {
        this.player = player;
    }

    public PlayerDataEntity getTarget() {
        return target;
    }

    public void setTarget(PlayerDataEntity target) {
        this.target = target;
    }
}
