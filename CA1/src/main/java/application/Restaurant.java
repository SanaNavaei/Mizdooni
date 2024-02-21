package application;

import java.time.LocalTime;

public class Restaurant {
    private String name;
    private User manager;
    private String type;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private Address address;

    public Restaurant(String name, User manager, String type, LocalTime startTime, LocalTime endTime,
                      String description, Address address) {
        this.name = name;
        this.manager = manager;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.address = address;
    }
}
