package com.hitmanbackend.entities;

import autovalue.shaded.org.jetbrains.annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "account_data")
public class PlayerDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String birthdate;
    @NotNull
    private String facebook;
    @NotNull
    private String schoolAndSpeciality;
    @NotNull
    private String workPlace;
    @NotNull
    private String hobbies;
    @NotNull
    private String favoritePlaces;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String username;
    @NotNull
    private String profileImage;
    @NotNull
    private Boolean eliminated;
    @NotNull
    private String role;

    @NotNull
    private Boolean approved;

    @OneToOne(mappedBy = "player")
    private EliminationEntity playerEntity;

    @OneToOne(mappedBy = "target")
    private EliminationEntity targetEntity;

    @OneToOne(mappedBy = "player")
    private ScoreEntity scoreEntity;

    @OneToMany(mappedBy = "player")
    private Set<MissionAssignmentEntity> missions = new HashSet<>();

    @OneToMany(mappedBy = "player")
    private Set<CheckpointCompletionEntity> checkpoints = new HashSet<>();

    public PlayerDataEntity(Long id, String firstName, String lastName, String email, String birthdate, String facebook,
                            String schoolAndSpeciality, String workPlace, String hobbies, String favoritePlaces,
                            String phoneNumber, String username,String profileImage, Boolean eliminated, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.facebook = facebook;
        this.schoolAndSpeciality = schoolAndSpeciality;
        this.workPlace = workPlace;
        this.hobbies = hobbies;
        this.favoritePlaces = favoritePlaces;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.profileImage = profileImage;
        this.eliminated = eliminated;
        this.role = role;
    }

    public PlayerDataEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Boolean getEliminated() {
        return eliminated;
    }

    public void setEliminated(Boolean eliminated) {
        this.eliminated = eliminated;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ScoreEntity getScoreEntity() {
        return scoreEntity;
    }
    public Set<MissionAssignmentEntity> getMissions() {
        return missions;
    }
    public EliminationEntity getPlayerEntity() {
        return playerEntity;
    }

    public EliminationEntity getTargetEntity() {
        return targetEntity;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setPlayerEntity(EliminationEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public void setTargetEntity(EliminationEntity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public void setScoreEntity(ScoreEntity scoreEntity) {
        this.scoreEntity = scoreEntity;
    }

    public void setMissions(Set<MissionAssignmentEntity> missions) {
        this.missions = missions;
    }

    public Set<CheckpointCompletionEntity> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(Set<CheckpointCompletionEntity> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}
