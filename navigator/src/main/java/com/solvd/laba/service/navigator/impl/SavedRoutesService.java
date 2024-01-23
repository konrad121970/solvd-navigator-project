package com.solvd.laba.service.navigator.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.laba.model.City;
import com.solvd.laba.persistence.ConnectionPool;
import com.solvd.laba.service.navigator.ISavedRoutesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class SavedRoutesService implements ISavedRoutesService {

    private static final Logger LOGGER = LogManager.getLogger(SavedRoutesService.class);
    private final ConnectionPool connectionPool;
    private final ObjectMapper objectMapper;

    public SavedRoutesService() {
        this.connectionPool = ConnectionPool.getInstance();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<City> getSavedRoute(Long startCityId, Long endCityId) {
        Connection connection = connectionPool.getConnection();
        String sql = "SELECT route FROM saved_routes WHERE start_city_id = ? AND end_city_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, startCityId);
            stmt.setLong(2, endCityId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String routeJson = rs.getString("route");
                return objectMapper.readValue(routeJson, new TypeReference<List<City>>() {});
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Error in getSavedRoute: {}", e.getMessage(), e);
        } catch (JsonProcessingException e) {
            LOGGER.error("JSON Parsing Error in getSavedRoute: {}", e.getMessage(), e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void saveRoute(Long startCityId, Long endCityId, List<City> route) {
        Connection connection = connectionPool.getConnection();
        String sql = "INSERT INTO saved_routes (start_city_id, end_city_id, route) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String routeJson = objectMapper.writeValueAsString(route);
            stmt.setLong(1, startCityId);
            stmt.setLong(2, endCityId);
            stmt.setString(3, routeJson);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQL Error in saveRoute: {}", e.getMessage(), e);
        } catch (JsonProcessingException e) {
            LOGGER.error("JSON Serialization Error in saveRoute: {}", e.getMessage(), e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
