package application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Table {
    private int tableNumber;
    private String restaurantName;
    private int seatsNumber;
    private List<Reservation> reservations;

    public Table(int tableNumber, String restaurantName, int seatsNumber) {
        this.tableNumber = tableNumber;
        this.restaurantName = restaurantName;
        this.seatsNumber = seatsNumber;
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public boolean isReserved(LocalDateTime datetime) {
        for (Reservation r : reservations) {
            if (r.getDateTime().equals(datetime) && !r.isCancelled()) {
                return true;
            }
        }
        return false;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Map<LocalDate, List<LocalTime>> findReservationsDate() {
        Map<LocalDate, List<LocalTime>> reservationDate = new HashMap<>();

        for (Reservation r : reservations) {
            if (r.isCancelled()) {
                continue;
            }
            List<LocalTime> availableHours = reservationDate.get(r.getDateTime().toLocalDate());
            if (availableHours == null) {
                availableHours = new ArrayList<>();
            }
            availableHours.add(r.getDateTime().toLocalTime());
            reservationDate.put(r.getDateTime().toLocalDate(), availableHours);
        }

        return reservationDate;
    }

    public JsonNode toJson(List<String> timeAndDate) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("tableNumber", tableNumber);
        node.put("seatsNumber", seatsNumber);
        node.set("availableTimes", objectMapper.valueToTree(timeAndDate));
        return node;
    }
}
