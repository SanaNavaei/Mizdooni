package application;

import java.time.LocalTime;
import java.util.ArrayList;

public class Restaurant {
    private String name;
    private User manager;
    private String type;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private Address address;
    private ArrayList<Table> tables = new ArrayList<>();

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

    public Object getName() { return name; }

    public ArrayList<Table> getTables() { return tables; }

    public void addTable(Table table) {
        tables.add(table);
    }
}
