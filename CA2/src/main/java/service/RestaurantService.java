package service;

import com.fasterxml.jackson.databind.JsonNode;
import database.Database;
import exceptions.*;
import model.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static service.Utils.*;

public class RestaurantService {
    private Database db;

    private static final RestaurantService instance = new RestaurantService();

    private RestaurantService() {
        db = Database.getInstance();
    }

    public static RestaurantService getInstance() {
        return instance;
    }

    public Restaurant getRestaurant(int restaurantId) {
        return db.restaurants.stream().filter(r -> r.getId() == restaurantId).findFirst().orElse(null);
    }

    public List<Restaurant> getRestaurants() {
        return db.restaurants;
    }

    public void addRestaurant(String name, String manager, String type, LocalTime startTime, LocalTime endTime,
                              String description, Address address) throws DuplicatedRestaurantName, ManagerNotFound, InvalidWorkingTime {
        User managerUser = findUser(manager, db.users);

        if (findRestaurantByName(name, db.restaurants) != null) {
            throw new DuplicatedRestaurantName();
        }
        if (managerUser == null || managerUser.getRole() != User.Role.manager) {
            throw new ManagerNotFound();
        }
        if (!validateWorkingTime(startTime) || !validateWorkingTime(endTime)) {
            throw new InvalidWorkingTime();
        }

        Restaurant restaurant = new Restaurant(name, managerUser, type, startTime, endTime, description, address);
        db.restaurants.add(restaurant);
    }

    public void addTable(int tableNumber, String restaurantName, String manager, String seatsNumber)
            throws DuplicatedTableNumber, InvalidSeatsNumber, RestaurantNotFound, ManagerNotFound, InvalidManagerRestaurant {
        User managerUser = findUser(manager, db.users);
        int seatsNumberInt = (int) Double.parseDouble(seatsNumber);
        Restaurant restaurant = findRestaurantByName(restaurantName, db.restaurants);

        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        if (managerUser == null || managerUser.getRole() != User.Role.manager) {
            throw new ManagerNotFound();
        }
        if (!restaurant.getManager().equals(managerUser)) {
            throw new InvalidManagerRestaurant();
        }
        if (restaurant.getTable(tableNumber) != null) {
            throw new DuplicatedTableNumber();
        }
        if (!validateSeatsNumber(seatsNumber)) {
            throw new InvalidSeatsNumber();
        }

        Table table = new Table(tableNumber, restaurantName, seatsNumberInt);
        restaurant.addTable(table);
    }

    public List<JsonNode> showAvailableTables(String restaurantName) throws RestaurantNotFound {
        Restaurant restaurant = findRestaurantByName(restaurantName, db.restaurants);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }

        return restaurant.showAvailableTables();
    }

    public List<Restaurant> searchRestaurantsByName(String restaurantName) {
        return findRestaurantsByName(restaurantName, db.restaurants);
    }

    public List<Restaurant> searchRestaurantsByType(String restaurantType) {
        return findRestaurantsByType(restaurantType, db.restaurants);
    }

    public List<Restaurant> searchRestaurantsByCity(String city) {
        return findRestaurantsByCity(city, db.restaurants);
    }

    public List<Restaurant> sortRestaurantsByRate() {
        List<Restaurant> restaurants = db.restaurants;
        restaurants.sort((r1, r2) -> Double.compare(r2.getAverageRating().overall, r1.getAverageRating().overall));

        return restaurants;
    }

    public Restaurant searchRestaurantByManager(String manager) {
        return findRestaurantByManager(manager, db.restaurants);
    }

    public void addReview(String username, String restaurantName, double foodRate, double serviceRate, double ambianceRate,
                          double overallRate, String comment) throws UserNotFound, ManagerCannotReview, RestaurantNotFound, InvalidReviewRating, UserHasNotReserved {
        User user = findUser(username, db.users);
        if (user == null) {
            throw new UserNotFound();
        }
        if (user.getRole() == User.Role.manager) {
            throw new ManagerCannotReview();
        }

        Restaurant restaurant = findRestaurantByName(restaurantName, db.restaurants);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        if (!user.checkReserved(restaurant)) {
            throw new UserHasNotReserved();
        }

        if (foodRate < 0 || foodRate > 5) {
            throw new InvalidReviewRating("Food");
        }
        if (serviceRate < 0 || serviceRate > 5) {
            throw new InvalidReviewRating("Service");
        }
        if (ambianceRate < 0 || ambianceRate > 5) {
            throw new InvalidReviewRating("Ambiance");
        }
        if (overallRate < 0 || overallRate > 5) {
            throw new InvalidReviewRating("Overall");
        }

        Review review = new Review(user, foodRate, serviceRate, ambianceRate, overallRate, comment, LocalDateTime.now());
        restaurant.addReview(review);
    }

    public Rating showAverageRating(String restaurantName) throws RestaurantNotFound {
        Restaurant restaurant = findRestaurantByName(restaurantName, db.restaurants);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }

        return restaurant.getAverageRating();
    }
}
