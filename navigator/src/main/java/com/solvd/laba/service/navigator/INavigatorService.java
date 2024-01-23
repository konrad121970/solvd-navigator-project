package com.solvd.laba.service.navigator;

import com.solvd.laba.model.City;

import java.util.List;

public interface INavigatorService {

    List<City> findShortestPath(City startCity, City endCity);

    List<City> findShortestPathWithStop(City startCity, City intermediateCity, City endCity);

    int getRoadLength(List<City> cities);
}
