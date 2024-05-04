package mizdooni.service;

import mizdooni.database.Database;
import mizdooni.exceptions.DuplicatedRestaurantName;
import mizdooni.exceptions.InvalidWorkingTime;
import mizdooni.exceptions.ManagerNotFound;
import mizdooni.model.Address;
import mizdooni.model.Restaurant;
import mizdooni.model.RestaurantSearchFilter;
import mizdooni.model.User;
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
    @Autowired
    private UserService userService;

    public Restaurant getRestaurant(int restaurantId) {
        return ServiceUtils.findRestaurant(restaurantId, db.restaurants);
    }

    public PagedList<Restaurant> getRestaurants(int page, RestaurantSearchFilter filter) {
        List<Restaurant> restaurants = db.restaurants;
        if (filter != null) {
            restaurants = filter.filter(restaurants);
        }
        return new PagedList<>(restaurants, page, ServiceUtils.RESTAURANT_PAGE_SIZE);
    }

    public List<Restaurant> getManagerRestaurants(int managerId) {
        return db.restaurants.stream().filter(r -> r.getManager().getId() == managerId).collect(Collectors.toList());
    }

    public void addRestaurant(String name, String type, LocalTime startTime, LocalTime endTime, String description,
                              Address address, String imageLink) throws DuplicatedRestaurantName, ManagerNotFound, InvalidWorkingTime {
        User managerUser = userService.getCurrentUser();

        if (restaurantExists(name)) {
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

    public boolean restaurantExists(String name) {
        return db.restaurants.stream().anyMatch(r -> r.getName().equals(name));
    }
}
