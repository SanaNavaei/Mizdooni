package mizdooni.repository;

import mizdooni.model.MizTable;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface MizTableRepository extends ListCrudRepository<MizTable, Integer> {
    List<MizTable> findByRestaurantId(int restaurantId);

    MizTable findByRestaurantIdAndTableNumber(int restaurantId, int tableNumber);

    List<MizTable> findByRestaurantIdAndSeatsNumberGreaterThanEqual(int restaurantId, int people);
}
