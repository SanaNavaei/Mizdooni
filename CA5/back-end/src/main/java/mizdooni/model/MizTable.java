package mizdooni.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MizTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int number;

    @ManyToOne
    @JoinColumn
    private Restaurant restaurant;

    @Column(nullable = false)
    private int seatsNumber;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Reservation> reservations;

    public MizTable() {

    }

    public MizTable(int tableNumber, Restaurant restaurant, int seatsNumber) {
        this.number = tableNumber;
        this.restaurant = restaurant;
        this.seatsNumber = seatsNumber;
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public boolean isReserved(LocalDateTime datetime) {
        return reservations.stream().anyMatch(r -> r.getDateTime().equals(datetime) && !r.isCancelled());
    }

    public int getTableNumber() {
        return number;
    }

    public void setTableNumber(int tableNumber) {
        this.number = tableNumber;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
