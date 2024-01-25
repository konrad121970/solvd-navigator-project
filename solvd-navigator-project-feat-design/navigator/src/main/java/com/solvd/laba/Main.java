package com.solvd.laba;

import com.solvd.laba.model.City;
import com.solvd.laba.model.Route;
import com.solvd.laba.persistence.route.IRouteCityRepository;
import com.solvd.laba.service.city.ICityService;
import com.solvd.laba.service.city.impl.CityService;
import com.solvd.laba.service.navigator.INavigatorService;
import com.solvd.laba.service.navigator.impl.NavigatorService;
import com.solvd.laba.service.route.IRouteCityService;
import com.solvd.laba.service.route.IRouteService;
import com.solvd.laba.service.route.impl.RouteCityService;
import com.solvd.laba.service.route.impl.RouteService;

import java.util.List;
import java.util.Optional;


public class Main {
    public static void main(String[] args) {
        ICityService cityService = new CityService();
        INavigatorService navigatorService = new NavigatorService();
        IRouteService routeService = new RouteService();
        IRouteCityService routeCityService = new RouteCityService();

        City city1 = cityService.findCityById(1L);
        City city2 = cityService.findCityById(4L);
        City city3 = cityService.findCityById(3L);

        List<City> asd = navigatorService.findShortestPath(city1, city2);
        List<City> asd2 = navigatorService.findShortestPathWithStop(city1, city2, city3);

        //System.out.println(navigatorService.getRoadLength(navigatorService.findShortestPath(city1, city2)));
        System.out.println();

    }
}