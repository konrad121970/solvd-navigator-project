package com.solvd.laba.persistence;

import com.solvd.laba.model.Road;
import java.util.List;

public interface RoadRepository {

    void create(Road road);

    Road findRoadByStartAndEndCity(Long startCityId, Long endCityId);

    List<Road> findByCityId(Long cityId);

    void updateRoadDistance(Long startCityId, Long endCityId, int newDistance);

    void deleteRoad(Long startCityId, Long endCityId);
}
