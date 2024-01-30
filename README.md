# Solvd Navigator Project

## Navigator Overview
The Solvd Navigator Project is a terminal-operated navigation system designed to provide functionalities such as route planning, mathematical logic using Dijkstra's algorithm, point generation, and the ability to save and manage routes. The project incorporates a MySQL database to store coordinates of points and save routes, with a well-structured model layer, DAO (Data Access Object) for database interaction, and a service layer for business logic.

## Project Features Checklist
- [x] **UI: Terminal Operation**
    - The user interface is designed to be operated through the terminal.

- [x] **Mathematical Logic: Dijkstra's Algorithm**
    - Utilizes Dijkstra's algorithm for efficient route planning and navigation.

- [x] **Generate Points**
    - Implements a functionality to generate points and represents them in the GUI.

- [x] **Additional Stops in Route**
    - Allows users to add additional stop in the route.

- [x] **Database Integration: MySQL**
    - Utilizes MySQL to store coordinates of points and saved routes.

- [x] **Model Layer Based on Database**
    - Establishes a well-structured model layer based on the database schema.

- [x] **DAO (Data Access Object)**
    - Implements DAO for effective interaction with the MySQL database using JDBC and is different-implementation-ready.

- [x] **Service Layer**
    - Incorporates a service layer for handling business logic and communication between the UI and database.

- [x] **Design Patterns**
    - Utilizes design patterns such as Singleton, Builder pattern, Listener, and Factory method.

## Configuration
 - To connect to your database you have to edit the config.properties file:
```text
driver = com.mysql.cj.jdbc.Driver
url = // YOUR_DB_URL
username = // YOUR_DB_USERNAME
password = // YOUR_DB_PASSWORD
poolSize = 5
impl = jdbc
```

## Database Initialization SQL script
```sql
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

INSERT INTO cities (name, x_pos, y_pos) VALUES
('A', 10.0, 10.0),
('B', 40.0, 40.0),
('C', 100.0, 20.0),
('D', 20.0, 60.0),
('E', 60.0, 80.0),
('F', 50.0, 30.0),
('G', 120.0, 40.0),
('H', 90.0, 100.0),
('I', 90.0, 120.0),
('J', 140.0, 80.0);

INSERT INTO roads (start_city_id, end_city_id, distance) VALUES
(1, 2, 40),  -- A->B
(2, 1, 40),
(1, 3, 100), -- A->C
(3, 1, 100),
(2, 3, 50),  -- B->C
(3, 2, 50),
(2, 4, 20),  -- B->D
(4, 2, 20),
(4, 5, 30),  -- D->E
(5, 4, 30),
(5, 3, 70),  -- E->C
(3, 5, 70),
(1, 6, 30),  -- A->F
(6, 1, 30),
(2, 7, 80),  -- B->G
(7, 2, 80),
(3, 8, 60),  -- C->H
(8, 3, 60),
(4, 9, 40),  -- D->I
(9, 4, 40),
(5, 10, 90), -- E->J
(10, 5, 90);

INSERT INTO routes (distance) VALUES
(90), -- Sample route 1
(200); -- Sample route 2

INSERT INTO routes_has_cities (routes_id, cities_id, city_order) VALUES
-- A -> E Route
(1, 1, 1), -- Sample route 1: City A
(1, 2, 2), -- Sample route 1: City B
(1, 4, 3), -- Sample route 1: City D
(1, 5, 4), -- Sample route 1: City E

-- C -> E Route
(2, 3, 1), -- Sample route 2: City C
(2, 5, 2); -- Sample route 2: City E;
```