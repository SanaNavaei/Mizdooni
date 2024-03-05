package database;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Address;
import model.Restaurant;
import model.User;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

public class DataLoader {
    private final String USERS_FILE = "data/users.json";
    private final String RESTAURANTS_FILE = "data/restaurants.json";

    private ObjectMapper objectMapper;
    private Database db;

    public DataLoader(Database database) {
        objectMapper = new ObjectMapper();
        db = database;
    }

    public void read() {
        readUsers();
        readRestaurants();
    }

    private void readUsers() {
        JsonNode usersList = null;
        try {
            usersList = objectMapper.readTree(new File(USERS_FILE));
        } catch (IOException ex) {

        }
        for (JsonNode node : usersList) {
            String role = node.get("role").asText();
            String country = node.get("address").get("country").asText();
            String city = node.get("address").get("city").asText();
            User user = new User(
                    node.get("username").asText(),
                    node.get("password").asText(),
                    node.get("email").asText(),
                    new Address(country, city, null),
                    User.Role.valueOf(role)
            );
            db.users.add(user);
        }
    }

    private void readRestaurants() {
        JsonNode restaurantsList = null;
        try {
            restaurantsList = objectMapper.readTree(new File(RESTAURANTS_FILE));
        } catch (IOException ex) {

        }
        for (JsonNode node : restaurantsList) {
            String managerUsername = node.get("managerUsername").asText();
            User manager = db.users.stream().filter(u -> u.getUsername().equals(managerUsername)).findFirst().orElse(null);
            LocalTime startTime = LocalTime.parse(node.get("startTime").asText());
            LocalTime endTime = LocalTime.parse(node.get("endTime").asText());
            String country = node.get("address").get("country").asText();
            String city = node.get("address").get("city").asText();
            String street = node.get("address").get("street").asText();

            Restaurant restaurant = new Restaurant(
                    node.get("name").asText(),
                    manager,
                    node.get("type").asText(),
                    startTime,
                    endTime,
                    node.get("description").asText(),
                    new Address(country, city, street)
            );
            db.restaurants.add(restaurant);
        }
    }
}
