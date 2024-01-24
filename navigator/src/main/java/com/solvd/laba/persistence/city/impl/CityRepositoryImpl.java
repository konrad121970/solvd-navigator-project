package com.solvd.laba.persistence.city.impl;

import com.solvd.laba.model.City;
import com.solvd.laba.persistence.city.ICityRepository;
import com.solvd.laba.persistence.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityRepositoryImpl implements ICityRepository {
    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    private static final String GET_ALL_CITIES_QUERY = "SELECT id as city_id, name as city_name, x_pos as city_x_pos, y_pos as city_y_pos FROM cities";
    private static final String CREATE_CITY_QUERY = "INSERT INTO cities (name, x_pos, y_pos) VALUES (?, ?, ?)";
    private static final String GET_CITY_BY_ID_QUERY = "SELECT id as city_id, name as city_name, x_pos as city_x_pos, y_pos as city_y_pos FROM cities WHERE id = ?";
    private static final String UPDATE_CITY_QUERY = "UPDATE cities SET name = ?, x_pos = ?, y_pos = ? WHERE id = ?";
    private static final String DELETE_CITY_QUERY = "DELETE FROM cities WHERE id = ?";

    @Override
    public void create(City city) {
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
    public City findById(Long id) {
        Connection connection = CONNECTION_POOL.getConnection();
        City city = null;
        try {
            connection.setAutoCommit(false);

            // Get the city by id
            try (PreparedStatement stmt = connection.prepareStatement(GET_CITY_BY_ID_QUERY)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    // Populate city and WITHOUT associated roads
                    city = mapRow(connection, rs);
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
    public List<City> getAllCities() {
        Connection connection = CONNECTION_POOL.getConnection();
        List<City> cities = new ArrayList<>();
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_CITIES_QUERY)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    // Populate each city WITHOUT associated roads
                    City city = mapRow(connection, rs);
                    cities.add(city);
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
        return cities;
    }


    @Override
    public void updateById(City city) {
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(UPDATE_CITY_QUERY)) {
                stmt.setString(1, city.getName());
                stmt.setDouble(2, city.getxPos());
                stmt.setDouble(3, city.getyPos());
                stmt.setLong(4, city.getId());
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
    public void deleteById(Long id) {
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            connection.setAutoCommit(false);
            // Delete the city
            try (PreparedStatement stmt = connection.prepareStatement(DELETE_CITY_QUERY)) {
                stmt.setLong(1, id);
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
    private City mapRow(Connection connection, ResultSet rs) throws SQLException {
        int id = rs.getInt("city_id");
        String name = rs.getString("city_name");
        double xPos = rs.getDouble("city_x_pos");
        double yPos = rs.getDouble("city_y_pos");
        return new City((long) id, name, xPos, yPos, null);
    }
}