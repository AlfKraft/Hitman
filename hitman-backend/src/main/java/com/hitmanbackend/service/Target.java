package com.hitmanbackend.service;

import org.springframework.stereotype.Service;


public class Target {

    private String name;

    private String facebook;
    private String schoolAndSpeciality;
    private String workPlace;
    private String hobbies;
    private String favoritePlaces;
    private String image;

    public Target(String name, String facebook, String schoolAndSpeciality, String workPlace, String hobbies, String favoritePlaces, String image) {
        this.name = name;
        this.facebook = facebook;
        this.schoolAndSpeciality = schoolAndSpeciality;
        this.workPlace = workPlace;
        this.hobbies = hobbies;
        this.favoritePlaces = favoritePlaces;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getSchoolAndSpeciality() {
        return schoolAndSpeciality;
    }

    public void setSchoolAndSpeciality(String schoolAndSpeciality) {
        this.schoolAndSpeciality = schoolAndSpeciality;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getFavoritePlaces() {
        return favoritePlaces;
    }

    public void setFavoritePlaces(String favoritePlaces) {
        this.favoritePlaces = favoritePlaces;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
