package com.hitmanbackend.dto;

import org.springframework.web.multipart.MultipartFile;

public class TestFile {

    private MultipartFile profileImage;

    private String firstName;

    public TestFile(MultipartFile profileImage, String firstName) {
        this.profileImage = profileImage;
        this.firstName = firstName;
    }

    public TestFile() {
    }

    public MultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
