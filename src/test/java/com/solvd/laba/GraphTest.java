package com.solvd.laba;

import com.solvd.laba.model.City;
import com.solvd.laba.service.navigator.impl.Graph;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class GraphTest {
    private Graph graph;

    @BeforeClass
    public void setUp() {
        graph = new Graph();
    }

    @Test
    public void testAddCity() {
        City city = City.builder()
                .setId(10L)
                .setName("Andrzejów")
                .setXPos(20.0)
                .setYPos(45.0)
                .build();

        graph.addCity(city);

        Map<Long, City> cities = graph.getCities();
        Assert.assertTrue(cities.containsKey(city.getId()));
    }

}
