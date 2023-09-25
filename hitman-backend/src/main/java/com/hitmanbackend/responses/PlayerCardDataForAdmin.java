package com.hitmanbackend.responses;

public class PlayerCardDataForAdmin {

    private String name;

    private String profileImage;

    private String email;

    private String birthdate;

    private String facebook;

    private String schoolAndSpeciality;

    private String workPlace;

    private String hobbies;

    private String favoritePlaces;

    private String phoneNumber;

    private Boolean approved;

    private Boolean eliminated;

    public PlayerCardDataForAdmin(String name, String profileImage, String email, String birthdate, String facebook,
                                  String schoolAndSpeciality, String workPlace, String hobbies, String favoritePlaces, String phoneNumber, Boolean approved, Boolean eliminated) {
        this.name = name;
        this.profileImage = profileImage;
        this.email = email;
        this.birthdate = birthdate;
        this.facebook = facebook;
        this.schoolAndSpeciality = schoolAndSpeciality;
        this.workPlace = workPlace;
        this.hobbies = hobbies;
        this.favoritePlaces = favoritePlaces;
        this.phoneNumber = phoneNumber;
        this.approved = approved;
        this.eliminated = eliminated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
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

    public String getFavoritePlaces() {
        return favoritePlaces;
    }

    public void setFavoritePlaces(String favoritePlaces) {
        this.favoritePlaces = favoritePlaces;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getEliminated() {
        return eliminated;
    }

    public void setEliminated(Boolean eliminated) {
        this.eliminated = eliminated;
    }
}
