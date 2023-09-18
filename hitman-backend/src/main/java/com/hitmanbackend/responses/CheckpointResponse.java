package com.hitmanbackend.responses;

public class CheckpointResponse {

    private Long id;

    private String checkpointName;

    private String location;

    private String startTime;

    private String endTime;

    private String checkpointCode;

    public CheckpointResponse(Long id, String checkpointName, String location, String startTime, String endTime, String checkpointCode) {
        this.id = id;
        this.checkpointName = checkpointName;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.checkpointCode = checkpointCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheckpointName() {
        return checkpointName;
    }

    public void setCheckpointName(String checkpointName) {
        this.checkpointName = checkpointName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCheckpointCode() {
        return checkpointCode;
    }

    public void setCheckpointCode(String checkpointCode) {
        this.checkpointCode = checkpointCode;
    }
}
