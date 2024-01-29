package com.solvd.laba.service.route;

import com.solvd.laba.model.Route;

import java.util.List;
import java.util.Optional;

public interface IRouteService {

    void createRoute(Route route);

    Route getRouteById(Long id);

    List<Route> getAllRoutes();

    List<Route> findRoutesByStartCityId(Long startCityId);

    void updateRoute(Route route);

    void deleteRouteById(Long id);

    Optional<Route> getRouteBetweenTwoCities(Long startCityId, Long endCityId);
}
