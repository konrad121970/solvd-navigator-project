package com.solvd.laba.model;

public class RouteCity {
    private Route route;
    private City city;
    private Integer cityOrderNumber;

    public RouteCity(Route route, City city, Integer cityOrderNumber) {
        this.route = route;
        this.city = city;
        this.cityOrderNumber = cityOrderNumber;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Integer getCityOrderNumber() {
        return cityOrderNumber;
    }

    public void setCityOrderNumber(Integer cityOrderNumber) {
        this.cityOrderNumber = cityOrderNumber;
    }
}
