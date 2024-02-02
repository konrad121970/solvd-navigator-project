package com.solvd.laba.unit.service;

import com.solvd.laba.model.City;
import com.solvd.laba.service.city.ICityService;
import com.solvd.laba.service.city.impl.CityService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CityServiceTest {
    private ICityService cityService = new CityService();

    @Test
    public void createNewCity(){
        City city = City.builder()
                .setName("Kuraszewo")
                .setXPos(10.0)
                .setYPos(20.0)
                .build();

        cityService.createCity(city);

        City addedCity = cityService.findCity(city);
        Assert.assertNotNull(addedCity);
        Assert.assertEquals(addedCity.getName(), "Kuraszewo");
    }

    @Test
    public void deleteCity(){
        City city = City.builder()
                .setName("Andrzejów")
                .setXPos(20.0)
                .setYPos(45.0)
                .build();

        cityService.createCity(city);
        cityService.deleteCityById(city.getId());

        Assert.assertNull(cityService.findCityById(city.getId()));
    }

    @Test
    public void updateCity(){
        City city = City.builder()
                .setName("Hajnówka")
                .setXPos(20.0)
                .setYPos(45.0)
                .build();

        cityService.createCity(city);
        city.setName("Bialystok");

        cityService.updateCity(city);
        City updatedCity = cityService.findCityById(city.getId());

        Assert.assertNotNull(updatedCity);
        Assert.assertEquals(updatedCity.getName(), "Bialystok");
    }
}
