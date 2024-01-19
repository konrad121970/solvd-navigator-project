package com.solvd.laba.model;

public class Road {

    private Long id;
    private int startCityId;
    private int endCityId;
    private int distance;

    public Road(Long id, int startCityId, int endCityId, int distance) {
        this.id = id;
        this.startCityId = startCityId;
        this.endCityId = endCityId;
        this.distance = distance;
    }

    // Constructor for creating new road without id:
    public Road(int startCityId, int endCityId, int distance) {
        this.startCityId = startCityId;
        this.endCityId = endCityId;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStartCityId() {
        return startCityId;
    }

    public void setStartCityId(int startCityId) {
        this.startCityId = startCityId;
    }

    public int getEndCityId() {
        return endCityId;
    }

    public void setEndCityId(int endCityId) {
        this.endCityId = endCityId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
