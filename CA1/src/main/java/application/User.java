package application;

import java.util.ArrayList;

public class User {
    public enum Role {
        client,
        manager,
    }

    private String username;
    private String password;
    private String email;
    private Address address;
    private Role role;
    private int reservationCounter;
    private ArrayList<Reservation> reservations;

    public User(String username, String password, String email, Address address, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.role = role;
        this.reservationCounter = 1;
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservation.setReservationNumber(reservationCounter);
        reservationCounter++;
        reservations.add(reservation);
    }

    public Reservation getReservation(int reservationNumber) {
        for (Reservation r : reservations) {
            if (r.getReservationNumber() == reservationNumber && !r.isCancelled()) {
                return r;
            }
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
