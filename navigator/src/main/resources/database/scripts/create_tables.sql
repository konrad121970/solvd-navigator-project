drop database if exists navigator_db;

create database if not exists navigator_db;

use navigator_db;

CREATE TABLE IF NOT EXISTS cities (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE, -- SERIAL
    name VARCHAR(50) NOT NULL,
    x_pos DOUBLE NOT NULL,
    y_pos DOUBLE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS roads (
    start_city_id BIGINT UNSIGNED NOT NULL,
    end_city_id BIGINT UNSIGNED NOT NULL ,
    distance INT NOT NULL,
    PRIMARY KEY (start_city_id, end_city_id),
    CONSTRAINT fk_roads_start_city_id FOREIGN KEY (start_city_id) REFERENCES cities(id) on delete cascade on update no action,
    CONSTRAINT fk_roads_end_city_id FOREIGN KEY (end_city_id) REFERENCES cities(id) on delete cascade on update no action
);

CREATE TABLE IF NOT EXISTS routes (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
    distance INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS routes_has_cities (
	routes_id BIGINT UNSIGNED NOT NULL,
    cities_id BIGINT UNSIGNED NOT NULL,
	city_order INT NOT NULL,
    PRIMARY KEY (cities_id, routes_id),
    CONSTRAINT fk_cities_has_routes FOREIGN KEY (cities_id) REFERENCES
	cities (id) on delete cascade on update no action,
    CONSTRAINT fk_cities_has_routes2 FOREIGN KEY (routes_id) REFERENCES
	routes (id) on delete cascade on update no action
);
