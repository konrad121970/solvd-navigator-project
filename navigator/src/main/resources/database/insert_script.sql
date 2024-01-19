USE navigator_db;

INSERT INTO cities (name, x_pos, y_pos) VALUES
('Warsaw', 10.0, 20.0),
('Bialystok', 15.0, 25.0),
('Cracow', 30.0, 40.0);

-- INSERT dla tabeli 'roads'
INSERT INTO roads (start_city_id, end_city_id, distance) VALUES
(1, 2, 50),
(2, 1, 50),
(2, 3, 75),
(3, 2, 75),
(3, 1, 100),
(1, 3, 100);