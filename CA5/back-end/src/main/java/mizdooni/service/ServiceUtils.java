package mizdooni.service;

import mizdooni.model.Restaurant;

import java.time.LocalTime;
import java.util.List;

public class ServiceUtils {
    static final int REVIEW_PAGE_SIZE = 5;
    static final int RESTAURANT_PAGE_SIZE = 12;

    public static boolean validateUsername(String username) {
        String usernameFormat = "^\\w+$";
        return username.matches(usernameFormat);
    }

    public static boolean validateEmail(String email) {
        String emailFormat = "^\\w+@\\w+\\.\\w+$";
        return email.matches(emailFormat);
    }

    static boolean validateWorkingTime(LocalTime time) {
        return time.getMinute() == 0;
    }

    static Restaurant findRestaurant(int id, List<Restaurant> restaurants) {
        return restaurants.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
    }
}
