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

public class RoadDAO implements RoadRepository {
    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    private static final String INSERT_ROAD_QUERY = "INSERT INTO roads (start_city_id, end_city_id, distance) VALUES (?, ?, ?)";
    private static final String SELECT_ROADS_BY_CITY_ID_QUERY = "SELECT start_city_id, end_city_id, distance FROM roads " +
            "WHERE start_city_id = ? OR end_city_id = ?";


    @Override
    public void create(Road road) {
        try (Connection connection = CONNECTION_POOL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_ROAD_QUERY)) {
            stmt.setInt(1, road.getStartCityId());
            stmt.setInt(2, road.getEndCityId());
            stmt.setInt(3, road.getDistance());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error creating road: {}", e.getMessage());
        }
    }

    @Override
    public List<Road> findByCityId(int cityId) {
        List<Road> roads = new ArrayList<>();
        try (Connection connection = CONNECTION_POOL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ROADS_BY_CITY_ID_QUERY)) {
            stmt.setInt(1, cityId);
            stmt.setInt(2, cityId);
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

    public Road findRoadByStartAndEndCity(int startCityId, int endCityId) {
        Road road = null;
        String sql = "SELECT * FROM roads WHERE start_city_id = ? AND end_city_id = ?";
        try (Connection connection = CONNECTION_POOL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, startCityId);
            stmt.setInt(2, endCityId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                road = mapRow(rs);
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding road: {}", e.getMessage());
        }
        return road;
    }

    public void updateRoadDistance(int startCityId, int endCityId, int newDistance) {
        String sql = "UPDATE roads SET distance = ? WHERE start_city_id = ? AND end_city_id = ?";
        try (Connection connection = CONNECTION_POOL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newDistance);
            stmt.setInt(2, startCityId);
            stmt.setInt(3, endCityId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error updating road: {}", e.getMessage());
        }
    }

    public void deleteRoad(int startCityId, int endCityId) {
        String sql = "DELETE FROM roads WHERE start_city_id = ? AND end_city_id = ?";
        try (Connection connection = CONNECTION_POOL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, startCityId);
            stmt.setInt(2, endCityId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error deleting road: {}", e.getMessage());
        }
    }


    private Road mapRow(ResultSet rs) throws SQLException {
        int startCityId = rs.getInt("start_city_id");
        int endCityId = rs.getInt("end_city_id");
        int distance = rs.getInt("distance");
        return new Road(startCityId, endCityId, distance);
    }
}