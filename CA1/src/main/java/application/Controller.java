package application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import exceptions.DuplicatedUsernameEmail;
import exceptions.InvalidEmailFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.InvalidUsernameFormat;

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

    public JsonNode addUser(String json) {
        JsonNode node = stringToJson(json);
        String username = node.get("username").asText();
        String password = node.get("password").asText();
        String email = node.get("email").asText();
        Address address;

        try {
            String country = node.get("address").get("country").asText();
            String city = node.get("address").get("city").asText();
            address = new Address(country, city, null);
        } catch (NullPointerException ex) {
            return createResultJson(false, TextNode.valueOf("Address is not complete."));
        }

        User.Role role = User.Role.valueOf(node.get("role").asText());

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
