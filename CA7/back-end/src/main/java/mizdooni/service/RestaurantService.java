package mizdooni.service;

import co.elastic.apm.api.CaptureSpan;
import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Span;
import mizdooni.exceptions.DuplicatedRestaurantName;
import mizdooni.exceptions.InvalidWorkingTime;
import mizdooni.exceptions.UserNotManager;
import mizdooni.model.Address;
import mizdooni.model.Restaurant;
import mizdooni.model.RestaurantSearchFilter;
import mizdooni.model.user.User;
import mizdooni.repository.RestaurantRepository;
import mizdooni.repository.RestaurantSearchSpec;
import mizdooni.response.PagedList;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Restaurant getRestaurant(int restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant != null) {
            Hibernate.initialize(restaurant.getReviews());
            Hibernate.initialize(restaurant.getTables());
        }
        return restaurant;
    }

    @Transactional
    public PagedList<Restaurant> getRestaurants(int page, RestaurantSearchFilter filter) {
        RestaurantSearchSpec spec = new RestaurantSearchSpec(filter.getName(), filter.getType(), filter.getLocation());
        List<Restaurant> restaurants = restaurantRepository.findAll(spec);
        restaurants = filter.sort(restaurants);
        for (Restaurant restaurant : restaurants) {
            Hibernate.initialize(restaurant.getReviews());
            Hibernate.initialize(restaurant.getTables());
        }
        return new PagedList<>(restaurants, page, ServiceUtils.RESTAURANT_PAGE_SIZE);
    }

    @Transactional
    public List<Restaurant> getManagerRestaurants(int managerId) {
        List<Restaurant> restaurants = restaurantRepository.findByManagerId(managerId);
        for (Restaurant restaurant : restaurants) {
            Hibernate.initialize(restaurant.getReviews());
            Hibernate.initialize(restaurant.getTables());
        }
        return restaurants;
    }

    @CaptureSpan
    public int addRestaurant(int userId, String name, String type, LocalTime startTime, LocalTime endTime, String description,
                             Address address, String imageLink) throws DuplicatedRestaurantName, UserNotManager, InvalidWorkingTime {
        Span addRestaurantSpan = ElasticApm.currentSpan();
        Span getUserSpan = addRestaurantSpan.startSpan().setName("get manager");
        User manager = userService.getUser(userId);
        getUserSpan.end();

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
        Span newRestaurantSpan = addRestaurantSpan.startSpan().setName("add restaurant");
        Restaurant restaurant = new Restaurant(id, name, manager, type, startTime, endTime, description, address, imageLink);
        restaurantRepository.save(restaurant);
        newRestaurantSpan.end();
        return restaurant.getId();
    }

    public boolean restaurantExists(String name) {
        return restaurantRepository.existsByName(name);
    }

    public Set<String> getRestaurantTypes() {
        return restaurantRepository.findAllTypes();
    }

    @CaptureSpan
    public Map<String, Set<String>> getRestaurantLocations() {
        Span getLocationsSpan = ElasticApm.currentSpan();
        Span findLocationsSpan = getLocationsSpan.startSpan().setName("find locations");
        List<List<String>> locations = restaurantRepository.findCitiesByCountry();
        findLocationsSpan.end();

        return locations.stream().collect(Collectors.groupingBy(
                location -> location.get(0),
                Collectors.mapping(location -> location.get(1), Collectors.toSet())
        ));
    }
}
