package com.solvd.laba.persistence.city;

import com.solvd.laba.model.City;

import java.util.List;

public interface ICityRepository {


    void create(City city);

    City findById(Long id);

    List<City> getAllCities();

    void updateById(City city);

    void deleteById(Long id);
}
