package com.solvd.laba.model;

import java.util.List;


public class City {
    private Long id;
    private String name;
    private Double xPos;
    private Double yPos;
    private List<Road> roads;

    private City(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.xPos = builder.xPos;
        this.yPos = builder.yPos;
        this.roads = builder.roads;
    }

    public static class Builder {
        private Long id;
        private String name;
        private Double xPos;
        private Double yPos;
        private List<Road> roads;

        public Builder() {
            // Set default values or leave them null
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder xPos(Double xPos) {
            this.xPos = xPos;
            return this;
        }

        public Builder yPos(Double yPos) {
            this.yPos = yPos;
            return this;
        }

        public Builder roads(List<Road> roads) {
            this.roads = roads;
            return this;
        }

        public City build() {
            return new City(this);
        }
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

