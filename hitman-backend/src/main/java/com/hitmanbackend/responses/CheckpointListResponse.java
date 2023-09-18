package com.hitmanbackend.responses;

import java.util.ArrayList;
import java.util.List;

public class CheckpointListResponse {

    List<CheckpointResponse> checkpoints;

    List<UserCheckpoint> userCheckpoints;

    public CheckpointListResponse() {
        this.checkpoints = new ArrayList<>();
        this.userCheckpoints = new ArrayList<>();
    }

    public List<CheckpointResponse> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<CheckpointResponse> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public List<UserCheckpoint> getUserCheckpoints() {
        return userCheckpoints;
    }

    public void setUserCheckpoints(List<UserCheckpoint> userCheckpoints) {
        this.userCheckpoints = userCheckpoints;
    }
}
