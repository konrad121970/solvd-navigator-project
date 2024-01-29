package com.solvd.laba.persistence.route.impl;

import com.solvd.laba.model.RouteCity;
import com.solvd.laba.persistence.route.IRouteCityRepository;
import com.solvd.laba.persistence.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteCityRepositoryImpl implements IRouteCityRepository {
    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    private static final String ADD_ROUTE_CITY_QUERY = "INSERT INTO routes_has_cities (routes_id, cities_id, city_order) VALUES (?, ?, ?)";
    private static final String UPDATE_ROUTE_CITY_QUERY = "UPDATE routes_has_cities SET city_order = ? WHERE routes_id = ? AND cities_id = ?";
    private static final String DELETE_ROUTE_CITY_QUERY = "DELETE FROM routes_has_cities WHERE routes_id = ?";
    private static final String GET_ALL_ROUTE_CITIES_QUERY = "SELECT * FROM routes_has_cities";

    @Override
    public void addRouteCity(RouteCity routeCity) {
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            connection.setAutoCommit(false);

            // Insert the route city
            try (PreparedStatement stmt = connection.prepareStatement(ADD_ROUTE_CITY_QUERY)) {
                stmt.setLong(1, routeCity.getRoute().getId());
                stmt.setLong(2, routeCity.getCity().getId());
                stmt.setInt(3, routeCity.getCityOrderNumber());
                stmt.executeUpdate();
            }

            connection.commit();
            LOGGER.info("RouteCity added: {}", routeCity);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Rollback failed: {}", ex.getMessage());
            }
            LOGGER.error("Adding RouteCity failed: {}", e.getMessage());
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
    public void updateRouteCity(RouteCity routeCity) {
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(UPDATE_ROUTE_CITY_QUERY)) {
                stmt.setInt(1, routeCity.getCityOrderNumber());
                stmt.setLong(2, routeCity.getRoute().getId());
                stmt.setLong(3, routeCity.getCity().getId());
                stmt.executeUpdate();
            }

            connection.commit();
            LOGGER.info("RouteCity updated: {}", routeCity);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Rollback failed: {}", ex.getMessage());
            }
            LOGGER.error("Update RouteCity failed: {}", e.getMessage());
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
    public void deleteRouteCity(long routeId) {
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            connection.setAutoCommit(false);

            // Delete the route city
            try (PreparedStatement stmt = connection.prepareStatement(DELETE_ROUTE_CITY_QUERY)) {
                stmt.setLong(1, routeId);
                stmt.executeUpdate();
            }

            connection.commit();
            LOGGER.info("RouteCity deleted with Route ID: {} and City ID: {}", routeId);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Rollback failed: {}", ex.getMessage());
            }
            LOGGER.error("Delete RouteCity failed: {}", e.getMessage());
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
    public List<RouteCity> getAllRouteCities() {
        Connection connection = CONNECTION_POOL.getConnection();
        List<RouteCity> routeCities = new ArrayList<>();
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_ROUTE_CITIES_QUERY)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    // Populate each RouteCity WITH associated Route and City
                    RouteCity routeCity = mapRow(connection, rs);
                    routeCities.add(routeCity);
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
        return routeCities;
    }



    // TODO:
    private RouteCity mapRow(Connection connection, ResultSet rs) throws SQLException {
        long routeId = rs.getLong("routes_id");
        long cityId = rs.getLong("cities_id");
        int cityOrderNumber = rs.getInt("city_order");


        return null;
    }
}
