package com.solvd.laba.persistence;

import com.solvd.laba.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

public class ConnectionPool {

    private static volatile ConnectionPool instance = null;
    private final Queue<Connection> availableConnections;
    private final int poolSize;

    private ConnectionPool() {
        this.poolSize = Integer.parseInt(Config.POOL_SIZE.getValue());
        this.availableConnections = new LinkedList<>();

        try {
            Class.forName(Config.DRIVER.getValue());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No Driver class: ", e);
        }

        IntStream.range(0, poolSize)
                .forEach(index -> availableConnections.add(createConnection()));
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(Config.URL.getValue(), Config.USERNAME.getValue(), Config.PASSWORD.getValue());
        } catch (SQLException e) {
            throw new RuntimeException("Create connection failed! ", e);
        }
    }

    public Connection getConnection() {
        synchronized (availableConnections) {
            while (availableConnections.isEmpty()) {
                try {
                    availableConnections.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Waiting for connection was interrupted", e);
                }
            }
            return availableConnections.poll();
        }
    }

    public void releaseConnection(Connection connection) {
        synchronized (availableConnections) {
            boolean added = availableConnections.offer(connection);
            if (added) {
                availableConnections.notify();
            } else {
                throw new RuntimeException("Connection could not be released back to the pool");
            }
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    public void closeAllConnections() {
        synchronized (availableConnections) {
            while (!availableConnections.isEmpty()) {
                try {
                    availableConnections.poll().close();
                } catch (SQLException e) {
                    throw new RuntimeException("Error closing connection", e);
                }
            }
        }
    }
}