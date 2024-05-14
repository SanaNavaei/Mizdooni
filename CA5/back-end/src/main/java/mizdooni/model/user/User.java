package mizdooni.model.user;

import jakarta.persistence.*;
import mizdooni.model.Address;
import mizdooni.model.Reservation;
import mizdooni.model.Restaurant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_role", discriminatorType = DiscriminatorType.STRING)
public abstract class User {
    public User() {

    }

    public enum Role {
        client,
        manager,
    }

    @Column(unique = true)
    private int id;

    @Id
    private String username;

    private String password;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Enumerated(EnumType.STRING)
    private Role role;
    private int reservationCounter;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    public User(String username, String password, String email, Address address, Role role) {
        this.id = 0;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.role = role;
        this.reservationCounter = 0;
    }

    public void addReservation(Reservation reservation) {
        reservation.setReservationNumber(reservationCounter);
        reservationCounter++;
        reservations.add(reservation);
    }

    public boolean checkReserved(Restaurant restaurant) {
        return reservations.stream().anyMatch(r -> !r.isCancelled() &&
                r.getDateTime().isBefore(LocalDateTime.now()) &&
                r.getRestaurant().equals(restaurant));
    }

    public Reservation getReservation(int reservationNumber) {
        for (Reservation r : reservations) {
            if (r.getReservationNumber() == reservationNumber && !r.isCancelled()) {
                return r;
            }
        }
        return null;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public boolean checkPassword(String pass) {
        return password.equals(pass);
    }

    public int getId() {
        return id;
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
