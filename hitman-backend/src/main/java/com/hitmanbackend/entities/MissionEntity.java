package com.hitmanbackend.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

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
    private Boolean forEliminated;
    private Long missionCompletionCount;

    @OneToMany(mappedBy = "mission")
    private Set<MissionAssignmentEntity> missions = new HashSet<>();

    public MissionEntity(String missionName, String missionInfo, String startTime, String endTime, String location
            , String missionCode, Long points, Boolean forEliminated, Long missionCompletionCount) {
        this.missionName = missionName;
        this.missionInfo = missionInfo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.missionCode = missionCode;
        this.points = points;
        this.forEliminated = forEliminated;
        this.missionCompletionCount = missionCompletionCount;
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

    public Set<MissionAssignmentEntity> getMissions() {
        return missions;
    }

    public void setMissions(Set<MissionAssignmentEntity> missions) {
        this.missions = missions;
    }
}
