package com.hitmanbackend.requests;

public class CheckpointCreationRequest {


    private String checkpointName;
    private String checkpointLocation;
    private String checkpointDate;

    public CheckpointCreationRequest(String checkpointName, String checkpointLocation, String checkpointDate) {
        this.checkpointName = checkpointName;
        this.checkpointLocation = checkpointLocation;
        this.checkpointDate = checkpointDate;
    }

    public String getCheckpointName() {
        return checkpointName;
    }

    public void setCheckpointName(String checkpointName) {
        this.checkpointName = checkpointName;
    }

    public String getCheckpointLocation() {
        return checkpointLocation;
    }

    public void setCheckpointLocation(String checkpointLocation) {
        this.checkpointLocation = checkpointLocation;
    }

    public String getCheckpointDate() {
        return checkpointDate;
    }

    public void setCheckpointDate(String checkpointDate) {
        this.checkpointDate = checkpointDate;
    }
}
