package com.solvd.laba.service.route;

import com.solvd.laba.model.RouteCity;

import java.util.List;

public interface IRouteCityService {
    void createRouteCity(RouteCity routeCity);

    List<RouteCity> getAllRouteCities();

    void updateRouteCity(RouteCity routeCity);

    void deleteRouteCityById(Long id);
}
