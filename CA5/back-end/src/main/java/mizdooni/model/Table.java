package mizdooni.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tableNumber;

    @ManyToOne
    private Restaurant restaurant;
    private int seatsNumber;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    public Table() {

    }

    public Table(int tableNumber, Restaurant restaurant, int seatsNumber) {
        this.tableNumber = tableNumber;
        this.restaurant = restaurant;
        this.seatsNumber = seatsNumber;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public boolean isReserved(LocalDateTime datetime) {
        return reservations.stream().anyMatch(r -> r.getDateTime().equals(datetime) && !r.isCancelled());
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
