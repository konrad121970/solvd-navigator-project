package com.solvd.laba.persistence;

import com.solvd.laba.model.City;
import com.solvd.laba.model.Road;

import java.util.List;

public interface CityRepository {


    void create(City city, List<Road> roads);

    City findById(int id);

    void updateById(int id, City city);

    void deleteById(int id);
}
