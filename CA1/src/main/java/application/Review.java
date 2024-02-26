package application;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Review {
    private double foodRate;
    private double serviceRate;
    private double ambianceRate;
    private double overallRate;
    private String comment;
    private LocalDateTime datetime;

    public Review(double foodRate, double serviceRate, double ambianceRate, double overallRate, String comment, LocalDateTime datetime) {
        this.foodRate = foodRate;
        this.serviceRate = serviceRate;
        this.ambianceRate = ambianceRate;
        this.overallRate = overallRate;
        this.comment = comment;
        this.datetime = datetime;
    }
}
