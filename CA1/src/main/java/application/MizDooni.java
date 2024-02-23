package application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import exceptions.*;

import java.time.LocalTime;
import java.util.ArrayList;

import static application.Controller.createRestaurantJson;
import static application.Utils.*;

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
                              String description, Address address) throws DuplicatedRestaurantName, ManagerNotFound, InvalidWorkingTime {
        User managerUser = findManager(manager, users);
        Restaurant restaurant = new Restaurant(name, managerUser, type, startTime, endTime, description, address);

        if (managerUser == null) {
            throw new ManagerNotFound();
        }
        if (!restaurantIsTaken(name, restaurants)) {
            throw new DuplicatedRestaurantName();
        }
        if (!validateWorkingTime(startTime) || !validateWorkingTime(endTime)) {
            throw new InvalidWorkingTime();
        }

        restaurants.add(restaurant);
    }

    public void addTable(int tableNumber, String restaurantName, String manager, String seatsNumber)
                        throws DuplicatedTableNumber, InvalidSeatsNumber, RestaurantNotFound, ManagerNotFound, InvalidManagerRestaurant {
        User managerUser = findManager(manager, users);
        int seatsNumberInt = (int)Double.parseDouble(seatsNumber);
        Table table = new Table(tableNumber, restaurantName, managerUser, seatsNumberInt);
        Restaurant restaurant = findRestaurant(restaurantName, restaurants);

        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        if (managerUser == null) {
            throw new ManagerNotFound();
        }
        if (!validateManagerRestaurant(managerUser, restaurant)) {
            throw new InvalidManagerRestaurant();
        }
        if (!tableIsTaken(tableNumber, restaurant)) {
            throw new DuplicatedTableNumber();
        }
        if (!validateSeatsNumber(seatsNumber)) {
            throw new InvalidSeatsNumber();
        }
        restaurant.addTable(table);
    }

    public void reserveTable() {

    }

    public void cancelReservation() {

    }

    public void showReservationHistory() {

    }

    public JsonNode searchRestaurantsByName(String restaurantName) throws RestaurantNotFound {
        Restaurant restaurant = findRestaurant(restaurantName, restaurants);

        if (restaurant == null) {
            throw new RestaurantNotFound();
        }

        ArrayList<JsonNode> restaurantJsons = new ArrayList<>();

        String name = restaurant.getName();
        String type = restaurant.getType();
        String startTime = restaurant.getStartTime().toString();
        String endTime = restaurant.getEndTime().toString();
        String description = restaurant.getDescription();
        String country = restaurant.getAddress().getCountry();
        String city = restaurant.getAddress().getCity();
        String street = restaurant.getAddress().getStreet();

        restaurantJsons.add(createRestaurantJson(name, type, startTime, endTime, description, country, city, street));

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("restaurants", mapper.valueToTree(restaurantJsons));
        return node;
    }

    public JsonNode searchRestaurantsByType(String restaurantType) {
        ArrayList<Restaurant> restaurantResults = findRestaurantsByType(restaurantType, restaurants);
        ArrayList<JsonNode> restaurantJsons = new ArrayList<>();

        for (Restaurant r : restaurantResults) {
            String name = r.getName();
            String type = r.getType();
            String startTime = r.getStartTime().toString();
            String endTime = r.getEndTime().toString();
            String description = r.getDescription();
            String country = r.getAddress().getCountry();
            String city = r.getAddress().getCity();
            String street = r.getAddress().getStreet();

            restaurantJsons.add(createRestaurantJson(name, type, startTime, endTime, description, country, city, street));
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("restaurants", mapper.valueToTree(restaurantJsons));
        return node;
    }

    public void showAvailableTables() {

    }

    public void addReview() {

    }
}
