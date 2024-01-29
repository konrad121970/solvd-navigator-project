package com.solvd.laba.persistence;

import com.solvd.laba.model.Route;
import com.solvd.laba.persistence.city.ICityRepository;
import com.solvd.laba.persistence.city.IRoadRepository;
import com.solvd.laba.persistence.city.impl.CityRepositoryImpl;
import com.solvd.laba.persistence.city.impl.RoadRepositoryImpl;
import com.solvd.laba.persistence.route.IRouteCityRepository;
import com.solvd.laba.persistence.route.IRouteRepository;
import com.solvd.laba.persistence.route.impl.RouteCityRepositoryImpl;
import com.solvd.laba.persistence.route.impl.RouteRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;

public class RepositoryFactory {
    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());


    public static ICityRepository createCityRepository(String type) {
        ICityRepository result;
        switch (type) {
            case "jdbc":
                result = new CityRepositoryImpl();
                break;
//            case "mybatis":
//                result = new CityRepositoryImpl();
//                break;
            default:
                result = new CityRepositoryImpl();
                LOGGER.info("Data source was not specified or is invalid. Defaulting to JDBC implementation");
                break;
        }
        return result;
    }

    public static IRoadRepository createRoadRepository(String type) {
        IRoadRepository result;
        switch (type) {
            case "jdbc":
                result = new RoadRepositoryImpl();
                break;
//            case "mybatis":
//                result = new RoadRepositoryImpl();
//                break;
            default:
                result = new RoadRepositoryImpl();
                LOGGER.info("Data source was not specified or is invalid. Defaulting to JDBC implementation");
                break;
        }
        return result;
    }

    public static IRouteRepository createRouteRepository(String type) {
        IRouteRepository result;
        switch (type) {
            case "jdbc":
                result = new RouteRepositoryImpl();
                break;
//            case "mybatis":
//                result = new RouteRepositoryImpl();
//                break;
            default:
                result = new RouteRepositoryImpl();
                LOGGER.info("Data source was not specified or is invalid. Defaulting to JDBC implementation");
                break;
        }
        return result;
    }

    public static IRouteCityRepository createRouteCityRepository(String type) {
        IRouteCityRepository result;
        switch (type) {
            case "jdbc":
                result = new RouteCityRepositoryImpl();
                break;
//            case "mybatis":
//                result = new RouteCityRepositoryImpl();
//                break;
            default:
                result = new RouteCityRepositoryImpl();
                LOGGER.info("Data source was not specified or is invalid. Defaulting to JDBC implementation");
                break;
        }
        return result;
    }


}
