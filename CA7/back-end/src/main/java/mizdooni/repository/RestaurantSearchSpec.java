package mizdooni.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import mizdooni.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RestaurantSearchSpec implements Specification<Restaurant> {
    private String name;
    private String type;
    private String location;

    public RestaurantSearchSpec(String name, String type, String location) {
        this.name = name;
        this.type = type;
        this.location = location;
    }

    @Override
    public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (name != null) {
            predicates.add(builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (type != null) {
            predicates.add(builder.equal(root.get("type"), type));
        }
        if (location != null) {
            predicates.add(builder.equal(root.get("address").get("city"), location));
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
