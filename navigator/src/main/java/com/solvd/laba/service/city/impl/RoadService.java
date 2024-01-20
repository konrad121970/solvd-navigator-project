package com.solvd.laba.service.city.impl;

import com.solvd.laba.persistence.city.RoadRepository;
import com.solvd.laba.persistence.city.impl.RoadRepositoryImpl;
import com.solvd.laba.service.city.IRoadService;

public class RoadService implements IRoadService {

    private RoadRepository roadRepository;
    private CityService cityService;

    public RoadService() {
        roadRepository = new RoadRepositoryImpl();
        cityService = new CityService();
    }

}
