package mizdooni.service;

import co.elastic.apm.api.CaptureSpan;
import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Span;
import mizdooni.exceptions.*;
import mizdooni.model.Rating;
import mizdooni.model.Restaurant;
import mizdooni.model.Review;
import mizdooni.model.user.User;
import mizdooni.repository.ReservationRepository;
import mizdooni.repository.RestaurantRepository;
import mizdooni.repository.ReviewRepository;
import mizdooni.response.PagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserService userService;

    @CaptureSpan
    public PagedList<Review> getReviews(int restaurantId, int page) throws RestaurantNotFound {
        Span getReviewsSpan = ElasticApm.currentSpan();
        Span findRestaurantSpan = getReviewsSpan.startSpan().setName("find restaurant");
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        findRestaurantSpan.end();

        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        PageRequest pageRequest = PageRequest.of(page - 1, ServiceUtils.REVIEW_PAGE_SIZE);
        Span getReviewsFromDbSpan = getReviewsSpan.startSpan().setName("get reviews");
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId, pageRequest);
        getReviewsFromDbSpan.end();
        int totalReviews = reviewRepository.countByRestaurantId(restaurantId);
        return new PagedList<>(reviews, totalReviews, pageRequest);
    }

    @CaptureSpan
    public void addReview(int userId, int restaurantId, Rating rating, String comment)
            throws UserNotFound, ManagerCannotReview, RestaurantNotFound, InvalidReviewRating, UserHasNotReserved {
        Span addReviewSpan = ElasticApm.currentSpan();
        Span getUserSpan = addReviewSpan.startSpan().setName("get user");
        User user = userService.getUser(userId);
        getUserSpan.end();

        if (user == null) {
            throw new UserNotFound();
        }
        if (user.getRole() == User.Role.manager) {
            throw new ManagerCannotReview();
        }

        Span findRestaurantSpan = addReviewSpan.startSpan().setName("find restaurant");
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        findRestaurantSpan.end();

        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        if (!checkReserved(user, restaurant)) {
            throw new UserHasNotReserved();
        }

        if (rating.food < 0 || rating.food > 5) {
            throw new InvalidReviewRating("Food");
        }
        if (rating.service < 0 || rating.service > 5) {
            throw new InvalidReviewRating("Service");
        }
        if (rating.ambiance < 0 || rating.ambiance > 5) {
            throw new InvalidReviewRating("Ambiance");
        }
        if (rating.overall < 0 || rating.overall > 5) {
            throw new InvalidReviewRating("Overall");
        }

        Span saveReviewSpan = addReviewSpan.startSpan().setName("add review");
        Review previous = reviewRepository.findByUserIdAndRestaurantId(user.getId(), restaurantId);
        if (previous == null) {
            Review review = new Review(user, restaurant, rating, comment, LocalDateTime.now());
            reviewRepository.save(review);
        } else {
            reviewRepository.update(previous.getId(), rating, comment, LocalDateTime.now());
        }
        saveReviewSpan.end();
    }

    private boolean checkReserved(User user, Restaurant restaurant) {
        return reservationRepository.existsByUserIdAndRestaurantIdAndCancelledFalseAndDatetimeBefore(
                user.getId(), restaurant.getId(), LocalDateTime.now());
    }
}
