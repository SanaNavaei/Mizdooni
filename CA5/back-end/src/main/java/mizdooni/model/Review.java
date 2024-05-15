package mizdooni.model;

import jakarta.persistence.*;
import mizdooni.model.user.User;

import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @Embedded
    private Rating rating;

    @ManyToOne
    @JoinColumn
    private Restaurant restaurant;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private LocalDateTime datetime;

    public Review() {

    }

    public Review(User user, Rating rating, String comment, LocalDateTime datetime) {
        this.user = user;
        this.rating = rating;
        this.comment = comment;
        this.datetime = datetime;
    }

    public Rating getRating() {
        return rating;
    }

    public int getStarCount() {
        return rating.getStarCount();
    }

    public User getUser() {
        return user;
    }
}
