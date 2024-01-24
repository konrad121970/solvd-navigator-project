package com.solvd.laba.service.route.impl;

import com.solvd.laba.model.RouteCity;
import com.solvd.laba.persistence.route.IRouteCityRepository;
import com.solvd.laba.persistence.route.impl.RouteCityRepositoryImpl;
import com.solvd.laba.service.route.IRouteCityService;

import java.util.List;

public class RouteCityService implements IRouteCityService {

    private final IRouteCityRepository routeCityRepository;

    public RouteCityService() {
        this.routeCityRepository = new RouteCityRepositoryImpl();
    }

    @Override
    public void createRouteCity(RouteCity routeCity) {
        routeCityRepository.addRouteCity(routeCity);
    }

    @Override
    public List<RouteCity> getAllRouteCities() {
        return routeCityRepository.getAllRouteCities();
    }

    @Override
    public void updateRouteCity(RouteCity routeCity) {
        routeCityRepository.updateRouteCity(routeCity);
    }

    @Override
    public void deleteRouteCityById(Long routeId) {
        routeCityRepository.deleteRouteCity(routeId);
    }


}
