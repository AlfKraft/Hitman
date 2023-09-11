package com.hitmanbackend.dto;

import autovalue.shaded.org.jetbrains.annotations.NotNull;


public class AccountDataDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String birthdate;

    private String facebook;

    private String schoolAndSpeciality;

    private String workPlace;

    private String hobbies;

    private String mostVisitedPlaces;

    private String phoneNumber;

    private String username;

    private String password;

    private String repeatedPassword;

    public AccountDataDTO(String firstName, String lastName, String email, String birthdate, String facebook, String schoolAndSpeciality, String workPlace, String hobbies, String mostVisitedPlaces, String phoneNumber, String username, String password, String repeatedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.facebook = facebook;
        this.schoolAndSpeciality = schoolAndSpeciality;
        this.workPlace = workPlace;
        this.hobbies = hobbies;
        this.mostVisitedPlaces = mostVisitedPlaces;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }

    public AccountDataDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
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

    public String getMostVisitedPlaces() {
        return mostVisitedPlaces;
    }

    public void setMostVisitedPlaces(String mostVisitedPlaces) {
        this.mostVisitedPlaces = mostVisitedPlaces;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
}
