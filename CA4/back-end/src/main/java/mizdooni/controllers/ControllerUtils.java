package mizdooni.controllers;

import mizdooni.model.Restaurant;
import mizdooni.response.ResponseException;
import mizdooni.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ControllerUtils {
    @Autowired
    private static RestaurantService restaurantService;

    static final String PARAMS_MISSING = "parameters missing";
    static final String PARAMS_BAD_TYPE = "bad parameter type";

    static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    static boolean containsKeys(Map<String, ?> params, String... keys) {
        for (String key : keys) {
            if (!params.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    static boolean doExist(String... values) {
        for (String value : values) {
            if (value == null || value.isBlank()) {
                return false;
            }
        }
        return true;
    }

    static Restaurant checkRestaurant(int restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
        if (restaurant == null) {
            throw new ResponseException(HttpStatus.NOT_FOUND, "restaurant not found");
        }
        return restaurant;
    }
}
