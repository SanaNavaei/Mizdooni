package mizdooni.model;

import jakarta.persistence.*;
import mizdooni.model.user.User;

import java.time.LocalDateTime;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;

    private LocalDateTime datetime;
    private int reservationNumber;
    private boolean cancelled;

    public Reservation() {
    }

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

    public User getUser() {
        return user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Table getTable() {
        return table;
    }

    public LocalDateTime getDateTime() {
        return datetime;
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public boolean isPastTime() {
        return datetime.isBefore(LocalDateTime.now());
    }
}
