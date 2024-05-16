package mizdooni.database;

import mizdooni.repository.MizTableRepository;
import mizdooni.repository.RestaurantRepository;
import mizdooni.repository.ReviewRepository;
import mizdooni.repository.user.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class Database {
    UserRepository userRepository;
    RestaurantRepository restaurantRepository;
    MizTableRepository mizTableRepository;
    ReviewRepository reviewRepository;

    public Database(UserRepository userRepository, RestaurantRepository restaurantRepository,
                    MizTableRepository mizTableRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.mizTableRepository = mizTableRepository;
        this.reviewRepository = reviewRepository;
        new DataLoader(this).read();
    }
}
