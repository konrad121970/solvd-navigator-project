
SET @start_city_id = 1;
SET @end_city_id = 5;

-- Check if exists route between two cities
SELECT EXISTS (
    SELECT 1
    FROM routes_has_cities rhc1
    JOIN routes_has_cities rhc2 ON rhc1.routes_id = rhc2.routes_id
    JOIN cities c1 ON rhc1.cities_id = c1.id
    JOIN cities c2 ON rhc2.cities_id = c2.id
    WHERE c1.id = @start_city_id AND c2.id = 5
      AND rhc1.city_order = 1  -- Start city has city_order = 1
      AND rhc2.city_order = (SELECT MAX(city_order) 
							 FROM routes_has_cities 
                             WHERE routes_id = rhc2.routes_id) -- End_city has the highest city_order
) AS route_exists;


-- find route id between two cities
SELECT rhc1.routes_id AS route_id
FROM routes_has_cities rhc1
JOIN routes_has_cities rhc2 ON rhc1.routes_id = rhc2.routes_id
WHERE rhc1.cities_id = 1
  AND rhc2.cities_id = 5
  AND rhc1.city_order = 1
  AND rhc2.city_order = (SELECT MAX(city_order) 
						 FROM routes_has_cities 
                         WHERE routes_id = rhc1.routes_id);

-- Find all cities on one route
SELECT rhc.city_order, c.name
FROM routes_has_cities rhc
JOIN cities c ON rhc.cities_id = c.id
WHERE rhc.routes_id = (
    SELECT rhc1.routes_id
    FROM routes_has_cities rhc1
    JOIN routes_has_cities rhc2 ON rhc1.routes_id = rhc2.routes_id
    WHERE rhc1.cities_id = @start_city_id
      AND rhc2.cities_id = @end_city_id
      AND rhc1.city_order = 1
      AND rhc2.city_order = (SELECT MAX(city_order) 
							 FROM routes_has_cities 
                             where routes_id = rhc2.routes_id)
)
ORDER BY rhc.city_order;
