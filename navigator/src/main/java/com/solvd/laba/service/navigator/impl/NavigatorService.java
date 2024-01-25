package com.solvd.laba.service.navigator.impl;

import com.solvd.laba.model.City;
import com.solvd.laba.model.Route;
import com.solvd.laba.service.city.ICityService;
import com.solvd.laba.service.city.IRoadService;
import com.solvd.laba.service.city.impl.CityService;
import com.solvd.laba.service.city.impl.RoadService;
import com.solvd.laba.service.navigator.INavigatorService;
import com.solvd.laba.service.route.IRouteService;
import com.solvd.laba.service.route.impl.RouteService;

import java.awt.*;
import java.util.*;
import java.util.List;

public class NavigatorService implements INavigatorService {

    private Graph graph;
    private ICityService cityService;
    private IRouteService routeService;
    private IRoadService roadService;


    public NavigatorService() {
        cityService = new CityService();
        routeService = new RouteService();
        roadService = new RoadService();
        graph = new Graph();
        graph.addCities(cityService.getAllCities());
    }

    @Override
    public List<City> findShortestPath(City startCity, City endCity) {
        Optional<Route> existingRoute = routeService.getRouteBetweenTwoCities(startCity.getId(), endCity.getId());
        if(existingRoute.isPresent()){
            Map<Integer, City> cityOrder = existingRoute.get().getCityOrder();

            List<City> cityList = new ArrayList<>(cityOrder.values());

            cityList.forEach(city -> {
                city.setRoads(roadService.findRoadsByStartCityId(city.getId()));
            });

            return cityList;

        } else {
            Route route = new Route();
            Map<Integer, City> cityOrder = new HashMap<>();

            int order = 1;

            List<City> cities =  graph.findShortestPath(startCity.getId(), endCity.getId());

            for (City city : cities) {
                cityOrder.put(order, city);
                order++;
            }

            route.setCityOrder(cityOrder);
            route.setDistance(getRoadLength(cities));
            routeService.createRoute(route);

            return cities;
        }
    }

    @Override
    public List<City> findShortestPathWithStop(City startCity, City intermediateCity, City endCity){

        List<City> pathToIntermediate = graph.findShortestPath(startCity.getId(), intermediateCity.getId());
        List<City> pathToEnd = graph.findShortestPath(intermediateCity.getId(), endCity.getId());

        if (startCity.getId().equals(intermediateCity.getId()) ||
                intermediateCity.getId().equals(endCity.getId()) ||
                startCity.getId().equals(endCity.getId())) {

            throw new IllegalArgumentException("Start, stop, and end cities must be different.");
        }

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
