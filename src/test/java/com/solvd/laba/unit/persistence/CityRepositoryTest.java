package com.solvd.laba.unit.persistence;

import com.solvd.laba.model.City;
import com.solvd.laba.persistence.city.ICityRepository;
import com.solvd.laba.persistence.city.impl.CityRepositoryImpl;
import com.solvd.laba.persistence.ConnectionPool;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class CityRepositoryTest {

    private ICityRepository cityRepository;
    @BeforeClass
    public void setUp() {
        cityRepository = new CityRepositoryImpl();
    }

    @Test
    public void testCreateCity() {
        City city = City.builder().setName("TestCity").setXPos(1.0).setYPos(2.0).build();
        cityRepository.create(city);
        Assert.assertNotNull(city.getId());
    }

    @Test
    public void testFindCityById() {
        Long cityId = 1L; // Change to a valid city ID
        City city = cityRepository.findById(cityId);
        Assert.assertNotNull(city);
        Assert.assertEquals(city.getId(), cityId);
    }

    @Test
    public void testGetAllCities() {
        List<City> cities = cityRepository.getAllCities();
        Assert.assertNotNull(cities);
        Assert.assertFalse(cities.isEmpty());
    }

    @Test
    public void testUpdateCity() {
        Long cityId = 1L; // Change to a valid city ID
        City city = cityRepository.findById(cityId);
        Assert.assertNotNull(city);

        String updatedName = "UpdatedCity";
        city.setName(updatedName);
        cityRepository.updateById(city);

        City updatedCity = cityRepository.findById(cityId);
        Assert.assertEquals(updatedCity.getName(), updatedName);
    }

    @Test
    public void testDeleteCity() {
        City city = City.builder().setName("ToDeleteCity").setXPos(1.0).setYPos(2.0).build();
        cityRepository.create(city);

        Long cityId = city.getId();
        Assert.assertNotNull(cityId);

        cityRepository.deleteById(cityId);
        City deletedCity = cityRepository.findById(cityId);
        Assert.assertNull(deletedCity);
    }


}

