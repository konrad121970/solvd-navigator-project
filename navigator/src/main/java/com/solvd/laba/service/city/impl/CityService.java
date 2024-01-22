package com.solvd.laba.service.city.impl;

import com.solvd.laba.model.City;
import com.solvd.laba.persistence.city.CityRepository;
import com.solvd.laba.persistence.city.impl.CityRepositoryImpl;
import com.solvd.laba.service.city.ICityService;

public class CityService implements ICityService {

    private CityRepository cityRepository;
    private RoadService roadService;

    public CityService() {
        cityRepository = new CityRepositoryImpl();
        roadService = new RoadService();
    }

    @Override
    public void createCity(City city) {
        cityRepository.create(city);

        if(city.getRoads() != null){
            city.getRoads().forEach(road -> {
                roadService.createRoad(road);
            });
        }
    }

    @Override
    public City findCityById(Long id) {
        City city = cityRepository.findById(id);
        city.setRoads(roadService.findRoadsByStartCityId(id));
        return city;
    }

    @Override
    public void updateCity(City city) {
        cityRepository.updateById(city);
    }

    @Override
    public void deleteCityById(Long id) {
        cityRepository.deleteById(id);
    }


}
