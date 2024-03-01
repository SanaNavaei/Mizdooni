package application;

import exceptions.InvalidReviewRating;
import exceptions.ManagerCannotReview;
import exceptions.RestaurantNotFound;
import exceptions.UserNotFound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalTime;
import java.util.List;

public class ReviewServiceTest {
    private MizDooni mizdooni;
    private User manager;
    private User client;
    private Restaurant restaurant;

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
        mizdooni.users.add(client);
        mizdooni.users.add(manager);
        mizdooni.restaurants.add(restaurant);
    }

    @Test
    @DisplayName("Add review successfully")
    public void testAddReviewSuccessfully() throws UserNotFound, ManagerCannotReview, RestaurantNotFound, InvalidReviewRating {
        mizdooni.addReview("client", "restaurant", 1.2, 2.4, 3.6, 4.8, "Comment.");
        List<Review> reviews = restaurant.getReviews();
        Assertions.assertEquals(1, reviews.size());

        Review review = reviews.get(0);
        Assertions.assertEquals(1.2, review.getFoodRate());
        Assertions.assertEquals(2.4, review.getServiceRate());
        Assertions.assertEquals(3.6, review.getAmbianceRate());
        Assertions.assertEquals(4.8, review.getOverallRate());
        Assertions.assertEquals("Comment.", review.getComment());
    }

    @Test
    @DisplayName("Add review when user doesn't exist")
    public void testAddReviewNonexistentUser() {
        Assertions.assertThrows(UserNotFound.class, () -> mizdooni.addReview("nonexistent", "restaurant", 1.2, 2.4, 3.6, 4.8, "Comment."));
    }

    @Test
    @DisplayName("Add review when user is a manager")
    public void testAddReviewManager() {
        Assertions.assertThrows(ManagerCannotReview.class, () -> mizdooni.addReview("manager", "restaurant", 1.2, 2.4, 3.6, 4.8, "Comment."));
    }

    @Test
    @DisplayName("Add review when restaurant doesn't exist")
    public void testAddReviewNonexistentRestaurant() {
        Assertions.assertThrows(RestaurantNotFound.class, () -> mizdooni.addReview("client", "nonexistent", 1.2, 2.4, 3.6, 4.8, "Comment."));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 0.5, 1, 4.5, 5})
    @DisplayName("Add review with valid ratings")
    public void testAddReviewValidRatings(double rating) throws UserNotFound, ManagerCannotReview, RestaurantNotFound, InvalidReviewRating {
        mizdooni.addReview("client", "restaurant", rating, rating, rating, rating, "Comment.");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-100, -1.5, -1, -0.5, 5.5, 6, 6.5, 100})
    @DisplayName("Add review with invalid food rate")
    public void testAddReviewInvalidFoodRate(double foodRate) {
        InvalidReviewRating ex = Assertions.assertThrows(InvalidReviewRating.class, () -> mizdooni.addReview("client", "restaurant", foodRate, 2.4, 3.6, 4.8, "Comment."));
        Assertions.assertEquals("Food", ex.getParameter());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-100, -1.5, -1, -0.5, 5.5, 6, 6.5, 100})
    @DisplayName("Add review with invalid service rate")
    public void testAddReviewInvalidServiceRate(double serviceRate) {
        InvalidReviewRating ex = Assertions.assertThrows(InvalidReviewRating.class, () -> mizdooni.addReview("client", "restaurant", 1.2, serviceRate, 3.6, 4.8, "Comment."));
        Assertions.assertEquals("Service", ex.getParameter());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-100, -1.5, -1, -0.5, 5.5, 6, 6.5, 100})
    @DisplayName("Add review with invalid ambiance rate")
    public void testAddReviewInvalidAmbianceRate(double ambianceRate) {
        InvalidReviewRating ex = Assertions.assertThrows(InvalidReviewRating.class, () -> mizdooni.addReview("client", "restaurant", 1.2, 2.4, ambianceRate, 4.8, "Comment."));
        Assertions.assertEquals("Ambiance", ex.getParameter());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-100, -1.5, -1, -0.5, 5.5, 6, 6.5, 100})
    @DisplayName("Add review with invalid overall rate")
    public void testAddReviewInvalidOverallRate(double overallRate) {
        InvalidReviewRating ex = Assertions.assertThrows(InvalidReviewRating.class, () -> mizdooni.addReview("client", "restaurant", 1.2, 2.4, 3.6, overallRate, "Comment."));
        Assertions.assertEquals("Overall", ex.getParameter());
    }
}
