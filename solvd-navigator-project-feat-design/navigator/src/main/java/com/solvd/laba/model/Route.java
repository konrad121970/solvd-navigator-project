package com.solvd.laba.model;

import java.util.Map;

public class Route {
    private Long id;
    private Map<Integer, City> cityOrder;
    private int distance;

    public Route() {}

    public Route(Long id, Map<Integer, City> cityOrder, int distance) {
        this.id = id;
        this.cityOrder = cityOrder;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Integer, City> getCityOrder() {
        return cityOrder;
    }

    public void setCityOrder(Map<Integer, City> cityOrder) {
        this.cityOrder = cityOrder;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", cityOrder=" + cityOrder +
                ", distance=" + distance +
                '}';
    }
}
