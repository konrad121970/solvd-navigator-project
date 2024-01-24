package com.solvd.laba.service.city.impl;

import com.solvd.laba.model.Road;
import com.solvd.laba.persistence.city.IRoadRepository;
import com.solvd.laba.persistence.city.impl.RoadRepositoryImpl;
import com.solvd.laba.service.city.IRoadService;

import java.util.List;

public class RoadService implements IRoadService {

    private IRoadRepository roadRepository;

    public RoadService() {
        roadRepository = new RoadRepositoryImpl();
    }

    @Override
    public void createRoad(Road road) {
        roadRepository.create(road);
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
