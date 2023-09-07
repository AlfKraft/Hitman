package com.hitmanbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "missions")
public class MissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String missionName;
    private String missionInfo;
    private String startTime;
    private String endTime;
    private String location;
    private String missionCode;
    private Long points;

    public MissionEntity(Long id, String missionName, String missionInfo, String startTime, String endTime, String location, String missionCode, Long points) {
        this.id = id;
        this.missionName = missionName;
        this.missionInfo = missionInfo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.missionCode = missionCode;
        this.points = points;
    }

    public MissionEntity() {
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

    public String getMissionCode() {
        return missionCode;
    }

    public void setMissionCode(String missionCode) {
        this.missionCode = missionCode;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }
}
