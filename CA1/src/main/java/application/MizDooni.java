package application;

import exceptions.DuplicatedUsernameEmail;
import exceptions.InvalidEmailFormat;
import exceptions.InvalidUsernameFormat;

import java.time.LocalTime;
import java.util.ArrayList;

import static application.Utils.validateEmail;
import static application.Utils.validateUsername;
import static application.Utils.userIsTaken;
import static application.Utils.findManager;

public class MizDooni {
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Restaurant> restaurants = new ArrayList<>();

    public void addUser(String username, String password, String email, Address address,
                        User.Role role) throws InvalidEmailFormat, InvalidUsernameFormat, DuplicatedUsernameEmail {
        User user = new User(username, password, email, address, role);
        if (!validateUsername(username)) {
            throw new InvalidUsernameFormat();
        }
        if (!validateEmail(email)) {
            throw new InvalidEmailFormat();
        }
        if (!userIsTaken(username, email, users)) {
            throw new DuplicatedUsernameEmail();
        }

        users.add(user);
    }

    public void addRestaurant(String name, String manager, String type, LocalTime startTime, LocalTime endTime,
                              String description, Address address) {
        User managerUser = findManager(manager, users);
        Restaurant restaurant = new Restaurant(name, managerUser, type, startTime, endTime, description, address);

        restaurants.add(restaurant);
    }

    public void addTable() {

    }

    public void reserveTable() {

    }

    public void cancelReservation() {

    }

    public void showReservationHistory() {

    }

    public void searchRestaurantsByName() {

    }

    public void searchRestaurantsByType() {

    }

    public void showAvailableTables() {

    }

    public void addReview() {

    }
}
