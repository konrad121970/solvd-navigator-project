package com.solvd.laba.service.city;

import com.solvd.laba.model.Road;

import java.util.List;

public interface IRoadService {

    void createRoad(Road road);

    List<Road> findRoadsByStartCityId(Long cityId);

    Road findRoadByStartAndEndCity(Long startCityId, Long endCityId);

    void updateRoadDistance(Long startCityId, Long endCityId, int newDistance);

    void deleteRoad(Long startCityId, Long endCityId);
}
