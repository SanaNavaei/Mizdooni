package mizdooni.repository;

import mizdooni.model.Reservation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends ListCrudRepository<Reservation, Integer> {
    boolean existsByUserIdAndRestaurantIdAndCancelledFalseAndDatetimeBefore(int userId, int restaurantId, LocalDateTime datetime);

    List<Reservation> findByTableTableNumber(int tableNumber);
}
