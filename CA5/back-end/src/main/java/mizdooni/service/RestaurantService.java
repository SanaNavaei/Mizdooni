package mizdooni.service;

import mizdooni.exceptions.DuplicatedRestaurantName;
import mizdooni.exceptions.InvalidWorkingTime;
import mizdooni.exceptions.UserNotManager;
import mizdooni.model.Address;
import mizdooni.model.Restaurant;
import mizdooni.model.RestaurantSearchFilter;
import mizdooni.model.user.User;
import mizdooni.repository.RestaurantRepository;
import mizdooni.response.PagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private UserService userService;

    public Restaurant getRestaurant(int restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }

    public PagedList<Restaurant> getRestaurants(int page, RestaurantSearchFilter filter) {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (filter != null) {
            restaurants = filter.filter(restaurants);
        }
        return new PagedList<>(restaurants, page, ServiceUtils.RESTAURANT_PAGE_SIZE);
    }

    public List<Restaurant> getManagerRestaurants(int managerId) {
        return restaurantRepository.findByManagerId(managerId);
    }

    public int addRestaurant(String name, String type, LocalTime startTime, LocalTime endTime, String description,
                             Address address, String imageLink) throws DuplicatedRestaurantName, UserNotManager, InvalidWorkingTime {
        User manager = userService.getCurrentUser();

        if (restaurantRepository.existsByName(name)) {
            throw new DuplicatedRestaurantName();
        }
        if (manager == null || manager.getRole() != User.Role.manager) {
            throw new UserNotManager();
        }
        if (!ServiceUtils.validateWorkingTime(startTime) ||
                !ServiceUtils.validateWorkingTime(endTime)) {
            throw new InvalidWorkingTime();
        }

        int id = (int) restaurantRepository.count();
        Restaurant restaurant = new Restaurant(id, name, manager, type, startTime, endTime, description, address, imageLink);
        restaurantRepository.save(restaurant);
        return restaurant.getId();
    }

    public boolean restaurantExists(String name) {
        return restaurantRepository.existsByName(name);
    }

    public Set<String> getRestaurantTypes() {
        return restaurantRepository.findAllTypes();
    }

    public Map<String, Set<String>> getRestaurantLocations() {
        List<List<String>> locations = restaurantRepository.findCitiesByCountry();
        return locations.stream().collect(Collectors.groupingBy(
                location -> location.get(0),
                Collectors.mapping(location -> location.get(1), Collectors.toSet())
        ));
    }
}
