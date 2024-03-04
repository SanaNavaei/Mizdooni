package model;

import java.time.LocalDateTime;

public class Review {
    private Rating rating;
    private String comment;
    private LocalDateTime datetime;
    private User user;

    public Review(User user, double foodRate, double serviceRate, double ambianceRate, double overallRate, String comment, LocalDateTime datetime) {
        this.user = user;
        this.rating = new Rating();
        this.rating.food = foodRate;
        this.rating.service = serviceRate;
        this.rating.ambiance = ambianceRate;
        this.rating.overall = overallRate;
        this.comment = comment;
        this.datetime = datetime;
    }

    public Rating getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public User getUser() {
        return user;
    }
}
