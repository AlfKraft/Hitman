package com.hitmanbackend.requests;

public class CompleteMissionRequest {

    private Long missionId;

    private String missionCode;

    public CompleteMissionRequest(Long missionId, String missionCode) {
        this.missionId = missionId;
        this.missionCode = missionCode;
    }

    public CompleteMissionRequest() {
    }


    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public String getMissionCode() {
        return missionCode;
    }

    public void setMissionCode(String missionCode) {
        this.missionCode = missionCode;
    }
}
