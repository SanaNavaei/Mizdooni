package mizdooni.service;

import mizdooni.database.Database;
import mizdooni.exceptions.*;
import mizdooni.model.Reservation;
import mizdooni.model.Restaurant;
import mizdooni.model.Table;
import mizdooni.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static mizdooni.service.Utils.*;

public class ReservationService {
    private Database db;
    private User currentUser;

    private static final ReservationService instance = new ReservationService();

    private ReservationService() {
        db = Database.getInstance();
        currentUser = null;
    }

    public static ReservationService getInstance() {
        return instance;
    }

    public Reservation reserveTable(String username, String restaurantName, int tableNumber, LocalDateTime datetime)
            throws UserNotFound, ManagerReservationNotAllowed, InvalidWorkingTime, RestaurantNotFound, TableNotFound,
            DateTimeInThePast, ReservationNotInOpenTimes, TableAlreadyReserved {
        User user = findUser(username, db.users);
        if (user == null) {
            throw new UserNotFound();
        }
        if (user.getRole() == User.Role.manager) {
            throw new ManagerReservationNotAllowed();
        }

        if (!validateWorkingTime(datetime.toLocalTime())) {
            throw new InvalidWorkingTime();
        }
        if (datetime.isBefore(LocalDateTime.now())) {
            throw new DateTimeInThePast();
        }

        Restaurant restaurant = findRestaurantByName(restaurantName, db.restaurants);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        Table table = restaurant.getTable(tableNumber);
        if (table == null) {
            throw new TableNotFound();
        }
        if (datetime.toLocalTime().isBefore(restaurant.getStartTime()) ||
                datetime.toLocalTime().isAfter(restaurant.getEndTime())) {
            throw new ReservationNotInOpenTimes();
        }
        if (table.isReserved(datetime)) {
            throw new TableAlreadyReserved();
        }

        Reservation reservation = new Reservation(user, restaurant, table, datetime);
        user.addReservation(reservation);
        table.addReservation(reservation);
        return reservation;
    }

    public void cancelReservation(String username, int reservationNumber)
            throws UserNotFound, ReservationNotFound, ReservationCannotBeCancelled {
        User user = findUser(username, db.users);
        if (user == null) {
            throw new UserNotFound();
        }

        Reservation reservation = user.getReservation(reservationNumber);
        if (reservation == null) {
            throw new ReservationNotFound();
        }

        if (reservation.getDateTime().isBefore(LocalDateTime.now())) {
            throw new ReservationCannotBeCancelled();
        }

        reservation.cancel();
    }

    public List<Reservation> showReservationHistory(String username) throws UserNotFound {
        User user = findUser(username, db.users);
        if (user == null) {
            throw new UserNotFound();
        }

        return user.getReservations();
    }
}
