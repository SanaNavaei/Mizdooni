package application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import exceptions.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalTime;

public class Controller {
    private MizDooni mizdooni;
    private ObjectMapper objectMapper = new ObjectMapper();

    public Controller(MizDooni mizdooni) {
        this.mizdooni = mizdooni;
    }

    private JsonNode stringToJson(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException ex) {
            return null;
        }
    }

    private JsonNode createResultJson(boolean success, JsonNode data) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("success", success);
        node.set("data", data);
        return node;
    }

    static public JsonNode createRestaurantJson(String name, String type, String startTime, String endTime, String description, String country, String city, String street) {
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("name", name);
        node.put("type", type);
        node.put("startTime", startTime);
        node.put("endTime", endTime);
        node.put("description", description);

        ObjectNode address = new ObjectMapper().createObjectNode();
        address.put("country", country);
        address.put("city", city);
        address.put("street", street);
        node.set("address", address);

        return node;
    }

    public JsonNode addUser(String json) {
        JsonNode node = stringToJson(json);
        String username = node.get("username").asText();
        String password = node.get("password").asText();
        String email = node.get("email").asText();
        Address address;
        User.Role role;

        try {
            String country = node.get("address").get("country").asText();
            String city = node.get("address").get("city").asText();
            address = new Address(country, city, null);
        } catch (NullPointerException ex) {
            return createResultJson(false, TextNode.valueOf("Address is not complete."));
        }

        try {
            role = User.Role.valueOf(node.get("role").asText());
        } catch (IllegalArgumentException ex) {
            return createResultJson(false, TextNode.valueOf("Role is not valid."));
        }

        boolean success = true;
        String data = "User added successfully.";

        try {
            mizdooni.addUser(username, password, email, address, role);
        } catch (InvalidUsernameFormat | InvalidEmailFormat | DuplicatedUsernameEmail ex) {
            success = false;
            data = ex.getMessage();
        }

        return createResultJson(success, TextNode.valueOf(data));
    }

    public JsonNode addRestaurant(String json) {
        JsonNode node = stringToJson(json);
        String name = node.get("name").asText();
        String manager = node.get("managerUsername").asText();
        String type = node.get("type").asText();
        String description = node.get("description").asText();
        Address address;

        LocalTime startTime = LocalTime.parse(node.get("startTime").asText());
        LocalTime endTime = LocalTime.parse(node.get("endTime").asText());

        try {
            String country = node.get("address").get("country").asText();
            String city = node.get("address").get("city").asText();
            String street = node.get("address").get("street").asText();
            address = new Address(country, city, street);
        } catch (NullPointerException ex) {
            return createResultJson(false, TextNode.valueOf("Address is not complete."));
        }

        boolean success = true;
        String data = "Restaurant added successfully.";

        try{
            mizdooni.addRestaurant(name, manager, type, startTime, endTime, description, address);
        } catch (DuplicatedRestaurantName | ManagerNotFound | InvalidWorkingTime ex) {
            success = false;
            data = ex.getMessage();
        }

        return createResultJson(success, TextNode.valueOf(data));
    }

    public JsonNode addTable(String json) {
        JsonNode node = stringToJson(json);
        int tableNumber = node.get("tableNumber").asInt();
        String restaurantName = node.get("restaurantName").asText();
        String manager = node.get("managerUsername").asText();
        String seatsNumber = node.get("seatsNumber").asText();

        boolean success = true;
        String data = "Table added successfully.";

        try {
            mizdooni.addTable(tableNumber, restaurantName, manager, seatsNumber);
        } catch (DuplicatedTableNumber | InvalidSeatsNumber | RestaurantNotFound | ManagerNotFound | InvalidManagerRestaurant ex) {
            success = false;
            data = ex.getMessage();
        }

        return createResultJson(success, TextNode.valueOf(data));
    }

    public void reserveTable() {

    }

    public void cancelReservation() {

    }

    public void showReservationHistory() {

    }

    public JsonNode searchRestaurantsByName(String json) {
        JsonNode node = stringToJson(json);
        String restaurantName = node.get("name").asText();

        boolean success = true;
        JsonNode data = TextNode.valueOf("No restaurant found.");

        try{
            data = mizdooni.searchRestaurantsByName(restaurantName);
        } catch (RestaurantNotFound ex) {
            success = false;
        }

        return createResultJson(success, data);
    }

    public void searchRestaurantsByType() {

    }

    public void showAvailableTables() {

    }

    public void addReview() {

    }
}
