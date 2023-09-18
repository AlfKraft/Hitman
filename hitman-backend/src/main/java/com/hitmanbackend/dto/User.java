package com.hitmanbackend.dto;

public class User {

    private Long id;

    private String username;

    private String name;

    private Long points;

    private Boolean eliminated;

    private String email;

    private String phoneNumber;

    public User(Long id, String username, String name, Long points, Boolean eliminated, String email, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.points = points;
        this.eliminated = eliminated;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEliminated() {
        return eliminated;
    }

    public void setEliminated(Boolean eliminated) {
        this.eliminated = eliminated;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
