package com.solvd.laba.model;

public class Road {
    private int startCityId;
    private int endCityId;
    private int distance;

    public Road(int startCityId, int endCityId, int distance) {
        this.startCityId = startCityId;
        this.endCityId = endCityId;
        this.distance = distance;
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
