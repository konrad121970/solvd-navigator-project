package com.solvd.laba.persistence;

import com.solvd.laba.model.Road;
import java.util.List;

public interface RoadRepository {

    void create(Road road);

    Road findRoadByStartAndEndCity(int startCityId, int endCityId);

    List<Road> findByCityId(int cityId);

    void updateRoadDistance(int startCityId, int endCityId, int newDistance);

    void deleteRoad(int startCityId, int endCityId);
}
