package com.hitmanbackend.requests;

public class CheckpointCreationRequest {


    private String checkpointName;
    private String checkpointLocation;
    private String startDateTime;
    private String endDateTime;

    public CheckpointCreationRequest(String checkpointName, String checkpointLocation, String startDateTime, String endDateTime) {
        this.checkpointName = checkpointName;
        this.checkpointLocation = checkpointLocation;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }
}
