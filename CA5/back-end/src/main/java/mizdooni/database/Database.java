package mizdooni.database;

import mizdooni.model.Restaurant;
import mizdooni.model.user.User;
import mizdooni.repository.MizTableRepository;
import mizdooni.repository.RestaurantRepository;
import mizdooni.repository.user.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Database {
    public List<User> users;
    public List<Restaurant> restaurants;

    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;
    private MizTableRepository mizTableRepository;

    public Database(UserRepository userRepository, RestaurantRepository restaurantRepository, MizTableRepository mizTableRepository) {
        users = new ArrayList<>();
        restaurants = new ArrayList<>();
        new DataLoader(this, userRepository, restaurantRepository, mizTableRepository).read();
    }
}
