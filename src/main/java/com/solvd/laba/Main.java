package com.solvd.laba;

import com.solvd.laba.model.City;
import com.solvd.laba.service.city.ICityService;
import com.solvd.laba.service.city.impl.CityService;
import com.solvd.laba.service.navigator.INavigatorService;
import com.solvd.laba.service.navigator.impl.NavigatorService;
import com.solvd.laba.model.Road;
import com.solvd.laba.service.city.IRoadService;
import com.solvd.laba.service.city.impl.RoadService;
import com.solvd.laba.ui.MainFrame;

import java.util.*;
import java.util.List;
import java.util.Scanner;


public class Main {
        private static final Scanner scanner = new Scanner(System.in);
        private static MainFrame mainFrame = null;
        private static final ICityService cityService = new CityService();
        private static final IRoadService roadService = new RoadService();
        private static final INavigatorService navigatorService = new NavigatorService();

        public static void main(String[] args) {
            List<City> cities = cityService.getAllCities();
            mainFrame = new MainFrame("Navigator", cities);

            boolean running = true;
            while (running) {
                System.out.println("\n--- Welcome to the app created by navigator team! ---");
                System.out.println("\n--- Main Menu ---");
                System.out.println("1. Add City");
                System.out.println("2. Add Road");
                System.out.println("3. Find Shortest Path Between Cities");
                System.out.println("4. Find Shortest Path With Stop");
                System.out.println("5. Open GUI");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        addCity();
                        break;
                    case 2:
                        addRoad();
                        break;
                    case 3:
                        findShortestPath();
                        break;
                    case 4:
                        findShortestPathWithStop();
                        break;
                    case 5:
                        mainFrame.setVisible(true);
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            }

            scanner.close();
            System.out.println("Exiting program.");
        }

        private static int getIntInput() {
            while (true) {
                try {
                    return scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter a valid integer: ");
                } finally {
                    scanner.nextLine();
                }
            }
        }

        private static double getDoubleInput() {
            while (true) {
                try {
                    return scanner.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter a valid decimal number: ");
                } finally {
                    scanner.nextLine();
                }
            }
        }

    private static void addCity() {
        System.out.print("Enter city name: ");
        String name = scanner.nextLine();
        double xPos, yPos;

        while (true) {
            System.out.print("Enter city X position (must be >= 0): ");
            xPos = getDoubleInput();
            if (xPos >= 0) {
                break;
            }
            System.out.println("X position cannot be lower than 0. Please try again.");
        }

        while (true) {
            System.out.print("Enter city Y position (must be >= 0): ");
            yPos = getDoubleInput();
            if (yPos >= 0) {
                break;
            }
            System.out.println("Y position cannot be lower than 0. Please try again.");
        }

        City city = City.builder()
                .setName(name)
                .setXPos(xPos)
                .setYPos(yPos)
                .build();

        cityService.createCity(city);
        mainFrame.addCity(city);
        navigatorService.updateCitiesList();
        System.out.println("City added successfully: " + city);
    }


    private static void addRoad() {
        System.out.println("Available cities:");
        List<City> allCities = cityService.getAllCities();
        allCities.forEach(city -> System.out.println(city.getId() + ". " + city.getName()));

        System.out.print("Enter start city ID: ");
        Long startCityId = getLongInput();
        System.out.print("Enter end city ID: ");
        Long endCityId = getLongInput();

        if (!isValidCityId(startCityId, allCities) || !isValidCityId(endCityId, allCities)) {
            System.out.println("Invalid city ID. Please enter a valid city ID from the list.");
            return;
        }

        if (startCityId.equals(endCityId)) {
            System.out.println("The start and end city cannot be the same. Please enter different IDs.");
            return;
        }

        System.out.print("Enter distance: ");
        int distance = getIntInput();

        try {
            Road road = new Road(startCityId, endCityId, distance);
            roadService.createRoad(road);
            System.out.println("Road added successfully: " + road);
            navigatorService.updateCitiesList();
        } catch (Exception e) {
            System.err.println("Error creating road: " + e.getMessage());
        }
        allCities = cityService.getAllCities();
        mainFrame.setCities(allCities);
    }

    private static boolean isValidCityId(Long cityId, List<City> cities) {
        return cities.stream().anyMatch(city -> city.getId().equals(cityId));
    }


    private static Long getLongInput() {
            while (true) {
                try {
                    return scanner.nextLong();
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter a valid long integer: ");
                } finally {
                    scanner.nextLine();
                }
            }
        }

    private static void findShortestPath() {
        System.out.println("Available cities:");
        List<City> allCities = cityService.getAllCities();
        allCities.forEach(city -> System.out.println(city.getId() + ". " + city.getName()));

        System.out.print("Enter start city ID: ");
        Long startCityId = getLongInput();
        System.out.print("Enter end city ID: ");
        Long endCityId = getLongInput();

        if (startCityId.equals(endCityId)) {
            System.out.println("The start and end city must be different.");
            return;
        }
        try {
            City startCity = cityService.findCityById(startCityId);
            City endCity = cityService.findCityById(endCityId);
            if (startCity == null || endCity == null) {
                System.out.println("One or both cities do not exist.");
                return;
            }
            List<City> path = navigatorService.findShortestPath(startCity, endCity);
            if(path.size() == 0){
                System.err.println("There is no such path: ");
            } else {
                System.out.println("Shortest path: " + path + "\nRoute length: " + navigatorService.getRoadLength(path));
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error finding shortest path: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred while finding the shortest path: " + e.getMessage());
        }
    }



    private static void findShortestPathWithStop() {
        System.out.println("Available cities:");
        List<City> allCities = cityService.getAllCities();
        allCities.forEach(city -> System.out.println(city.getId() + ". " + city.getName()));

        Long startCityId, stopCityId, endCityId;
        System.out.print("Enter start city ID: ");
        startCityId = getLongInput();
        System.out.print("Enter stop city ID: ");
        stopCityId = getLongInput();
        System.out.print("Enter end city ID: ");
        endCityId = getLongInput();

        if (startCityId.equals(stopCityId) || stopCityId.equals(endCityId) || startCityId.equals(endCityId)) {
            System.out.println("Start, stop, and end cities must be different.");
            return;
        }

        // Validate city IDs
        if (!isValidCityId(startCityId, allCities) || !isValidCityId(stopCityId, allCities) || !isValidCityId(endCityId, allCities)) {
            System.out.println("Invalid city ID. Please enter a valid city ID from the list.");
            return;
        }

        try {
            City startCity = cityService.findCityById(startCityId);
            City stopCity = cityService.findCityById(stopCityId);
            City endCity = cityService.findCityById(endCityId);
            if (startCity == null || stopCity == null || endCity == null) {
                System.out.println("One or more of the specified cities do not exist.");
                return;
            }

            List<City> pathWithStop = navigatorService.findShortestPathWithStop(startCity, stopCity, endCity);

            if (pathWithStop.isEmpty()) {
                System.err.println("Couldn't find route!");
            } else {
                System.out.println("Shortest path with stop: " + pathWithStop + "\nRoute length: " + navigatorService.getRoadLength(pathWithStop));
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred while finding the shortest path with stop: " + e.getMessage());
        }
    }

}
