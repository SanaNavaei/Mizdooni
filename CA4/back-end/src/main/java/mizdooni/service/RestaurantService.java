package mizdooni.service;

import com.fasterxml.jackson.databind.JsonNode;
import mizdooni.database.Database;
import mizdooni.exceptions.*;
import mizdooni.model.*;
import mizdooni.response.PagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    @Autowired
    private Database db;

    public Restaurant getRestaurant(int restaurantId) {
        return db.restaurants.stream().filter(r -> r.getId() == restaurantId).findFirst().orElse(null);
    }

    public PagedList<Restaurant> getRestaurants(int page, RestaurantSearchFilter filter) {
        List<Restaurant> restaurants = db.restaurants;
        if (filter != null) {
            restaurants = filter.filter(restaurants);
        }
        return new PagedList<>(restaurants, page, ServiceUtils.RESTAURANT_PAGE_SIZE);
    }

    public void addRestaurant(String name, String manager, String type, LocalTime startTime, LocalTime endTime,
                              String description, Address address, String imageLink) throws DuplicatedRestaurantName, ManagerNotFound, InvalidWorkingTime {
        User managerUser = ServiceUtils.findUser(manager, db.users);

        if (ServiceUtils.findRestaurantByName(name, db.restaurants) != null) {
            throw new DuplicatedRestaurantName();
        }
        if (managerUser == null || managerUser.getRole() != User.Role.manager) {
            throw new ManagerNotFound();
        }
        if (!ServiceUtils.validateWorkingTime(startTime) ||
                !ServiceUtils.validateWorkingTime(endTime)) {
            throw new InvalidWorkingTime();
        }

        Restaurant restaurant = new Restaurant(name, managerUser, type, startTime, endTime, description, address, imageLink);
        db.restaurants.add(restaurant);
    }

    public void addTable(int tableNumber, String restaurantName, String manager, String seatsNumber)
            throws DuplicatedTableNumber, InvalidSeatsNumber, RestaurantNotFound, ManagerNotFound, InvalidManagerRestaurant {
        User managerUser = ServiceUtils.findUser(manager, db.users);
        int seatsNumberInt = (int) Double.parseDouble(seatsNumber);
        Restaurant restaurant = ServiceUtils.findRestaurantByName(restaurantName, db.restaurants);

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
        if (!ServiceUtils.validateSeatsNumber(seatsNumber)) {
            throw new InvalidSeatsNumber();
        }

        Table table = new Table(tableNumber, restaurantName, seatsNumberInt);
        restaurant.addTable(table);
    }

    public List<JsonNode> showAvailableTables(String restaurantName) throws RestaurantNotFound {
        Restaurant restaurant = ServiceUtils.findRestaurantByName(restaurantName, db.restaurants);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }

        return restaurant.showAvailableTables();
    }

    public List<Restaurant> searchRestaurantsByManager(String manager) {
        return db.restaurants.stream().filter(r -> r.getManager().getUsername().equals(manager)).collect(Collectors.toList());
    }
}
