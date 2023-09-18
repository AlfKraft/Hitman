package com.hitmanbackend.requests;

public class CheckpointCompletionRequest {

    Long checkpointId;

    String checkpointCode;

    public CheckpointCompletionRequest(Long checkpointId, String checkpointCode) {
        this.checkpointId = checkpointId;
        this.checkpointCode = checkpointCode;
    }

    public Long getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(Long checkpointId) {
        this.checkpointId = checkpointId;
    }

    public String getCheckpointCode() {
        return checkpointCode;
    }

    public void setCheckpointCode(String checkpointCode) {
        this.checkpointCode = checkpointCode;
    }
}
