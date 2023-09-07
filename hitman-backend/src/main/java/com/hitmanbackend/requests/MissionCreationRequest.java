package com.hitmanbackend.requests;

public class MissionCreationRequest {
    private Long id;

    private String missionName;
    private String missionInfo;
    private String startDateTime;
    private String endDateTime;
    private String missionLocation;

    private String points;

    public MissionCreationRequest(Long id, String missionName, String missionInfo, String startDateTime, String endDateTime
            , String missionLocation, String points) {
        this.id = id;
        this.missionName = missionName;
        this.missionInfo = missionInfo;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.missionLocation = missionLocation;
        this.points = points;
    }

    public MissionCreationRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getMissionInfo() {
        return missionInfo;
    }

    public void setMissionInfo(String missionInfo) {
        this.missionInfo = missionInfo;
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

    public String getMissionLocation() {
        return missionLocation;
    }

    public void setMissionLocation(String missionLocation) {
        this.missionLocation = missionLocation;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
