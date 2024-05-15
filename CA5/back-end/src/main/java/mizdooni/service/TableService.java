package mizdooni.service;

import mizdooni.database.Database;
import mizdooni.exceptions.InvalidManagerRestaurant;
import mizdooni.exceptions.RestaurantNotFound;
import mizdooni.exceptions.UserNotManager;
import mizdooni.model.Restaurant;
import mizdooni.model.MizTable;
import mizdooni.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {
    @Autowired
    private Database db;
    @Autowired
    private UserService userService;

    public List<MizTable> getTables(int restaurantId) throws RestaurantNotFound {
        Restaurant restaurant = ServiceUtils.findRestaurant(restaurantId, db.restaurants);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        return restaurant.getTables();
    }

    public void addTable(int restaurantId, int seatsNumber)
            throws RestaurantNotFound, UserNotManager, InvalidManagerRestaurant {
        User manager = userService.getCurrentUser();
        Restaurant restaurant = ServiceUtils.findRestaurant(restaurantId, db.restaurants);

        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        if (manager == null || manager.getRole() != User.Role.manager) {
            throw new UserNotManager();
        }
        if (!restaurant.getManager().equals(manager)) {
            throw new InvalidManagerRestaurant();
        }

        MizTable table = new MizTable(0, restaurant, seatsNumber);
        restaurant.addTable(table);
    }
}
