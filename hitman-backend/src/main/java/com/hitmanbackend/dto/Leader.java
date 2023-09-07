package com.hitmanbackend.dto;

public class Leader {

    private Long rank;

    private String name;

    private Long points;

    public Leader(Long rank, String name, Long points) {
        this.rank = rank;
        this.name = name;
        this.points = points;
    }


    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }
}
