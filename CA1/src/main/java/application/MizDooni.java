package application;

import com.fasterxml.jackson.databind.JsonNode;
import exceptions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static application.Utils.*;

public class MizDooni {
    List<User> users;
    List<Restaurant> restaurants;

    public MizDooni() {
        users = new ArrayList<>();
        restaurants = new ArrayList<>();
    }

    public void addUser(String username, String password, String email, Address address,
                        User.Role role) throws InvalidEmailFormat, InvalidUsernameFormat, DuplicatedUsernameEmail {
        if (!validateUsername(username)) {
            throw new InvalidUsernameFormat();
        }
        if (!validateEmail(email)) {
            throw new InvalidEmailFormat();
        }
        if (!userIsTaken(username, email, users)) {
            throw new DuplicatedUsernameEmail();
        }

        User user = new User(username, password, email, address, role);
        users.add(user);
    }

    public void addRestaurant(String name, String manager, String type, LocalTime startTime, LocalTime endTime,
                              String description, Address address) throws DuplicatedRestaurantName, ManagerNotFound, InvalidWorkingTime {
        User managerUser = findUser(manager, users);

        if (findRestaurantByName(name, restaurants) != null) {
            throw new DuplicatedRestaurantName();
        }
        if (managerUser == null || managerUser.getRole() != User.Role.manager) {
            throw new ManagerNotFound();
        }
        if (!validateWorkingTime(startTime) || !validateWorkingTime(endTime)) {
            throw new InvalidWorkingTime();
        }

        Restaurant restaurant = new Restaurant(name, managerUser, type, startTime, endTime, description, address);
        restaurants.add(restaurant);
    }

    public void addTable(int tableNumber, String restaurantName, String manager, String seatsNumber)
            throws DuplicatedTableNumber, InvalidSeatsNumber, RestaurantNotFound, ManagerNotFound, InvalidManagerRestaurant {
        User managerUser = findUser(manager, users);
        int seatsNumberInt = (int) Double.parseDouble(seatsNumber);
        Restaurant restaurant = findRestaurantByName(restaurantName, restaurants);

        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        if (managerUser == null || managerUser.getRole() != User.Role.manager) {
            throw new ManagerNotFound();
        }
        if (!restaurant.getManager().equals(managerUser)) {
            throw new InvalidManagerRestaurant();
        }
        if (restaurant.getTable(tableNumber) != null) {
            throw new DuplicatedTableNumber();
        }
        if (!validateSeatsNumber(seatsNumber)) {
            throw new InvalidSeatsNumber();
        }

        Table table = new Table(tableNumber, restaurantName, seatsNumberInt);
        restaurant.addTable(table);
    }

    public Reservation reserveTable(String username, String restaurantName, int tableNumber, LocalDateTime datetime)
            throws UserNotFound, ManagerReservationNotAllowed, InvalidWorkingTime, RestaurantNotFound, TableNotFound,
            DateTimeInThePast, ReservationNotInOpenTimes, TableAlreadyReserved {
        User user = findUser(username, users);
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

        Restaurant restaurant = findRestaurantByName(restaurantName, restaurants);
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
        User user = findUser(username, users);
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
        User user = findUser(username, users);
        if (user == null) {
            throw new UserNotFound();
        }

        return user.getReservations();
    }

    public List<Restaurant> searchRestaurantsByName(String restaurantName) throws RestaurantNotFound {
        List<Restaurant> results = findRestaurantsByName(restaurantName, restaurants);
        if (results.isEmpty()) {
            throw new RestaurantNotFound();
        }

        return results;
    }

    public List<Restaurant> searchRestaurantsByType(String restaurantType) throws RestaurantNotFound {
        List<Restaurant> results = findRestaurantsByType(restaurantType, restaurants);
        if (results.isEmpty()) {
            throw new RestaurantNotFound();
        }

        return results;
    }

    public List<JsonNode> showAvailableTables(String restaurantName) throws RestaurantNotFound {
        Restaurant restaurant = findRestaurantByName(restaurantName, restaurants);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }

        return restaurant.showAvailableTables();
    }

    public void addReview(String username, String restaurantName, double foodRate, double serviceRate, double ambianceRate,
                          double overallRate, String comment) throws UserNotFound, ManagerCannotReview, RestaurantNotFound, InvalidReviewRating {
        User user = findUser(username, users);
        if (user == null) {
            throw new UserNotFound();
        }
        if (user.getRole() == User.Role.manager) {
            throw new ManagerCannotReview();
        }

        Restaurant restaurant = findRestaurantByName(restaurantName, restaurants);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }

        if (foodRate < 0 || foodRate > 5) {
            throw new InvalidReviewRating("Food");
        }
        if (serviceRate < 0 || serviceRate > 5) {
            throw new InvalidReviewRating("Service");
        }
        if (ambianceRate < 0 || ambianceRate > 5) {
            throw new InvalidReviewRating("Ambiance");
        }
        if (overallRate < 0 || overallRate > 5) {
            throw new InvalidReviewRating("Overall");
        }

        Review review = new Review(foodRate, serviceRate, ambianceRate, overallRate, comment, LocalDateTime.now());
        restaurant.addReview(review);
    }
}
