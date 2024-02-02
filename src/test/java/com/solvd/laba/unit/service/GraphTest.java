package com.solvd.laba.unit.service;

import com.solvd.laba.model.City;
import com.solvd.laba.model.Road;
import com.solvd.laba.service.navigator.impl.Graph;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphTest {
    private Graph graph;

    @BeforeClass
    public void setUp() {
        graph = new Graph();

        City cityA = City.builder().setId(1L).setName("City A").build();
        City cityB = City.builder().setId(2L).setName("City B").build();
        City cityC = City.builder().setId(3L).setName("City C").build();
        City cityD = City.builder().setId(4L).setName("City D").build();

        Road roadAB = new Road(1L, 2L, 10);
        Road roadAD = new Road(2L, 4L, 40);

        Road roadBC = new Road(2L, 3L, 15);
        Road roadBD = new Road(2L, 4L, 25);

        cityA.getRoads().add(roadAB);
        cityA.getRoads().add(roadAD);
        cityB.getRoads().add(roadBC);
        cityB.getRoads().add(roadBD);

        graph.addCity(cityA);
        graph.addCity(cityB);
        graph.addCity(cityC);
        graph.addCity(cityD);
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
        Assert.assertTrue(cities.containsKey(city.getId()), "City should be added to the graph");
    }

    @Test
    public void testFindShortestPath() {
        Long startCityId = 1L;
        Long endCityId = 4L;

        List<City> foundShortestPath = graph.findShortestPath(startCityId, endCityId);

        Assert.assertNotNull(foundShortestPath, "Shortest path should not be null");
    }

    @Test
    public void testFindCorrectShortestPath() {
        List<String> expectedPathNames = List.of("City A", "City B", "City D");
        Long startCityId = 1L;
        Long endCityId = 4L;

        List<City> foundShortestPath = graph.findShortestPath(startCityId, endCityId);
        List<String> foundPathNames = foundShortestPath.stream().map(City::getName).toList();

        Assert.assertNotNull(foundShortestPath, "Shortest path should not be null");
        Assert.assertEquals(foundPathNames, expectedPathNames, "Found path should match expected path");
    }

    @Test
    public void testFindShortestPathWithInvalidEndCity() {
        Long startCityId = 1L;
        Long endCityId = 99999999999L;

        List<City> fullPath = graph.findShortestPath(startCityId, endCityId);

        Assert.assertNotNull(fullPath, "Path should not be null");
        Assert.assertTrue(fullPath.isEmpty(), "Path should be empty for invalid end city");
    }

    @Test
    public void testFindShortestPathWithInvalidStartCity() {
        Long startCityId = 1999999999999999L;
        Long endCityId = 1L;

        List<City> fullPath = graph.findShortestPath(startCityId, endCityId);

        Assert.assertNotNull(fullPath, "Path should not be null");
        Assert.assertTrue(fullPath.isEmpty(), "Path should be empty for invalid start city");
    }
}
