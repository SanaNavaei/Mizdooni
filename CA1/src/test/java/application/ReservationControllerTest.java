package application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationControllerTest {
    private MizDooni mizdooni;
    private Controller controller;
    private User client;
    private User manager;
    private Restaurant restaurant;
    private Table table;
    private LocalDateTime datetime;

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
        table = new Table(1, "restaurant", 4);
        datetime = LocalDateTime.of(2030, 12, 12, 12, 0);
        restaurant.addTable(table);
        mizdooni.users.add(client);
        mizdooni.users.add(manager);
        mizdooni.restaurants.add(restaurant);
    }

    static private String createReservationJson(String username, String restaurantName, int tableNumber, LocalDateTime datetime) {
        ObjectNode input = new ObjectMapper().createObjectNode();
        input.put("username", username);
        input.put("restaurantName", restaurantName);
        input.put("tableNumber", tableNumber);
        input.put("datetime", datetime.format(Controller.datetimeFormatter));
        return input.toString();
    }

    @Test
    @DisplayName("Controller add reservation successfully")
    public void testAddReservationControllerSuccess() {
        String input = createReservationJson("client", "restaurant", 1, datetime);
        JsonNode result = controller.reserveTable(input);
        Assertions.assertEquals(true, result.get("success").asBoolean());
        Assertions.assertEquals(table.getReservations().get(0).getReservationNumber(), result.get("data").get("reservationNumber").asInt());
    }

    @Test
    @DisplayName("Controller add reservation failure")
    public void testAddReservationControllerFail() {
        String input = createReservationJson("client", "restaurant", 1, datetime);
        JsonNode result = controller.reserveTable(input);
        Assertions.assertEquals(true, result.get("success").asBoolean());
        result = controller.reserveTable(input);
        Assertions.assertEquals(false, result.get("success").asBoolean());
        Assertions.assertEquals("Table is already reserved.", result.get("data").asText());
    }
}
