package application;

import java.time.LocalTime;
import java.util.ArrayList;

public class Utils {
    static public boolean validateUsername(String username) {
        String usernameFormat = "^\\w+$";
        return username.matches(usernameFormat);
    }

    static public boolean validateEmail(String email) {
        String emailFormat = "^\\w+@\\w+\\.\\w+$";
        return email.matches(emailFormat);
    }

    static public boolean userIsTaken(String username, String email, ArrayList<User> users) {
        for (User u : users) {
            if (u.getUsername().equals(username) || u.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    static public User findManager(String username, ArrayList<User> users) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getRole() == User.Role.manager) {
                return u;
            }
        }
        return null;
    }

    static public boolean restaurantIsTaken(String name, ArrayList<Restaurant> restaurants) {
        return findRestaurantByName(name, restaurants) != null;
    }

    static public boolean validateWorkingTime(LocalTime time) {
        return time.getMinute() == 0;
    }

    static public Restaurant findRestaurantByName(String name, ArrayList<Restaurant> restaurants) {
        for (Restaurant r : restaurants) {
            if (r.getName().equals(name)) {
                return r;
            }
        }
        return null;
    }

    static public boolean tableIsTaken(int tableNumber, Restaurant restaurant) {
        ArrayList<Table> tables = restaurant.getTables();
        for (Table t : tables) {
            if (t.getTableNumber() == tableNumber) {
                return false;
            }
        }
        return true;
    }

    static public boolean validateSeatsNumber(String seatsNumber) {
        String seatsNumberFormat = "^[1-9][0-9]*$";
        return seatsNumber.matches(seatsNumberFormat);
    }

    static public boolean validateManagerRestaurant(User managerUser, Restaurant restaurant) {
        return restaurant.getManager().equals(managerUser);
    }

    static public ArrayList<Restaurant> findRestaurantsByType(String type, ArrayList<Restaurant> restaurants) {
        ArrayList<Restaurant> result = new ArrayList<>();
        for (Restaurant r : restaurants) {
            if (r.getType().equals(type)) {
                result.add(r);
            }
        }
        return result;
    }
}
