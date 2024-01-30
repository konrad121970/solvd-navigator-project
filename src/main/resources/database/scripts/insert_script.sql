USE navigator_db;

-- INSERT for the 'cities' table
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

-- INSERT for the 'roads' table
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
-- INSERT for the 'routes' table
INSERT INTO routes (distance) VALUES
(90), -- Sample route 1
(200); -- Sample route 2

-- INSERT for the 'routes_has_cities' table
INSERT INTO routes_has_cities (routes_id, cities_id, city_order) VALUES
-- A -> E Route
(1, 1, 1), -- Sample route 1: City A
(1, 2, 2), -- Sample route 1: City B
(1, 4, 3), -- Sample route 1: City D
(1, 5, 4), -- Sample route 1: City E

-- C -> E Route
(2, 3, 1), -- Sample route 2: City C
(2, 5, 2); -- Sample route 2: City E;
