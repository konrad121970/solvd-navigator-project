package com.solvd.laba.service.navigator.impl;

import com.solvd.laba.model.City;
import com.solvd.laba.model.Road;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private Map<Long, City> cities;

    public Graph() {
        this.cities = new HashMap<>();
    }

    public void addCity(City city) {
        cities.put(city.getId(), city);
    }

    public void addCities(List<City> cities){
        cities.forEach(city -> addCity(city));
    }

    public List<City> findShortestPath(Long startCityId, Long endCityId) {
        Map<Long, Integer> distances = new HashMap<>();
        Map<Long, Long> predecessors = new HashMap<>();
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (City city : cities.values()) {
            distances.put(city.getId(), Integer.MAX_VALUE);
            predecessors.put(city.getId(), null);
        }

        distances.put(startCityId, 0);
        priorityQueue.add(startCityId);

        while (!priorityQueue.isEmpty()) {
            Long currentCityId = priorityQueue.poll();

            for (Road road : cities.get(currentCityId).getRoads()) {
                Long neighborCityId = road.getEndCityId();
                int newDistance = distances.get(currentCityId) + road.getDistance();

                if (newDistance < distances.get(neighborCityId)) {
                    distances.put(neighborCityId, newDistance);
                    predecessors.put(neighborCityId, currentCityId);
                    priorityQueue.add(neighborCityId);
                }
            }
        }

        List<Long> shortestPath = new ArrayList<>();
        Long currentCityId = endCityId;
        while (currentCityId != null) {
            shortestPath.add(currentCityId);
            currentCityId = predecessors.get(currentCityId);
        }
        Collections.reverse(shortestPath);

        return shortestPath.stream().map(cities::get).collect(Collectors.toList());
    }

    public Map<Long, City> getCities() {
        return cities;
    }
}