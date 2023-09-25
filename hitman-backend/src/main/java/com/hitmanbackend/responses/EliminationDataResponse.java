package com.hitmanbackend.responses;

public class EliminationDataResponse {

    private String playerName;

    private String eliminationCode;

    private String targetName;

    public EliminationDataResponse(String playerName, String eliminationCode, String targetName) {
        this.playerName = playerName;
        this.eliminationCode = eliminationCode;
        this.targetName = targetName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getEliminationCode() {
        return eliminationCode;
    }

    public void setEliminationCode(String eliminationCode) {
        this.eliminationCode = eliminationCode;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
}
