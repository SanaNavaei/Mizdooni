package mizdooni.service;

import mizdooni.exceptions.*;
import mizdooni.model.MizTable;
import mizdooni.model.Reservation;
import mizdooni.model.Restaurant;
import mizdooni.model.user.User;
import mizdooni.repository.MizTableRepository;
import mizdooni.repository.ReservationRepository;
import mizdooni.repository.RestaurantRepository;
import mizdooni.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private MizTableRepository mizTableRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public List<Reservation> getReservations(int userId, int restaurantId, int tableNumber, LocalDate date)
            throws RestaurantNotFound, UserNotManager, InvalidManagerRestaurant, TableNotFound {
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }

        User manager = userService.getUser(userId);
        if (manager == null || manager.getRole() != User.Role.manager) {
            throw new UserNotManager();
        }
        if (restaurant.getManager().getId() != manager.getId()) {
            throw new InvalidManagerRestaurant();
        }

        MizTable table = mizTableRepository.findByRestaurantIdAndTableNumber(restaurantId, tableNumber);
        if (table == null) {
            throw new TableNotFound();
        }

        List<Reservation> reservations;
        if (date == null) {
            reservations = reservationRepository.findByTableTableNumber(table.getTableNumber());
        } else {
            reservations = reservationRepository.findByTableTableNumberAndDate(table.getTableNumber(), date);
        }
        return reservations;
    }

    public List<Reservation> getCustomerReservations(int userId, int customerId) throws UserNotFound, UserNoAccess {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new UserNotFound();
        }
        if (user.getId() != customerId) {
            throw new UserNoAccess();
        }
        return reservationRepository.findByUserId(customerId);
    }

    @Transactional
    public List<LocalTime> getAvailableTimes(int restaurantId, int people, LocalDate date)
            throws RestaurantNotFound, DateTimeInThePast, BadPeopleNumber {
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }

        if (date.isBefore(LocalDate.now())) {
            throw new DateTimeInThePast();
        }
        if (people <= 0) {
            throw new BadPeopleNumber();
        }

        List<MizTable> peopleTables = mizTableRepository.findByRestaurantIdAndSeatsNumberGreaterThanEqual(restaurantId, people);
        Set<LocalTime> availableTimes = peopleTables.stream()
                .flatMap(table -> getAvailableTableTimes(table, date, restaurant).stream())
                .collect(Collectors.toSet());

        return availableTimes.stream().sorted().toList();
    }

    @Transactional
    public Reservation reserveTable(int userId, int restaurantId, int people, LocalDateTime datetime)
            throws UserNotFound, ManagerReservationNotAllowed, InvalidWorkingTime, RestaurantNotFound, TableNotFound,
            DateTimeInThePast, ReservationNotInOpenTimes {
        User user = userService.getUser(userId);
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

        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        if (datetime.toLocalTime().isBefore(restaurant.getStartTime()) ||
                datetime.toLocalTime().isAfter(restaurant.getEndTime())) {
            throw new ReservationNotInOpenTimes();
        }

        MizTable table = findAvailableTable(restaurant, people, datetime);
        if (table == null) {
            throw new TableNotFound();
        }

        Reservation reservation = new Reservation(user, restaurant, table, datetime);
        user.nextReservation(reservation);
        userRepository.updateReservationCounter(user.getId());
        reservationRepository.save(reservation);
        return reservation;
    }

    public void cancelReservation(int userId, int reservationNumber) throws UserNotFound, ReservationNotFound, ReservationCannotBeCancelled {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new UserNotFound();
        }

        Reservation reservation = reservationRepository.findByUserIdAndReservationNumber(user.getId(), reservationNumber);
        if (reservation == null) {
            throw new ReservationNotFound();
        }

        if (reservation.getDateTime().isBefore(LocalDateTime.now())) {
            throw new ReservationCannotBeCancelled();
        }

        reservation.cancel();
        reservationRepository.save(reservation);
    }

    private List<LocalTime> getAvailableTableTimes(MizTable table, LocalDate date, Restaurant restaurant) {
        Set<LocalTime> reserves = table.getReservations().stream()
                .filter(r -> r.getDateTime().toLocalDate().equals(date) && !r.isCancelled())
                .map(r -> r.getDateTime().toLocalTime())
                .collect(Collectors.toSet());

        List<LocalTime> availableTimes = new ArrayList<>();
        int startTime = restaurant.getStartTime().getHour();
        int endTime = restaurant.getEndTime().getHour();

        for (int i = startTime; i <= endTime; i++) {
            LocalTime time = LocalTime.of(i, 0);
            if (!reserves.contains(time)) {
                availableTimes.add(time);
            }
        }
        return availableTimes;
    }

    private MizTable findAvailableTable(Restaurant restaurant, int people, LocalDateTime datetime) {
        return restaurant.getTables().stream()
                .filter(table -> table.getSeatsNumber() >= people && !table.isReserved(datetime))
                .min(Comparator.comparingInt(MizTable::getSeatsNumber))
                .orElse(null);
    }
}
