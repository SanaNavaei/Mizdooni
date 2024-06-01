package mizdooni.service;

import mizdooni.exceptions.InvalidManagerRestaurant;
import mizdooni.exceptions.RestaurantNotFound;
import mizdooni.exceptions.UserNotManager;
import mizdooni.model.MizTable;
import mizdooni.model.Restaurant;
import mizdooni.model.user.User;
import mizdooni.repository.MizTableRepository;
import mizdooni.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {
    @Autowired
    private MizTableRepository mizTableRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private UserService userService;

    public List<MizTable> getTables(int restaurantId) throws RestaurantNotFound {
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        return mizTableRepository.findByRestaurantId(restaurantId);
    }

    public void addTable(int userId, int restaurantId, int seatsNumber)
            throws RestaurantNotFound, UserNotManager, InvalidManagerRestaurant {
        User manager = userService.getUser(userId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId);

        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        if (manager == null || manager.getRole() != User.Role.manager) {
            throw new UserNotManager();
        }
        if (restaurant.getManager().getId() != manager.getId()) {
            throw new InvalidManagerRestaurant();
        }

        MizTable table = new MizTable(0, restaurant, seatsNumber);
        mizTableRepository.save(table);
    }
}
