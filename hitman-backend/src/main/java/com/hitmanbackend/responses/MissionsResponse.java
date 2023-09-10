package com.hitmanbackend.responses;

import com.hitmanbackend.entities.MissionEntity;

import java.util.ArrayList;
import java.util.List;

public class MissionsResponse {

    List<MissionResponse> missions;

    List<MissionResponse> completedMissions;

    public MissionsResponse(List<MissionResponse> missions) {
        this.missions = missions;
    }

    public MissionsResponse() {
        this.missions = new ArrayList<>();
        this.completedMissions = new ArrayList<>();
    }

    public List<MissionResponse> getMissions() {
        return missions;
    }

    public void setMissions(List<MissionResponse> missions) {
        this.missions = missions;
    }

    public List<MissionResponse> getCompletedMissions() {
        return completedMissions;
    }

    public void setMissionCompleted(List<MissionResponse> completedMissions) {
        this.completedMissions = completedMissions;
    }
}
