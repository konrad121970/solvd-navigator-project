package com.solvd.laba.persistence.city.impl;

import com.solvd.laba.model.Road;
import com.solvd.laba.persistence.ConnectionPool;
import com.solvd.laba.persistence.city.RoadRepository;
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
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(INSERT_ROAD_QUERY)) {
                stmt.setLong(1, road.getStartCityId());
                stmt.setLong(2, road.getEndCityId());
                stmt.setInt(3, road.getDistance());
                stmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Rollback failed: {}", ex.getMessage());
            }
            LOGGER.error("Error creating road: {}", e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("Auto-commit reset failed: {}", e.getMessage());
            }
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public List<Road> findByStartCityId(Long cityId) {
        List<Road> roads = new ArrayList<>();
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_ROADS_BY_START_CITY_ID_QUERY)) {
                stmt.setLong(1, cityId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    roads.add(mapRow(rs));
                }
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Rollback failed: {}", ex.getMessage());
            }
            LOGGER.error("Error finding roads by city ID: {}", e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("Auto-commit reset failed: {}", e.getMessage());
            }
            CONNECTION_POOL.releaseConnection(connection);
        }
        return roads;
    }

    @Override
    public Road findRoadByStartAndEndCity(Long startCityId, Long endCityId) {
        Road road = null;
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_ROADS_BY_START_CITY_ID_QUERY)) {
                stmt.setLong(1, startCityId);
                stmt.setLong(2, endCityId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    road = mapRow(rs);
                }
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Rollback failed: {}", ex.getMessage());
            }
            LOGGER.error("Error finding road: {}", e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("Auto-commit reset failed: {}", e.getMessage());
            }
            CONNECTION_POOL.releaseConnection(connection);
        }
        return road;
    }


    @Override
    public void updateRoadDistance(Long startCityId, Long endCityId, int newDistance) {
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(UPDATE_ROADS_QUERY)) {
                stmt.setInt(1, newDistance);
                stmt.setLong(2, startCityId);
                stmt.setLong(3, endCityId);
                stmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Rollback failed: {}", ex.getMessage());
            }
            LOGGER.error("Error updating road: {}", e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("Auto-commit reset failed: {}", e.getMessage());
            }
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void deleteRoad(Long startCityId, Long endCityId) {
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(DELETE_ROADS_QUERY)) {
                stmt.setLong(1, startCityId);
                stmt.setLong(2, endCityId);
                stmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Rollback failed: {}", ex.getMessage());
            }
            LOGGER.error("Error deleting road: {}", e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("Auto-commit reset failed: {}", e.getMessage());
            }
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    private Road mapRow(ResultSet rs) throws SQLException {
        Long startCityId = rs.getLong("road_start_city_id");
        Long endCityId = rs.getLong("road_end_city_id");
        int distance = rs.getInt("road_distance");
        return new Road(startCityId, endCityId, distance);
    }
}