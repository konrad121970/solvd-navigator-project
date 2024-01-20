package com.solvd.laba.service.city;

import com.solvd.laba.model.City;

public interface ICityService {
    void createCity(City city);

    City findCityById(Long id);

    void updateCity(City city);

    void deleteCityById(Long id);
}
