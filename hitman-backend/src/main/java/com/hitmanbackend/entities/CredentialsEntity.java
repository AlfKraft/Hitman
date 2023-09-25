package com.hitmanbackend.entities;

import autovalue.shaded.org.jetbrains.annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "credentials")
public class CredentialsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    @JsonIgnore
    private String password;

    public CredentialsEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public CredentialsEntity() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
