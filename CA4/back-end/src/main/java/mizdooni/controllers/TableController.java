package mizdooni.controllers;

import mizdooni.model.Restaurant;
import mizdooni.model.Table;
import mizdooni.response.Response;
import mizdooni.response.ResponseException;
import mizdooni.service.RestaurantService;
import mizdooni.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static mizdooni.controllers.ControllerUtils.*;

@RestController
class TableController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private TableService tableService;

    @GetMapping("/tables/{restaurantId}")
    public Response getTables(@PathVariable int restaurantId) {
        List<Table> tables = tableService.getTables(restaurantId);
        if (tables == null) {
            throw new ResponseException(HttpStatus.NOT_FOUND, RESTAURANT_NOT_FOUND);
        }
        return Response.ok("tables listed", tables);
    }

    @PostMapping("/tables/{restaurantId}")
    public Response addTable(@PathVariable int restaurantId, @RequestBody Map<String, String> params) {
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
        if (restaurant == null) {
            throw new ResponseException(HttpStatus.NOT_FOUND, RESTAURANT_NOT_FOUND);
        }
        if (!ControllerUtils.containsKeys(params, "seatsNumber")) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, PARAMS_MISSING);
        }

        int seatsNumber;
        try {
            seatsNumber = Integer.parseInt(params.get("seatsNumber"));
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, PARAMS_BAD_TYPE);
        }

        try {
            tableService.addTable(restaurant.getId(), seatsNumber);
            return Response.ok("table added");
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ex);
        }
    }
}
