package application;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public Map<LocalDate, List<Integer>> findReservationsDate() {
        Map<LocalDate, List<Integer>> reservationDate = new HashMap<>();

        for (Reservation r : reservations) {
            if (!r.isCancelled()) {
                if (reservationDate.containsKey(r.getDateTime().toLocalDate())) {
                    List<Integer> AvailableHours = reservationDate.get(r.getDateTime().toLocalDate());
                    AvailableHours.add(r.getDateTime().getHour());
                    reservationDate.put(r.getDateTime().toLocalDate(), AvailableHours);
                } else {
                    List<Integer> AvailableHours = new ArrayList<>();
                    AvailableHours.add(r.getDateTime().getHour());
                    reservationDate.put(r.getDateTime().toLocalDate(), AvailableHours);
                }
            }
        }

        return reservationDate;
    }

    public JsonNode toJson(List<String> TimeAndDate) {
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("tableNumber", tableNumber);
        node.put("seatsNumber", seatsNumber);
        node.put("availableTimes", TimeAndDate.toString());

        return node;
    }
}
