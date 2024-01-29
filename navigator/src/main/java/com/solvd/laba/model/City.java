package com.solvd.laba.model;

import java.util.ArrayList;
import java.util.List;

public class City {
    private Long id;
    private String name;
    private Double xPos;
    private Double yPos;
    private List<Road> roads;

    private City() {
        // Private constructor to enforce the use of the builder
    }

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {
        private City city;

        private Builder() {
            this.city = new City();
            this.city.setRoads(new ArrayList<>());
        }

        public Builder setId(Long id) {
            city.id = id;
            return this;
        }
        public Builder setName(String name) {
            city.name = name;
            return this;
        }

        public Builder setXPos(Double xPos) {
            city.xPos = xPos;
            return this;
        }

        public Builder setYPos(Double yPos) {
            city.yPos = yPos;
            return this;
        }

        public Builder setRoads(List<Road> roads) {
            city.roads = roads;
            return this;
        }

        public City build() {
            return city;
        }
    }
}
