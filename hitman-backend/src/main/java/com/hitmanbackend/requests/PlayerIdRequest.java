package com.hitmanbackend.requests;

public class PlayerIdRequest {
    private Long id;


    public PlayerIdRequest(Long id) {
        this.id = id;
    }

    public PlayerIdRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
