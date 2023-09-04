package com.hitmanbackend.service;

import org.springframework.stereotype.Service;


public class Target {

    private String name;

    private String targetInfo;

    public Target(String name, String targetInfo) {
        this.name = name;
        this.targetInfo = targetInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetInfo() {
        return targetInfo;
    }

    public void setTargetInfo(String targetInfo) {
        this.targetInfo = targetInfo;
    }
}
