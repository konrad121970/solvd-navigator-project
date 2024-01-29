package com.solvd.laba.service.city.impl;

import com.solvd.laba.config.Config;
import com.solvd.laba.model.Road;
import com.solvd.laba.model.Route;
import com.solvd.laba.persistence.RepositoryFactory;
import com.solvd.laba.persistence.city.IRoadRepository;
import com.solvd.laba.persistence.city.impl.RoadRepositoryImpl;
import com.solvd.laba.persistence.route.IRouteRepository;
import com.solvd.laba.service.city.IRoadService;
import com.solvd.laba.service.route.IRouteService;
import com.solvd.laba.service.route.impl.RouteService;

import java.util.List;

public class RoadService implements IRoadService {

    private IRoadRepository roadRepository;
    private IRouteService routeService;

    public RoadService() {
        roadRepository = RepositoryFactory.createRoadRepository(Config.IMPL.getValue());
        routeService = new RouteService();
    }

    @Override
    public void createRoad(Road road) {
        roadRepository.create(road);
        Long startCityId = road.getStartCityId();
        List<Route> routes = routeService.findRoutesByStartCityId(startCityId);

        routes.forEach(route -> {
            routeService.deleteRouteById(route.getId());
        });
    }

    @Override
    public List<Road> findRoadsByStartCityId(Long cityId) {
        return roadRepository.findByStartCityId(cityId);
    }

    @Override
    public Road findRoadByStartAndEndCity(Long startCityId, Long endCityId) {
        return roadRepository.findRoadByStartAndEndCity(startCityId, endCityId);
    }

    @Override
    public void updateRoadDistance(Long startCityId, Long endCityId, int newDistance) {
        roadRepository.updateRoadDistance(startCityId, endCityId, newDistance);
    }

    @Override
    public void deleteRoad(Long startCityId, Long endCityId) {
        roadRepository.deleteRoad(startCityId, endCityId);
    }

}
