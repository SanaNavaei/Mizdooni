package mizdooni.service;

import co.elastic.apm.api.CaptureSpan;
import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Span;
import mizdooni.exceptions.InvalidManagerRestaurant;
import mizdooni.exceptions.RestaurantNotFound;
import mizdooni.exceptions.UserNotManager;
import mizdooni.model.MizTable;
import mizdooni.model.Restaurant;
import mizdooni.model.user.User;
import mizdooni.repository.MizTableRepository;
import mizdooni.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {
    @Autowired
    private MizTableRepository mizTableRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private UserService userService;

    public List<MizTable> getTables(int restaurantId) throws RestaurantNotFound {
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        return mizTableRepository.findByRestaurantId(restaurantId);
    }

    @CaptureSpan
    public void addTable(int userId, int restaurantId, int seatsNumber)
            throws RestaurantNotFound, UserNotManager, InvalidManagerRestaurant {
        Span addTableSpan = ElasticApm.currentSpan();
        Span getUserSpan = addTableSpan.startSpan().setName("get user");
        User manager = userService.getUser(userId);
        getUserSpan.end();

        Span findRestaurantSpan = addTableSpan.startSpan().setName("find restaurant");
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        findRestaurantSpan.end();

        if (restaurant == null) {
            throw new RestaurantNotFound();
        }
        if (manager == null || manager.getRole() != User.Role.manager) {
            throw new UserNotManager();
        }
        if (restaurant.getManager().getId() != manager.getId()) {
            throw new InvalidManagerRestaurant();
        }

        Span newTableSpan = addTableSpan.startSpan().setName("add table");
        MizTable table = new MizTable(0, restaurant, seatsNumber);
        mizTableRepository.save(table);
        newTableSpan.end();
    }
}
