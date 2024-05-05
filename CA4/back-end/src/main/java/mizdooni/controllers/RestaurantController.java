package mizdooni.controllers;

import mizdooni.model.Address;
import mizdooni.model.Restaurant;
import mizdooni.model.RestaurantSearchFilter;
import mizdooni.response.PagedList;
import mizdooni.response.Response;
import mizdooni.response.ResponseException;
import mizdooni.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static mizdooni.controllers.ControllerUtils.*;

@RestController
class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants/{restaurantId}")
    public Response getRestaurant(@PathVariable int restaurantId) {
        Restaurant restaurant = ControllerUtils.checkRestaurant(restaurantId);
        return Response.ok("restaurant found", restaurant);
    }

    @GetMapping("/restaurants")
    public Response getRestaurants(@RequestParam int page, RestaurantSearchFilter filter) {
        try {
            PagedList<Restaurant> restaurants = restaurantService.getRestaurants(page, filter);
            return Response.ok("restaurants listed", restaurants);
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ex);
        }
    }

    @GetMapping("/restaurants/manager/{managerId}")
    public Response getManagerRestaurants(@PathVariable int managerId) {
        try {
            List<Restaurant> restaurants = restaurantService.getManagerRestaurants(managerId);
            return Response.ok("manager restaurants listed", restaurants);
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ex);
        }
    }

    @PostMapping("/restaurants")
    public Response addRestaurant(@RequestBody Map<String, Object> params) {
        if (!ControllerUtils.containsKeys(params, "name", "type", "startTime", "endTime", "description", "address")) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, PARAMS_MISSING);
        }

        String name, type, description, image;
        LocalTime startTime, endTime;
        Address address;

        try {
            name = (String) params.get("name");
            type = (String) params.get("type");
            description = (String) params.get("description");
            if (params.get("image") == null) {
                image = "/restaurant-placeholder.jpg";
            }
            else {
                image = (String) params.get("image");
            }
            startTime = LocalTime.parse((String) params.get("startTime"), ControllerUtils.TIME_FORMATTER);
            endTime = LocalTime.parse((String) params.get("endTime"), ControllerUtils.TIME_FORMATTER);
            Map<String, String> addr = (Map<String, String>) params.get("address");
            address = new Address(addr.get("country"), addr.get("city"), addr.get("street"));
            if (!ControllerUtils.doExist(name, type, description, image,
                    address.getCountry(), address.getCity(), address.getStreet())) {
                throw new ResponseException(HttpStatus.BAD_REQUEST, PARAMS_MISSING);
            }
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, PARAMS_BAD_TYPE);
        }

        try {
            restaurantService.addRestaurant(name, type, startTime, endTime, description, address, image);
            return Response.ok("restaurant added");
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ex);
        }
    }

    @GetMapping("/validate/restaurant-name")
    public Response validateRestaurantName(@RequestParam("data") String name) {
        if (restaurantService.restaurantExists(name)) {
            throw new ResponseException(HttpStatus.CONFLICT, "restaurant name is taken");
        }
        return Response.ok("restaurant name is available");
    }
}
