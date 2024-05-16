package mizdooni.repository;

import mizdooni.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface RestaurantRepository extends JpaRepository<Restaurant, String>, JpaSpecificationExecutor<Restaurant> {
    Restaurant findById(int id);

    Restaurant findByName(String name);

    List<Restaurant> findByManagerId(int managerId);

    boolean existsByName(String name);

    @Query("SELECT DISTINCT type FROM Restaurant")
    Set<String> findAllTypes();

    @Query("SELECT a.country, a.city " +
            "FROM Restaurant r JOIN r.address a " +
            "GROUP BY a.country, a.city")
    List<List<String>> findCitiesByCountry();
}
