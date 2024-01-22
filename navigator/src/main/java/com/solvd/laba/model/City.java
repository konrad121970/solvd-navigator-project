package com.solvd.laba.model;

import java.util.List;

public class City {
    private Long id;
    private String name;
    private Double xPos;
    private Double yPos;
    private List<Road> roads;

    public City(Long id, String name, Double xPos, Double yPos, List<Road> roads) {
        this.id = id;
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.roads = roads;
    }

    // Constructor for creating new city without id:
    public City(String name, Double xPos, Double yPos, List<Road> roads) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.roads = roads;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getxPos() {
        return xPos;
    }

    public void setxPos(Double xPos) {
        this.xPos = xPos;
    }

    public Double getyPos() {
        return yPos;
    }

    public void setyPos(Double yPos) {
        this.yPos = yPos;
    }

    @Override
    public String toString() {
        return name;
    }
}
