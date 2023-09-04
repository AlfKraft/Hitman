package com.hitmanbackend.requests;

public class EliminationRequest {
    private String eliminationCode;

    public EliminationRequest(String eliminationCode) {
        this.eliminationCode = eliminationCode;
    }

    public EliminationRequest() {
    }

    public String getEliminationCode() {
        return eliminationCode;
    }

    public void setEliminationCode(String eliminationCode) {
        this.eliminationCode = eliminationCode;
    }
}
