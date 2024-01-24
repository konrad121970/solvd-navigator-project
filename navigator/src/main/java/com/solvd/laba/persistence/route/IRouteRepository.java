package com.solvd.laba.persistence.route;

import com.solvd.laba.model.Route;

import java.util.List;

public interface IRouteRepository {
    void create(Route route);

    Route findById(Long id);

    List<Route> getAllRoutes();

    void updateById(Route route);

    void deleteById(Long id);
}
