package com.solvd.laba.service.navigator;

import com.solvd.laba.model.City;

import java.util.List;

public interface ISavedRoutesService {

    List<City> getSavedRoute(Long startCityId, Long endCityId);

    void saveRoute(Long startCityId, Long endCityId, List<City> route);

}
