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
    private static final String SELECT_ROAD_BY_ID_QUERY = "SELECT id, start_city_id, end_city_id, distance FROM roads WHERE id = ?";
    private static final String SELECT_ROADS_BY_CITY_ID_QUERY = "SELECT id, start_city_id, end_city_id, distance FROM roads " +
            "WHERE start_city_id = ? OR end_city_id = ?";
    private static final String UPDATE_ROAD_QUERY = "UPDATE roads SET start_city_id = ?, end_city_id = ?, distance = ? WHERE id = ?";
    private static final String DELETE_ROAD_QUERY = "DELETE FROM roads WHERE id = ?";

    @Override
    public void create(Road road) {
        try (Connection connection = CONNECTION_POOL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_ROAD_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, road.getStartCityId());
            stmt.setInt(2, road.getEndCityId());
            stmt.setInt(3, road.getDistance());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    road.setId((long) generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error creating road: {}", e.getMessage());
        }
    }

    @Override
    public Road findById(int id) {
        Road road = null;
        try (Connection connection = CONNECTION_POOL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ROAD_BY_ID_QUERY)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    road = mapRow(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding road by ID: {}", e.getMessage());
        }
        return road;
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

    @Override
    public void update(Road road) {
        try (Connection connection = CONNECTION_POOL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_ROAD_QUERY)) {
            stmt.setInt(1, road.getStartCityId());
            stmt.setInt(2, road.getEndCityId());
            stmt.setInt(3, road.getDistance());
            stmt.setInt(4, Math.toIntExact(road.getId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error updating road: {}", e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = CONNECTION_POOL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_ROAD_QUERY)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error deleting road: {}", e.getMessage());
        }
    }

    private Road mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int startCityId = rs.getInt("start_city_id");
        int endCityId = rs.getInt("end_city_id");
        int distance = rs.getInt("distance");
        return new Road((long) id, startCityId, endCityId, distance);
    }
}
