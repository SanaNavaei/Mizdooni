package mizdooni.model;

import jakarta.persistence.*;
import mizdooni.model.user.User;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    private String type;
    private LocalTime startTime;
    private LocalTime endTime;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne
    private Address address;

    private String imageLink;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Table> tables = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public Restaurant() {

    }

    public Restaurant(String name, User manager, String type, LocalTime startTime, LocalTime endTime,
                      String description, Address address, String imageLink) {
        this.id = 0;
        this.name = name;
        this.manager = manager;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.address = address;
        this.imageLink = imageLink;
    }

    public Table getTable(int tableNumber) {
        return tables.stream().filter(t -> t.getTableNumber() == tableNumber).findFirst().orElse(null);
    }

    public void addTable(Table table) {
        table.setTableNumber(tables.size() + 1);
        tables.add(table);
    }

    public void addReview(Review review) {
        for (Review r : reviews) {
            if (r.getUser().equals(review.getUser())) {
                reviews.remove(r);
                break;
            }
        }
        reviews.add(review);
    }

    public Rating getAverageRating() {
        Rating average = new Rating();

        for (Review r : reviews) {
            average.food += r.getRating().food;
            average.service += r.getRating().service;
            average.ambiance += r.getRating().ambiance;
            average.overall += r.getRating().overall;
        }

        if (!reviews.isEmpty()) {
            average.food /= reviews.size();
            average.service /= reviews.size();
            average.ambiance /= reviews.size();
            average.overall /= reviews.size();
        }

        return average;
    }

    public int getStarCount() {
        return getAverageRating().getStarCount();
    }

    public int getMaxSeatsNumber() {
        return tables.stream().map(Table::getSeatsNumber).max(Integer::compareTo).orElse(0);
    }

    public List<Table> getTables() {
        return tables;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getManager() {
        return manager;
    }

    public String getType() {
        return type;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Address getAddress() {
        return address;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
