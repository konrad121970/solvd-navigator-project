package com.solvd.laba.model;

public class Road {
    private Long startCityId;
    private Long endCityId;
    private int distance;

    public Road(Long startCityId, Long endCityId, int distance) {
        this.startCityId = startCityId;
        this.endCityId = endCityId;
        this.distance = distance;
    }

    public Long getStartCityId() {
        return startCityId;
    }

    public void setStartCityId(Long startCityId) {
        this.startCityId = startCityId;
    }

    public Long getEndCityId() {
        return endCityId;
    }

    public void setEndCityId(Long endCityId) {
        this.endCityId = endCityId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
