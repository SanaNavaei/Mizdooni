package mizdooni.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
    private int tableNumber;
    private int restaurantId;
    private int seatsNumber;
    private List<Reservation> reservations;

    public Table(int tableNumber, int restaurantId, int seatsNumber) {
        this.tableNumber = tableNumber;
        this.restaurantId = restaurantId;
        this.seatsNumber = seatsNumber;
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public boolean isReserved(LocalDateTime datetime) {
        for (Reservation r : reservations) {
            if (r.getDateTime().equals(datetime) && !r.isCancelled()) {
                return true;
            }
        }
        return false;
    }

    public Map<LocalDate, List<LocalTime>> getReservationsDate() {
        Map<LocalDate, List<LocalTime>> reservationsDate = new HashMap<>();

        for (Reservation r : reservations) {
            if (r.isCancelled()) {
                continue;
            }
            List<LocalTime> reservedHours = reservationsDate.get(r.getDateTime().toLocalDate());
            if (reservedHours == null) {
                reservedHours = new ArrayList<>();
            }
            reservedHours.add(r.getDateTime().toLocalTime());
            reservationsDate.put(r.getDateTime().toLocalDate(), reservedHours);
        }

        return reservationsDate;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public List<Reservation> getReservations() {
        return reservations;
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
