package com.solvd.laba.service.navigator.impl;

import com.solvd.laba.model.City;
import com.solvd.laba.service.city.ICityService;
import com.solvd.laba.service.city.IRoadService;
import com.solvd.laba.service.city.impl.CityService;
import com.solvd.laba.service.city.impl.RoadService;
import com.solvd.laba.service.navigator.INavigatorService;

import java.util.*;

public class NavigatorService implements INavigatorService {

    private Graph graph;
    private ICityService cityService;
//    private IRoadService roadService;

    public NavigatorService() {
        cityService = new CityService();
//        roadService = new RoadService();

        graph = new Graph();
        graph.addCities(cityService.getAllCities());
    }

    @Override
    public List<City> findShortestPath(City startCity, City endCity) {
        // TODO: add savedRoutesService
//        if(savedRoutesService.findByIds(startCity, endCity) != null){
//
//        }
//        else {
            return graph.findShortestPath(startCity.getId(), endCity.getId());
//        }
    }

    // TODO: implement findShortestPathWithStop() method
    @Override
    public List<City> findShortestPathWithStop(City startCity, City intermediateCity, City endCity){
        return null;
    }


}
