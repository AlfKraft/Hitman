package com.hitmanbackend.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "checkpoints")
public class CheckpointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String checkpointName;

    private String location;

    private String startTime;

    private String endTime;

    private String checkpointCode;

    @OneToMany(mappedBy = "checkpoint")
    private Set<CheckpointCompletionEntity> missions = new HashSet<>();


    public CheckpointEntity() {
    }

    public CheckpointEntity(String checkpointName, String location, String startTime, String endTime, String checkpointCode) {
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

    public String getCheckpointName() {
        return checkpointName;
    }

    public void setCheckpointName(String checkpointName) {
        this.checkpointName = checkpointName;
    }

    public String getCheckpointCode() {
        return checkpointCode;
    }

    public void setCheckpointCode(String checkpointCode) {
        this.checkpointCode = checkpointCode;
    }

    public Set<CheckpointCompletionEntity> getMissions() {
        return missions;
    }

    public void setMissions(Set<CheckpointCompletionEntity> missions) {
        this.missions = missions;
    }

    @Override
    public String toString() {
        return "CheckpointEntity{" +
                "id=" + id +
                ", checkpointName='" + checkpointName + '\'' +
                ", location='" + location + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
