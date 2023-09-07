package com.hitmanbackend.dto;

public class User {

    private Long id;

    private String username;

    private String name;

    private Long points;

    private Boolean eliminated;

    public User(Long id, String username, String name, Long points, Boolean eliminated) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.eliminated = eliminated;
        this.points = points;
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
}
