package com.solvd.laba.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.InputStream;
import java.util.Properties;

public enum Config {
    DRIVER("driver"),
    URL("url"),
    USERNAME("username"),
    PASSWORD("password"),
    POOL_SIZE("poolSize");

    private static final Logger LOGGER = LogManager.getLogger(Config.class);
    private static final Properties prop = new Properties();
    private final String key;

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                LOGGER.error("Unable to find config.properties");
                throw new IllegalStateException("config.properties not found in the classpath");
            }
            prop.load(input);
            LOGGER.info("Configuration properties loaded successfully. Driver: {}", prop.getProperty("driver"));
        } catch (Exception e) {
            LOGGER.error("Error loading configuration properties.", e);
            throw new IllegalStateException("Failed to load configuration properties.", e);
        }
    }

    Config(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return prop.getProperty(key);
    }
}