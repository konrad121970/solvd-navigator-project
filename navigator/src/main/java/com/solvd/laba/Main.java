package com.solvd.laba;

import com.solvd.laba.model.City;
import com.solvd.laba.service.city.ICityService;
import com.solvd.laba.service.city.impl.CityService;
import com.solvd.laba.service.navigator.INavigatorService;
import com.solvd.laba.service.navigator.impl.NavigatorService;



public class Main {
    public static void main(String[] args) {
        ICityService cityService = new CityService();
        INavigatorService navigatorService = new NavigatorService();

        City city1 = cityService.findCityById(1L);
        City city2 = cityService.findCityById(5L);

        System.out.println(navigatorService.findShortestPath(city1, city2));

        System.out.println();

    }
}