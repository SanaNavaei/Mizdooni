package application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

public class ReviewControllerTest {
    private MizDooni mizdooni;
    private Controller controller;
    private User client;
    private User manager;
    private Restaurant restaurant;

    @BeforeEach
    public void setup() {
        mizdooni = new MizDooni();
        controller = new Controller(mizdooni);
        client = new User("client", "password", "client@email.com",
                new Address("Iran", "Sari", null), User.Role.client);
        manager = new User("manager", "password", "manager@email.com",
                new Address("Iran", "Karaj", null), User.Role.manager);
        restaurant = new Restaurant("restaurant", manager, "iranian",
                LocalTime.of(8, 0), LocalTime.of(23, 0), "description",
                new Address("Iran", "Tehran", "North Kargar"));
        mizdooni.users.add(client);
        mizdooni.users.add(manager);
        mizdooni.restaurants.add(restaurant);
    }

    static private String createReviewJson(double foodRate, double serviceRate, double ambianceRate, double overallRate) {
        ObjectNode input = new ObjectMapper().createObjectNode();
        input.put("username", "client");
        input.put("restaurantName", "restaurant");
        input.put("foodRate", foodRate);
        input.put("serviceRate", serviceRate);
        input.put("ambianceRate", ambianceRate);
        input.put("overallRate", overallRate);
        input.put("comment", "comment");
        return input.toString();
    }

    @Test
    @DisplayName("Controller add review successfully")
    public void testAddReviewControllerSuccess() {
        String input = createReviewJson(1.2, 2.4, 3.6, 4.8);
        JsonNode result = controller.addReview(input);
        Assertions.assertTrue(result.get("success").asBoolean());
        Assertions.assertEquals("Review added successfully.", result.get("data").asText());
    }

    @Test
    @DisplayName("Controller add review failure")
    public void testAddReviewControllerFail() {
        String input = createReviewJson(1.2, 10.4, 3.6, 4.8);
        JsonNode result = controller.addReview(input);
        Assertions.assertFalse(result.get("success").asBoolean());
        Assertions.assertEquals("Review rating parameter <Service> out of range.", result.get("data").asText());
    }
}
