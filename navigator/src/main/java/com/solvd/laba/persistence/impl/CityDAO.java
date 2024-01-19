package com.solvd.laba.persistence.impl;

import com.solvd.laba.model.City;
import com.solvd.laba.model.Road;
import com.solvd.laba.persistence.CityRepository;
import com.solvd.laba.persistence.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAO implements CityRepository {
    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    private static final String CREATE_CITY_QUERY = "INSERT INTO cities (name, x_pos, y_pos) VALUES (?, ?, ?)";
    private static final String CREATE_ROAD_QUERY = "INSERT INTO roads (start_city_id, end_city_id, distance) VALUES (?, ?, ?)";
    private static final String GET_CITY_BY_ID_QUERY = "SELECT id, name, x_pos, y_pos FROM cities WHERE id = ?";
    private static final String GET_ROADS_BY_CITY_ID_QUERY = "SELECT id, start_city_id, end_city_id, distance FROM roads " +
            "WHERE start_city_id = ? OR end_city_id = ?";
    private static final String UPDATE_CITY_QUERY = "UPDATE cities SET name = ?, x_pos = ?, y_pos = ? WHERE id = ?";
    private static final String DELETE_CITY_QUERY = "DELETE FROM cities WHERE id = ?";
    private static final String DELETE_ROADS_QUERY = "DELETE FROM roads WHERE start_city_id = ? OR end_city_id = ?";

    @Override
    public void create(City city, List<Road> roads) {
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            connection.setAutoCommit(false);

            // Insert the city and get the generated ID
            try (PreparedStatement cityStmt = connection.prepareStatement(CREATE_CITY_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                cityStmt.setString(1, city.getName());
                cityStmt.setDouble(2, city.getxPos());
                cityStmt.setDouble(3, city.getyPos());
                int affectedRows = cityStmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating city failed, no rows affected.");
                }

                try (ResultSet generatedKeys = cityStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        city.setId((long) generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating city failed, no ID obtained.");
                    }
                }
            }

            // Insert the roads
            if (roads != null) {
                for (Road road : roads) {
                    try (PreparedStatement roadStmt = connection.prepareStatement(CREATE_ROAD_QUERY)) {
                        roadStmt.setInt(1, road.getStartCityId());
                        roadStmt.setInt(2, road.getEndCityId());
                        roadStmt.setInt(3, road.getDistance());
                        roadStmt.executeUpdate();
                    }
                }
            }

            connection.commit();
            LOGGER.info("City created: {}", city);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Rollback failed: {}", ex.getMessage());
            }
            LOGGER.error("Creating city failed: {}", e.getMessage());
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
    public City findById(int id) {
        Connection connection = CONNECTION_POOL.getConnection();
        City city = null;
        try {
            connection.setAutoCommit(false);

            // Get the city by id
            try (PreparedStatement stmt = connection.prepareStatement(GET_CITY_BY_ID_QUERY)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    // Populate city and associated roads
                    city = mapCityRow(connection, rs);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Rollback failed: {}", ex.getMessage());
            }
            LOGGER.error("Query failed: {}", e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("Auto-commit reset failed: {}", e.getMessage());
            }
            CONNECTION_POOL.releaseConnection(connection);
        }
        return city;
    }


    @Override
    public void updateById(int id, City city) {
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(UPDATE_CITY_QUERY)) {
                stmt.setString(1, city.getName());
                stmt.setDouble(2, city.getxPos());
                stmt.setDouble(3, city.getyPos());
                stmt.setInt(4, id);
                stmt.executeUpdate();
            }

            connection.commit();
            LOGGER.info("City updated: {}", city);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Rollback failed: {}", ex.getMessage());
            }
            LOGGER.error("Update failed: {}", e.getMessage());
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
    public void deleteById(int id) {
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            connection.setAutoCommit(false);

            // Delete roads associated with the city
            try (PreparedStatement stmt = connection.prepareStatement(DELETE_ROADS_QUERY)) {
                stmt.setInt(1, id);
                stmt.setInt(2, id);
                stmt.executeUpdate();
            }

            // Delete the city
            try (PreparedStatement stmt = connection.prepareStatement(DELETE_CITY_QUERY)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            connection.commit();
            LOGGER.info("City deleted with id: {}", id);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Rollback failed: {}", ex.getMessage());
            }
            LOGGER.error("Delete failed: {}", e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("Auto-commit reset failed: {}", e.getMessage());
            }
            CONNECTION_POOL.releaseConnection(connection);
        }
    }
    private City mapCityRow(Connection connection, ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double xPos = rs.getDouble("x_pos");
        double yPos = rs.getDouble("y_pos");
        List<Road> roads = getRoadsForCity(connection, id);
        return new City((long) id, name, xPos, yPos, roads);
    }

    private List<Road> getRoadsForCity(Connection connection, int cityId) throws SQLException {
        List<Road> roads = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(GET_ROADS_BY_CITY_ID_QUERY)) {
            stmt.setInt(1, cityId);
            stmt.setInt(2, cityId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    roads.add(new Road(
                            (long) rs.getInt("id"),
                            rs.getInt("start_city_id"),
                            rs.getInt("end_city_id"),
                            rs.getInt("distance")
                    ));
                }
            }
        }
        return roads;
    }
}