package com.solvd.laba.service.city;

import com.solvd.laba.model.City;

import java.util.List;

public interface ICityService {
    void createCity(City city);

    City findCityById(Long id);

    List<City> getAllCities();

    void updateCity(City city);

    void deleteCityById(Long id);
}
