package application;

import java.time.LocalDateTime;

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
}
