package mizdooni.database;

import com.fasterxml.jackson.databind.JsonNode;
import mizdooni.model.*;
import mizdooni.model.user.Client;
import mizdooni.model.user.Manager;
import mizdooni.model.user.User;
import mizdooni.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DataLoader {
    private Database db;
    private UserRepository userRepository;

    public DataLoader(Database database, UserRepository userRepository) {
        this.db = database;
        this.userRepository = userRepository;
    }

    public void read() {
        readUsers();
        readRestaurants();
        readTables();
        readReviews();
    }

    private void readUsers() {
        JsonNode usersList = IEApi.getResponse(IEApi.Api.USERS);
        if (usersList == null) {
            return;
        }

        int id = 0;
        for (JsonNode node : usersList) {
            String username = node.get("username").asText();
            String password = node.get("password").asText();
            String email = node.get("email").asText();
            String roleStr = node.get("role").asText();
            String country = node.get("address").get("country").asText();
            String city = node.get("address").get("city").asText();
            User.Role role = User.Role.valueOf(roleStr);
            Address address = new Address(country, city, null);

            User user = null;
            if (role == User.Role.client) {
                user = new Client(id, username, password, email, address);
            } else if (role == User.Role.manager) {
                user = new Manager(id, username, password, email, address);
            }
            db.users.add(user);
            userRepository.save(user);
            id++;
        }
    }

    private void readRestaurants() {
        JsonNode restaurantsList = IEApi.getResponse(IEApi.Api.RESTAURANTS);
        if (restaurantsList == null) {
            return;
        }

        int id = 0;
        for (JsonNode node : restaurantsList) {
            User manager = getUserByUsername(node.get("managerUsername").asText());

            LocalTime startTime = LocalTime.parse(node.get("startTime").asText());
            LocalTime endTime = LocalTime.parse(node.get("endTime").asText());

            String country = node.get("address").get("country").asText();
            String city = node.get("address").get("city").asText();
            String street = node.get("address").get("street").asText();

            Restaurant restaurant = new Restaurant(
                    id,
                    node.get("name").asText(),
                    manager,
                    node.get("type").asText(),
                    startTime,
                    endTime,
                    node.get("description").asText(),
                    new Address(country, city, street),
                    node.get("image").asText()
            );
            db.restaurants.add(restaurant);
            id++;
        }
    }

    private void readTables() {
        JsonNode tablesList = IEApi.getResponse(IEApi.Api.TABLES);
        if (tablesList == null) {
            return;
        }

        for (JsonNode node : tablesList) {
            Restaurant restaurant = getRestaurantByName(node.get("restaurantName").asText());

            int tableNumber = node.get("tableNumber").asInt();
            int seatsNumber = node.get("seatsNumber").asInt();
            MizTable table = new MizTable(tableNumber, restaurant, seatsNumber);
            restaurant.addTable(table);
        }
    }

    private void readReviews() {
        JsonNode reviewsList = IEApi.getResponse(IEApi.Api.REVIEWS);
        if (reviewsList == null) {
            return;
        }

        for (JsonNode node : reviewsList) {
            Restaurant restaurant = getRestaurantByName(node.get("restaurantName").asText());
            User user = getUserByUsername(node.get("username").asText());

            Rating rating = new Rating();
            rating.food = node.get("foodRate").asDouble();
            rating.service = node.get("serviceRate").asDouble();
            rating.ambiance = node.get("ambianceRate").asDouble();
            rating.overall = node.get("overallRate").asDouble();

            String comment = node.get("comment").asText();
            restaurant.addReview(new Review(user, rating, comment, LocalDateTime.now()));
        }
    }

    private User getUserByUsername(String username) {
        return db.users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }

    private Restaurant getRestaurantByName(String name) {
        return db.restaurants.stream().filter(r -> r.getName().equals(name)).findFirst().orElse(null);
    }
}
