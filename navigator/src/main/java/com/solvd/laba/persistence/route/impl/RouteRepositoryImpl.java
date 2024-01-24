package com.solvd.laba.persistence.route.impl;

import com.solvd.laba.model.Route;
import com.solvd.laba.persistence.route.IRouteRepository;
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

public class RouteRepositoryImpl implements IRouteRepository {
    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    private static final String CREATE_ROUTE_QUERY = "INSERT INTO routes (distance) VALUES (?)";
    private static final String GET_ROUTE_BY_ID_QUERY = "SELECT id as route_id, distance FROM routes WHERE id = ?";
    private static final String UPDATE_ROUTE_QUERY = "UPDATE routes SET distance = ? WHERE id = ?";
    private static final String DELETE_ROUTE_QUERY = "DELETE FROM routes WHERE id = ?";
    private static final String GET_ALL_ROUTES_QUERY = "SELECT id as route_id, distance FROM routes";

    @Override
    public void create(Route route) {
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            connection.setAutoCommit(false);

            // Insert the route and get the generated ID
            try (PreparedStatement routeStmt = connection.prepareStatement(CREATE_ROUTE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
                routeStmt.setInt(1, route.getDistance());
                int affectedRows = routeStmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating route failed, no rows affected.");
                }

                try (ResultSet generatedKeys = routeStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        route.setId((long) generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating route failed, no ID obtained.");
                    }
                }
            }

            connection.commit();
            LOGGER.info("Route created: {}", route);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Rollback failed: {}", ex.getMessage());
            }
            LOGGER.error("Creating route failed: {}", e.getMessage());
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
    public Route findById(Long id) {
        Connection connection = CONNECTION_POOL.getConnection();
        Route route = null;
        try {
            connection.setAutoCommit(false);

            // Get the route by id
            try (PreparedStatement stmt = connection.prepareStatement(GET_ROUTE_BY_ID_QUERY)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    // Populate route WITHOUT associated cities
                    route = mapRow(connection, rs);
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
        return route;
    }

    @Override
    public List<Route> getAllRoutes() {
        Connection connection = CONNECTION_POOL.getConnection();
        List<Route> routes = new ArrayList<>();
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_ROUTES_QUERY)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    // Populate each route WITHOUT associated cities
                    Route route = mapRow(connection, rs);
                    routes.add(route);
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
        return routes;
    }

    @Override
    public void updateById(Route route) {
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(UPDATE_ROUTE_QUERY)) {
                stmt.setInt(1, route.getDistance());
                stmt.setLong(2, route.getId());
                stmt.executeUpdate();
            }

            connection.commit();
            LOGGER.info("Route updated: {}", route);
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
            // Delete the route
            try (PreparedStatement stmt = connection.prepareStatement(DELETE_ROUTE_QUERY)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }

            connection.commit();
            LOGGER.info("Route deleted with id: {}", id);
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

    private Route mapRow(Connection connection, ResultSet rs) throws SQLException {
        long id = rs.getLong("route_id");
        int distance = rs.getInt("distance");
        return new Route(id, null, distance);
    }
}
