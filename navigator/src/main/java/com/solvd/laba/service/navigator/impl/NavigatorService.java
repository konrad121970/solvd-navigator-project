package com.solvd.laba.service.navigator.impl;

import com.solvd.laba.model.City;
import com.solvd.laba.service.city.ICityService;
import com.solvd.laba.service.city.impl.CityService;
import com.solvd.laba.service.navigator.INavigatorService;
import com.solvd.laba.service.navigator.ISavedRoutesService;

import java.util.*;

public class NavigatorService implements INavigatorService {

    private Graph graph;
    private ICityService cityService;
    private ISavedRoutesService savedRoutesService;


    public NavigatorService() {
        cityService = new CityService();
        savedRoutesService = new SavedRoutesService();
        graph = new Graph();
        graph.addCities(cityService.getAllCities());
    }

    @Override
    public List<City> findShortestPath(City startCity, City endCity) {

        List<City> savedRoute = savedRoutesService.getSavedRoute(startCity.getId(), endCity.getId());
        if (savedRoute != null && !savedRoute.isEmpty()) {
            return savedRoute;
        } else {
            List<City> shortestPath = graph.findShortestPath(startCity.getId(), endCity.getId());
            savedRoutesService.saveRoute(startCity.getId(), endCity.getId(), shortestPath);
            return shortestPath;
        }
    }

    @Override
    public List<City> findShortestPathWithStop(City startCity, City intermediateCity, City endCity){

        List<City> pathToIntermediate = graph.findShortestPath(startCity.getId(), intermediateCity.getId());
        List<City> pathToEnd = graph.findShortestPath(intermediateCity.getId(), endCity.getId());

        if (pathToIntermediate.isEmpty() || pathToEnd.isEmpty()) {

            // So basically here I return the empty path if either path is not found
            return new ArrayList<>();
        }

        // So that we don't have the intermediate city twice
        pathToIntermediate.remove(pathToIntermediate.size() - 1);
        // So then here join the two paths
        List<City> fullPath = new ArrayList<>(pathToIntermediate);
        fullPath.addAll(pathToEnd);

        return fullPath;
    }

    @Override
    public int getRoadLength(List<City> cities){
        return graph.getRoadLength(cities);
    }
}
