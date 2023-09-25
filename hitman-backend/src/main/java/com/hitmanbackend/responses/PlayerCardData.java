package com.hitmanbackend.responses;

public class PlayerCardData {

    private String name;

    private String eliminationCode;
    private Long score;

    private String rank;
    private Long aliveAgents;
    private Boolean isEliminated;

    public PlayerCardData(String name, String eliminationCode, Long score, Boolean isEliminated, String rank, Long aliveAgents) {
        this.name = name;
        this.eliminationCode = eliminationCode;
        this.score = score;
        this.isEliminated = isEliminated;
        this.rank = rank;
        this.aliveAgents = aliveAgents;
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
        return this.score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEliminated() {
        return isEliminated;
    }

    public void setEliminated(Boolean isEliminated) {
        this.isEliminated = isEliminated;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Long getAliveAgents() {
        return aliveAgents;
    }

    public void setAliveAgents(Long aliveAgents) {
        this.aliveAgents = aliveAgents;
    }
}
