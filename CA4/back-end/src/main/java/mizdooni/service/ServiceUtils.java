package mizdooni.service;

import mizdooni.model.Restaurant;
import mizdooni.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    static boolean validateSeatsNumber(String seatsNumber) {
        String seatsNumberFormat = "^[1-9][0-9]*$";
        return seatsNumber.matches(seatsNumberFormat);
    }

    static boolean userIsTaken(String username, String email, List<User> users) {
        return users.stream().anyMatch(u -> u.getUsername().equals(username) || u.getEmail().equals(email));
    }

    static User findUser(String username, List<User> users) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }

    static User findUserByEmail(String email, List<User> users) {
        return users.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    static Restaurant findRestaurantByName(String name, List<Restaurant> restaurants) {
        return restaurants.stream().filter(r -> r.getName().equals(name)).findFirst().orElse(null);
    }

    public static List<String> convertToString(Map<LocalDate, List<LocalTime>> availableHours) {
        List<String> timeAndDate = new ArrayList<>();
        for (Map.Entry<LocalDate, List<LocalTime>> entry : availableHours.entrySet()) {
            for (LocalTime time : entry.getValue()) {
                LocalDateTime datetime = LocalDateTime.of(entry.getKey(), time);
                timeAndDate.add(datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            }
        }
        return timeAndDate;
    }
}
