package mizdooni.database;

import com.fasterxml.jackson.databind.JsonNode;
import mizdooni.model.*;
import mizdooni.model.user.Client;
import mizdooni.model.user.Manager;
import mizdooni.model.user.User;
import mizdooni.service.Crypto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DataLoader {
    private Database db;

    public DataLoader(Database database) {
        this.db = database;
    }

    public void read() {
        if (db.userRepository.count() == 0) {
            readUsers();
        }
        if (db.restaurantRepository.count() == 0) {
            readRestaurants();
        }
        if (db.mizTableRepository.count() == 0) {
            readTables();
        }
        if (db.reviewRepository.count() == 0) {
            readReviews();
        }
    }

    private void readUsers() {
        JsonNode usersList = IEApi.getResponse(IEApi.Api.USERS);
        if (usersList == null) {
            return;
        }

        int id = 0;
        for (JsonNode node : usersList) {
            String username = node.get("username").asText();
            String password = Crypto.hashPassword(node.get("password").asText());
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
            db.userRepository.save(user);
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
            User manager = db.userRepository.findByUsername(node.get("managerUsername").asText());

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
            db.restaurantRepository.save(restaurant);
            id++;
        }
    }

    private void readTables() {
        JsonNode tablesList = IEApi.getResponse(IEApi.Api.TABLES);
        if (tablesList == null) {
            return;
        }

        for (JsonNode node : tablesList) {
            Restaurant restaurant = db.restaurantRepository.findByName(node.get("restaurantName").asText());
            int seatsNumber = node.get("seatsNumber").asInt();
            MizTable table = new MizTable(0, restaurant, seatsNumber);
            db.mizTableRepository.save(table);
        }
    }

    private void readReviews() {
        JsonNode reviewsList = IEApi.getResponse(IEApi.Api.REVIEWS);
        if (reviewsList == null) {
            return;
        }

        for (JsonNode node : reviewsList) {
            Restaurant restaurant = db.restaurantRepository.findByName(node.get("restaurantName").asText());
            User user = db.userRepository.findByUsername(node.get("username").asText());

            String comment = node.get("comment").asText();
            Rating rating = new Rating();
            rating.food = node.get("foodRate").asDouble();
            rating.service = node.get("serviceRate").asDouble();
            rating.ambiance = node.get("ambianceRate").asDouble();
            rating.overall = node.get("overallRate").asDouble();

            Review review = new Review(user, restaurant, rating, comment, LocalDateTime.now());
            db.reviewRepository.save(review);
        }
    }
}
