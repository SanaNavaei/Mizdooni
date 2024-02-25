package application;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Table {
    private int tableNumber;
    private String restaurantName;
    private int seatsNumber;
    private ArrayList<Reservation> reservations;

    public Table(int tableNumber, String restaurantName, int seatsNumber) {
        this.tableNumber = tableNumber;
        this.restaurantName = restaurantName;
        this.seatsNumber = seatsNumber;
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public boolean isReserved(LocalDateTime datetime) {
        for (Reservation r : reservations) {
            if (r.getDateTime().equals(datetime)) {
                return true;
            }
        }
        return false;
    }
}
