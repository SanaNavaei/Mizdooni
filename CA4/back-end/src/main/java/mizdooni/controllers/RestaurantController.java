package mizdooni.controllers;

import mizdooni.model.Address;
import mizdooni.model.Restaurant;
import mizdooni.response.PagedList;
import mizdooni.response.Response;
import mizdooni.response.ResponseException;
import mizdooni.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Map;

import static mizdooni.controllers.ControllerUtils.*;

@RestController
class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants/{id}")
    public Response getRestaurantById(@PathVariable int id) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        if (restaurant == null) {
            throw new ResponseException(HttpStatus.NOT_FOUND, RESTAURANT_NOT_FOUND);
        }
        return Response.ok("restaurant found", restaurant);
    }

    @GetMapping("/restaurants")
    public Response getRestaurants(@RequestParam int page) {
        try {
            PagedList<Restaurant> restaurants = restaurantService.getRestaurants(page);
            return Response.ok("restaurants listed", restaurants);
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ex);
        }
    }

    @PostMapping("/restaurants")
    public Response addRestaurant(@RequestBody Map<String, Object> params) {
        if (!ControllerUtils.containsKeys(params, "name", "managerUsername", "type", "startTime", "endTime", "description", "address", "image")) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, PARAMS_MISSING);
        }

        String name, managerUsername, type, description, image;
        LocalTime startTime, endTime;
        Address address;

        try {
            name = (String) params.get("name");
            managerUsername = (String) params.get("managerUsername");
            type = (String) params.get("type");
            description = (String) params.get("description");
            image = (String) params.get("image");
            startTime = LocalTime.parse((String) params.get("startTime"), ControllerUtils.TIME_FORMATTER);
            endTime = LocalTime.parse((String) params.get("endTime"), ControllerUtils.TIME_FORMATTER);
            Map<String, String> addr = (Map<String, String>) params.get("address");
            address = new Address(addr.get("country"), addr.get("city"), addr.get("street"));
            if (!ControllerUtils.doExist(name, managerUsername, type, description, image,
                    address.getCountry(), address.getCity(), address.getStreet())) {
                throw new ResponseException(HttpStatus.BAD_REQUEST, PARAMS_MISSING);
            }
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, PARAMS_BAD_TYPE);
        }

        try {
            restaurantService.addRestaurant(name, managerUsername, type, startTime, endTime, description, address, image);
            return Response.ok("restaurant added");
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ex);
        }
    }
}
