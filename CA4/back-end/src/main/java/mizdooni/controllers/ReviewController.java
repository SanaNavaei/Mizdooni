package mizdooni.controllers;

import mizdooni.model.Rating;
import mizdooni.model.Restaurant;
import mizdooni.model.Review;
import mizdooni.response.PagedList;
import mizdooni.response.Response;
import mizdooni.response.ResponseException;
import mizdooni.service.RestaurantService;
import mizdooni.service.ReviewService;
import mizdooni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static mizdooni.controllers.ControllerUtils.*;

@RestController
class ReviewController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;

    @GetMapping("/reviews/{restaurant_id}")
    public Response getReviews(@PathVariable("restaurant_id") int id, @RequestParam int page) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        if (restaurant == null) {
            throw new ResponseException(HttpStatus.NOT_FOUND, RESTAURANT_NOT_FOUND);
        }

        try {
            PagedList<Review> reviews = reviewService.getReviews(restaurant.getName(), page);
            String message = "reviews for restaurant (" + id + "): " + restaurant.getName();
            return Response.ok(message, reviews);
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ex);
        }
    }

    @PostMapping("/reviews/{restaurant_id}")
    public Response addReview(@PathVariable("restaurant_id") int id, @RequestBody Map<String, Object> params) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        if (restaurant == null) {
            throw new ResponseException(HttpStatus.NOT_FOUND, RESTAURANT_NOT_FOUND);
        }

        if (!ControllerUtils.containsKeys(params, "comment", "rating")) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, PARAMS_MISSING);
        }

        String comment;
        Rating rating = new Rating();

        try {
            comment = (String) params.get("comment");
            Map<String, Number> ratingMap = (Map<String, Number>) params.get("rating");
            rating.food = ratingMap.get("food").doubleValue();
            rating.service = ratingMap.get("service").doubleValue();
            rating.ambiance = ratingMap.get("ambiance").doubleValue();
            rating.overall = ratingMap.get("overall").doubleValue();
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, PARAMS_BAD_TYPE);
        }

        try {
            reviewService.addReview(userService.getCurrentUser().getUsername(), restaurant.getName(), rating, comment);
            return Response.ok("review added successfully");
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ex);
        }
    }
}
