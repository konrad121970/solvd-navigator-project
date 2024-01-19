package com.solvd.laba.persistence.impl;

import com.solvd.laba.model.Road;
import com.solvd.laba.persistence.ConnectionPool;
import com.solvd.laba.persistence.RoadRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoadRepositoryImpl implements RoadRepository {
    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private static final String INSERT_ROAD_QUERY = "INSERT INTO roads (start_city_id, end_city_id, distance) VALUES (?, ?, ?)";
    private static final String SELECT_ROADS_BY_START_CITY_ID_QUERY = "SELECT start_city_id as road_start_city_id, end_city_id as road_end_city_id, distance as road_distance FROM roads " +
            "WHERE start_city_id = ?";
    private static final String DELETE_ROADS_QUERY = "DELETE FROM roads WHERE start_city_id = ? OR end_city_id = ?";
    private static final String UPDATE_ROADS_QUERY = "UPDATE roads SET distance = ? WHERE start_city_id = ? AND end_city_id = ?";

    @Override
    public void create(Road road) {
        try (Connection connection = CONNECTION_POOL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_ROAD_QUERY)) {
            stmt.setLong(1, road.getStartCityId());
            stmt.setLong(2, road.getEndCityId());
            stmt.setInt(3, road.getDistance());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error creating road: {}", e.getMessage());
        }
    }

    @Override
    public List<Road> findByCityId(Long cityId) {
        List<Road> roads = new ArrayList<>();
        try (Connection connection = CONNECTION_POOL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ROADS_BY_START_CITY_ID_QUERY)) {
            stmt.setLong(1, cityId);
//            stmt.setLong(2, cityId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    roads.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding roads by city ID: {}", e.getMessage());
        }
        return roads;
    }

    public Road findRoadByStartAndEndCity(Long startCityId, Long endCityId) {
        Road road = null;
        String sql = "SELECT * FROM roads WHERE start_city_id = ? AND end_city_id = ?";
        try (Connection connection = CONNECTION_POOL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, startCityId);
            stmt.setLong(2, endCityId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                road = mapRow(rs);
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding road: {}", e.getMessage());
        }
        return road;
    }

    public void updateRoadDistance(Long startCityId, Long endCityId, int newDistance) {
        try (Connection connection = CONNECTION_POOL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_ROADS_QUERY)) {
            stmt.setInt(1, newDistance);
            stmt.setLong(2, startCityId);
            stmt.setLong(3, endCityId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error updating road: {}", e.getMessage());
        }
    }

    public void deleteRoad(Long startCityId, Long endCityId) {
        try (Connection connection = CONNECTION_POOL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_ROADS_QUERY)) {
            stmt.setLong(1, startCityId);
            stmt.setLong(2, endCityId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error deleting road: {}", e.getMessage());
        }
    }


    private Road mapRow(ResultSet rs) throws SQLException {
        Long startCityId = rs.getLong("road_start_city_id");
        Long endCityId = rs.getLong("road_end_city_id");
        int distance = rs.getInt("road_distance");
        return new Road(startCityId, endCityId, distance);
    }
}