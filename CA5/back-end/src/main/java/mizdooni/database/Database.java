package mizdooni.database;

import mizdooni.model.Restaurant;
import mizdooni.model.user.User;
import mizdooni.repository.user.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Database {
    public List<User> users;
    public List<Restaurant> restaurants;

    private UserRepository userRepository;

    public Database(UserRepository userRepository) {
        users = new ArrayList<>();
        restaurants = new ArrayList<>();
        new DataLoader(this, userRepository).read();
    }
}
