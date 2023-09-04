package com.hitmanbackend.responses;

public class PlayerCardData {

    private String eliminationCode;

    private Long Score;

    public PlayerCardData(String eliminationCode, Long score) {
        this.eliminationCode = eliminationCode;
        Score = score;
    }

    public PlayerCardData() {
    }

    public String getEliminationCode() {
        return eliminationCode;
    }

    public void setEliminationCode(String eliminationCode) {
        this.eliminationCode = eliminationCode;
    }

    public Long getScore() {
        return Score;
    }

    public void setScore(Long score) {
        Score = score;
    }
}
