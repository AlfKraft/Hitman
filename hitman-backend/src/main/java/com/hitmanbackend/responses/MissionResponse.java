package com.hitmanbackend.responses;

public class MissionResponse {
    private Long id;
    private String missionName;
    private String missionInfo;
    private String startTime;
    private String endTime;
    private String location;
    private Long points;
    private Boolean forEliminated;
    private Long missionCompletionCount;
    private Boolean active;

    public MissionResponse(Long id, String missionName, String missionInfo, String startTime, String endTime, String location, Long points, Boolean forEliminated, Long missionCompletionCount, Boolean active) {
        this.id = id;
        this.missionName = missionName;
        this.missionInfo = missionInfo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.points = points;
        this.forEliminated = forEliminated;
        this.missionCompletionCount = missionCompletionCount;
        this.active = active;
    }

    public MissionResponse() {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
