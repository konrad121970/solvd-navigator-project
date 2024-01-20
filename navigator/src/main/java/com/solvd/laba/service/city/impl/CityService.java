package com.solvd.laba.service.city.impl;

import com.solvd.laba.model.Road;
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
}
