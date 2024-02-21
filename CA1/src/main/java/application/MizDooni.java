package application;

import exceptions.InvalidEmailFormat;
import exceptions.InvalidUsernameFormat;

import java.util.ArrayList;

import static application.Utils.validateEmail;
import static application.Utils.validateUsername;

public class MizDooni {
    ArrayList<User> users = new ArrayList<>();

    public void addUser(String username, String password, String email, Address address,
                        User.Role role) throws InvalidEmailFormat, InvalidUsernameFormat {
        User user = new User(username, password, email, address, role);
        if (!validateUsername(username)) {
            throw new InvalidUsernameFormat();
        }
        if (!validateEmail(email)) {
            throw new InvalidEmailFormat();
        }
        users.add(user);
    }

    public void addRestaurant() {

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
