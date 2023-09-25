package com.hitmanbackend.responses;

import java.util.ArrayList;
import java.util.List;

public class GameResponse {

    private List<EliminationDataResponse> gameData;

    public GameResponse(List<EliminationDataResponse> gameData) {
        this.gameData = gameData;
    }

    public GameResponse() {
        this.gameData = new ArrayList<>();
    }


    public List<EliminationDataResponse> getGameData() {
        return gameData;
    }

    public void setGameData(List<EliminationDataResponse> gameData) {
        this.gameData = gameData;
    }
}
