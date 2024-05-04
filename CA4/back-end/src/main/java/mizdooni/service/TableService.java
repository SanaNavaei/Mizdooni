package mizdooni.service;

import com.fasterxml.jackson.databind.JsonNode;
import mizdooni.database.Database;
import mizdooni.exceptions.*;
import mizdooni.model.Restaurant;
import mizdooni.model.Table;
import mizdooni.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {
    @Autowired
    private Database db;
    @Autowired
    private UserService userService;

    public List<Table> getTables(int restaurantId) throws RestaurantNotFound {
        Restaurant restaurant = ServiceUtils.findRestaurant(restaurantId, db.restaurants);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        return restaurant.getTables();
    }

    public void addTable(int restaurantId, int seatsNumber)
            throws RestaurantNotFound, ManagerNotFound, InvalidManagerRestaurant {
        User managerUser = userService.getCurrentUser();
        Restaurant restaurant = ServiceUtils.findRestaurant(restaurantId, db.restaurants);

        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        if (managerUser == null || managerUser.getRole() != User.Role.manager) {
            throw new ManagerNotFound();
        }
        if (!restaurant.getManager().equals(managerUser)) {
            throw new InvalidManagerRestaurant();
        }

        Table table = new Table(0, restaurantId, seatsNumber);
        restaurant.addTable(table);
    }

    public List<JsonNode> showAvailableTables(int restaurantId) throws RestaurantNotFound {
        Restaurant restaurant = ServiceUtils.findRestaurant(restaurantId, db.restaurants);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }

        return restaurant.showAvailableTables();
    }
}
