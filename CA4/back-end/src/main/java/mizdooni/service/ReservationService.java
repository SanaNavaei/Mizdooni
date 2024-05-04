package mizdooni.service;

import mizdooni.database.Database;
import mizdooni.exceptions.*;
import mizdooni.model.Reservation;
import mizdooni.model.Restaurant;
import mizdooni.model.Table;
import mizdooni.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private Database db;
    @Autowired
    private UserService userService;

    public List<Reservation> getReservations(int restaurantId, int tableNumber)
            throws RestaurantNotFound, ManagerNotFound, TableNotFound {
        User manager = userService.getCurrentUser();
        Restaurant restaurant = ServiceUtils.findRestaurant(restaurantId, db.restaurants);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        if (manager == null || manager.getRole() != User.Role.manager || !restaurant.getManager().equals(manager)) {
            throw new ManagerNotFound();
        }

        Table table = restaurant.getTable(tableNumber);
        if (table == null) {
            throw new TableNotFound();
        }
        return table.getReservations();
    }

    public List<Reservation> getCustomerReservations() throws UserNotFound {
        User user = userService.getCurrentUser();
        if (user == null) {
            throw new UserNotFound();
        }
        return user.getReservations();
    }

    public Reservation reserveTable(int restaurantId, int tableNumber, LocalDateTime datetime)
            throws UserNotFound, ManagerReservationNotAllowed, InvalidWorkingTime, RestaurantNotFound, TableNotFound,
            DateTimeInThePast, ReservationNotInOpenTimes, TableAlreadyReserved {
        User user = userService.getCurrentUser();
        if (user == null) {
            throw new UserNotFound();
        }
        if (user.getRole() == User.Role.manager) {
            throw new ManagerReservationNotAllowed();
        }

        if (!ServiceUtils.validateWorkingTime(datetime.toLocalTime())) {
            throw new InvalidWorkingTime();
        }
        if (datetime.isBefore(LocalDateTime.now())) {
            throw new DateTimeInThePast();
        }

        Restaurant restaurant = ServiceUtils.findRestaurant(restaurantId, db.restaurants);
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

    public void cancelReservation(int reservationNumber) throws UserNotFound, ReservationNotFound, ReservationCannotBeCancelled {
        User user = userService.getCurrentUser();
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
}
