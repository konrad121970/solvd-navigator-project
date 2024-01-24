package com.solvd.laba.service.route;

import com.solvd.laba.model.Route;

import java.util.List;

public interface IRouteService {

    void createRoute(Route route);

    Route getRouteById(Long id);

    List<Route> getAllRoutes();

    void updateRoute(Route route);

    void deleteRouteById(Long id);

    Route getRouteBetweenTwoCities(Long startCityId, Long endCityId);
}
