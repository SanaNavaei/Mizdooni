package application;

import exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationServiceTest {
    private MizDooni mizdooni;
    private User client;
    private User manager;
    private Restaurant restaurant;
    private Table table;
    private LocalDateTime datetime;

    @BeforeEach
    public void setup() {
        mizdooni = new MizDooni();
        client = new User("client", "password", "client@email.com",
                new Address("Iran", "Sari", null), User.Role.client);
        manager = new User("manager", "password", "manager@email.com",
                new Address("Iran", "Karaj", null), User.Role.manager);
        restaurant = new Restaurant("restaurant", manager, "iranian",
                LocalTime.of(8, 0), LocalTime.of(23, 0), "description",
                new Address("Iran", "Tehran", "North Kargar"));
        table = new Table(1, "restaurant", 4);
        datetime = LocalDateTime.of(2030, 12, 12, 12, 0);
        restaurant.addTable(table);
        mizdooni.users.add(client);
        mizdooni.users.add(manager);
        mizdooni.restaurants.add(restaurant);
    }

    @Test
    @DisplayName("Add reservation successfully")
    public void testAddReservationSuccessfully() throws UserNotFound, ManagerReservationNotAllowed, InvalidWorkingTime, RestaurantNotFound, TableNotFound, DateTimeInThePast, ReservationNotInOpenTimes, TableAlreadyReserved {
        mizdooni.reserveTable("client", "restaurant", 1, datetime);
        Assertions.assertEquals(1, table.getReservations().size());
        Reservation reservation = table.getReservations().get(0);
        Assertions.assertEquals("client", reservation.getUser().getUsername());
        Assertions.assertEquals("restaurant", reservation.getRestaurant().getName());
        Assertions.assertEquals(1, reservation.getTable().getTableNumber());
        Assertions.assertEquals(datetime, reservation.getDateTime());
        Assertions.assertEquals(1, client.getReservations().size());
        Assertions.assertEquals(reservation, client.getReservations().get(0));
    }

    @Test
    @DisplayName("Add reservation when user doesn't exist")
    public void testAddReservationNonexistentUser() {
        Assertions.assertThrows(UserNotFound.class, () -> mizdooni.reserveTable("nonexistent", "restaurant", 1, datetime));
    }

    @Test
    @DisplayName("Add reservation when user is a manager")
    public void testAddReservationManager() {
        Assertions.assertThrows(ManagerReservationNotAllowed.class, () -> mizdooni.reserveTable("manager", "restaurant", 1, datetime));
    }

    @Test
    @DisplayName("Add reservation with invalid working time")
    public void testAddReservationInvalidWorkingTime() {
        LocalDateTime invalidDatetime = LocalDateTime.of(2030, 12, 12, 8, 20);
        Assertions.assertThrows(InvalidWorkingTime.class, () -> mizdooni.reserveTable("client", "restaurant", 1, invalidDatetime));
    }

    @Test
    @DisplayName("Add reservation in the past")
    public void testAddReservationInThePast() {
        LocalDateTime invalidDatetime = LocalDateTime.of(2020, 12, 12, 12, 0);
        Assertions.assertThrows(DateTimeInThePast.class, () -> mizdooni.reserveTable("client", "restaurant", 1, invalidDatetime));
    }

    @Test
    @DisplayName("Add reservation when restaurant doesn't exist")
    public void testAddReservationNonexistentRestaurant() {
        Assertions.assertThrows(RestaurantNotFound.class, () -> mizdooni.reserveTable("client", "nonexistent", 1, datetime));
    }

    @Test
    @DisplayName("Add reservation when table doesn't exist")
    public void testAddReservationNonexistentTable() {
        Assertions.assertThrows(TableNotFound.class, () -> mizdooni.reserveTable("client", "restaurant", 2, datetime));
    }

    @Test
    @DisplayName("Add reservation when datetime is not in open times")
    public void testAddReservationNotInOpenTimes() {
        LocalDateTime invalidDatetime = LocalDateTime.of(2030, 12, 12, 7, 0);
        Assertions.assertThrows(ReservationNotInOpenTimes.class, () -> mizdooni.reserveTable("client", "restaurant", 1, invalidDatetime));
    }

    @Test
    @DisplayName("Add reservation when table is already reserved")
    public void testAddReservationTableAlreadyReserved() throws UserNotFound, ManagerReservationNotAllowed, InvalidWorkingTime, RestaurantNotFound, TableNotFound, DateTimeInThePast, ReservationNotInOpenTimes, TableAlreadyReserved {
        mizdooni.reserveTable("client", "restaurant", 1, datetime);
        Assertions.assertThrows(TableAlreadyReserved.class, () -> mizdooni.reserveTable("client", "restaurant", 1, datetime));
    }
}
