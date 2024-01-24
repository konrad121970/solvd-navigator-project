package com.solvd.laba.persistence.route;

import com.solvd.laba.model.RouteCity;

import java.util.List;

public interface IRouteCityRepository {
    void addRouteCity(RouteCity routeCity);

    void updateRouteCity(RouteCity routeCity);

    void deleteRouteCity(long routeId, long cityId);

    List<RouteCity> getAllRouteCities();
}
