package mizdooni.repository;

import mizdooni.model.Rating;
import mizdooni.model.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByRestaurantId(int restaurantId, Pageable pageable);

    int countByRestaurantId(int restaurantId);

    Review findByUserIdAndRestaurantId(int userId, int restaurantId);

    @Transactional
    @Modifying
    @Query("UPDATE Review r "+
            "SET r.rating = :rating, r.comment = :comment, r.datetime = :datetime "+
            "WHERE r.id = :reviewId")
    void update(int reviewId, Rating rating, String comment, LocalDateTime datetime);
}
