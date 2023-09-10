package com.hitmanbackend.requests;

public class MissionCreationRequest {
    private Long id;

    private String missionName;
    private String missionInfo;
    private String startDateTime;
    private String endDateTime;
    private String missionLocation;
    private String missionCode;
    private String points;
    private Boolean forEliminated;
    private Long missionCompletionCount;

    public MissionCreationRequest(String missionName, String missionInfo, String startDateTime, String endDateTime
            , String missionLocation, String missionCode, String points, Boolean forEliminated, Long missionCompletionCount) {
        this.missionName = missionName;
        this.missionInfo = missionInfo;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.missionLocation = missionLocation;
        this.missionCode = missionCode;
        this.points = points;
        this.forEliminated = forEliminated;
        this.missionCompletionCount= missionCompletionCount;
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

    public String getMissionCode() {
        return missionCode;
    }

    public void setMissionCode(String missionCode) {
        this.missionCode = missionCode;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public Boolean getForEliminated() {
        return forEliminated;
    }

    public void setForEliminated(Boolean forEliminated) {
        this.forEliminated = forEliminated;
    }

    public Long getMissionCompletionCount() {
        return missionCompletionCount;
    }

    public void setMissionCompletionCount(Long missionCompletionCount) {
        this.missionCompletionCount = missionCompletionCount;
    }
}
