package mizdooni.repository.user;

import mizdooni.model.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;

interface AbstractUserRepository<T extends User> extends ListCrudRepository<T, String> {
    T findByUsername(String username);

    T findById(int userId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsernameOrEmail(String username, String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.reservationCounter = u.reservationCounter + 1 " +
            "WHERE u.id = :userId")
    void updateReservationCounter(int userId);
}
