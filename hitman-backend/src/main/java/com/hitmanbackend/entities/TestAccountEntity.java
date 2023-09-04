package com.hitmanbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "test_accounts")
public class TestAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String name;

    private String aboutInfo;

    private String role;

    private Boolean eliminated;

    @OneToOne(mappedBy = "player")
    private EliminationEntity playerEntity;

    @OneToOne(mappedBy = "target")
    private EliminationEntity targetEntity;


    public Boolean getEliminated() {
        return eliminated;
    }

    public void setEliminated(Boolean eliminated) {
        this.eliminated = eliminated;
    }

    public TestAccountEntity() {
    }

    public TestAccountEntity(Long id, String username, String password, String name, String aboutInfo, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.aboutInfo = aboutInfo;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAboutInfo() {
        return aboutInfo;
    }

    public void setAboutInfo(String aboutInfo) {
        this.aboutInfo = aboutInfo;
    }
}
