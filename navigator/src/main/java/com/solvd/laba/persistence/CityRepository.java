package com.solvd.laba.persistence;

import com.solvd.laba.model.City;

public interface CityRepository {


    void create(City city);

    City findById(Long id);

    void updateById(City city);

    void deleteById(Long id);
}
