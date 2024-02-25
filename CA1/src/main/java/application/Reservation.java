package application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private User user;
    private Restaurant restaurant;
    private Table table;
    private LocalDateTime datetime;
    private int reservationNumber;
    private boolean cancelled;

    public Reservation(User user, Restaurant restaurant, Table table, LocalDateTime datetime) {
        this.user = user;
        this.restaurant = restaurant;
        this.table = table;
        this.datetime = datetime;
        this.reservationNumber = -1;
        this.cancelled = false;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public LocalDateTime getDateTime() {
        return datetime;
    }

    public Table getTable() {
        return table;
    }

    public JsonNode toJson() {
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("reservationNumber", reservationNumber);
        node.put("restaurantName", restaurant.getName());
        node.put("tableNumber", table.getTableNumber());
        node.put("datetime", datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return node;
    }
}
