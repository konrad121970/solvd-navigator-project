package com.solvd.laba.service.route.impl;

import com.solvd.laba.model.City;
import com.solvd.laba.model.Route;
import com.solvd.laba.model.RouteCity;
import com.solvd.laba.persistence.route.IRouteRepository;
import com.solvd.laba.persistence.route.impl.RouteRepositoryImpl;
import com.solvd.laba.service.route.IRouteCityService;
import com.solvd.laba.service.route.IRouteService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RouteService implements IRouteService {

    private final IRouteRepository routeRepository;
    private final IRouteCityService routeCityService;


    public RouteService() {
        routeRepository = new RouteRepositoryImpl();
        routeCityService = new RouteCityService();
    }
    @Override
    public void createRoute(Route route) {
        routeRepository.create(route);

        route.getCityOrder().forEach( (cityOrderNumber, city) -> {
            RouteCity routeCity = new RouteCity(route, city, cityOrderNumber);
            routeCityService.createRouteCity(routeCity);
        } );

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
    public Optional<Route> getRouteBetweenTwoCities(Long startCityId, Long endCityId) {
        return routeRepository.getRouteBetweenTwoCities(startCityId, endCityId);
    }
}
