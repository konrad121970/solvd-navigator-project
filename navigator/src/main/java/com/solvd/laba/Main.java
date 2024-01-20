package com.solvd.laba;

import com.solvd.laba.model.City;
import com.solvd.laba.model.Road;
import com.solvd.laba.persistence.city.CityRepository;
import com.solvd.laba.persistence.city.RoadRepository;
import com.solvd.laba.persistence.city.impl.CityRepositoryImpl;
import com.solvd.laba.persistence.city.impl.RoadRepositoryImpl;

public class Main {
    public static void main(String[] args) {

        CityRepository cityRepository = new CityRepositoryImpl();
        RoadRepository roadRepository = new RoadRepositoryImpl();

        City city = new City("Bialowieza", 12.0,13.0, null);
        cityRepository.create(city);
        cityRepository.deleteById(city.getId());

        Road road = new Road(1L,4L, 200);
        roadRepository.create(road);
        roadRepository.deleteRoad(road.getStartCityId(), road.getEndCityId());

    }
}