package com.solvd.laba;

import com.solvd.laba.model.City;
import com.solvd.laba.persistence.CityRepository;
import com.solvd.laba.persistence.impl.CityRepositoryImpl;

public class Main {
    public static void main(String[] args) {

        CityRepository cityRepository = new CityRepositoryImpl();

        City city = new City("Bialowieza", 12.0,13.0, null);

        cityRepository.create(city);

        cityRepository.deleteById(city.getId());

    }
}