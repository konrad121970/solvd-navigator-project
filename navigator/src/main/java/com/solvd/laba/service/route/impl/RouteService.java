package com.solvd.laba.service.route.impl;

import com.solvd.laba.model.Route;
import com.solvd.laba.persistence.route.IRouteRepository;
import com.solvd.laba.persistence.route.impl.RouteRepositoryImpl;
import com.solvd.laba.service.route.IRouteService;

import java.util.List;

public class RouteService implements IRouteService {

    private final IRouteRepository routeRepository;


    public RouteService() {
        this.routeRepository = new RouteRepositoryImpl();
    }
    @Override
    public void createRoute(Route route) {
        routeRepository.create(route);
    }
    @Override
    public Route getRouteById(Long id) {
        return routeRepository.findById(id);
    }
    @Override
    public List<Route> getAllRoutes() {
        return routeRepository.getAllRoutes();
    }
    @Override
    public void updateRoute(Route route) {
        routeRepository.updateById(route);
    }
    @Override
    public void deleteRouteById(Long id) {
        routeRepository.deleteById(id);
    }
    @Override
    public Route getRouteBetweenTwoCities(Long startCityId, Long endCityId) {
        return routeRepository.getRouteBetweenTwoCities(startCityId, endCityId).get(0);
    }
}
