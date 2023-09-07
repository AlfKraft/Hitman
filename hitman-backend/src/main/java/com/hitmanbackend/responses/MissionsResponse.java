package com.hitmanbackend.responses;

import com.hitmanbackend.entities.MissionEntity;

import java.util.List;

public class MissionsResponse {

    List<MissionEntity> missions;

    public MissionsResponse(List<MissionEntity> missions) {
        this.missions = missions;
    }

    public List<MissionEntity> getMissions() {
        return missions;
    }

    public void setMissions(List<MissionEntity> missions) {
        this.missions = missions;
    }
}
