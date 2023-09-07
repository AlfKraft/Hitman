package com.hitmanbackend.service;

import com.hitmanbackend.entities.MissionEntity;
import com.hitmanbackend.repositories.MissionRepository;
import com.hitmanbackend.requests.MissionCreationRequest;
import com.hitmanbackend.responses.MissionsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MissionService {

    @Autowired
    MissionRepository missionRepository;

    @Autowired
    RandomCode missionCode;


    public void createNewMission(MissionCreationRequest request){
        String newMissionCode = missionCode.randomAlphanumericString(10);
        MissionEntity newMissionEntity = new MissionEntity(request.getId(), request.getMissionName(), request.getMissionInfo(),
                request.getStartDateTime(),request.getEndDateTime(),request.getMissionLocation(),newMissionCode, Long.parseLong(request.getPoints()));
        missionRepository.save(newMissionEntity);
    }

    public MissionsResponse getAllMissions(){
        return new MissionsResponse(missionRepository.findAll());
    }

}
