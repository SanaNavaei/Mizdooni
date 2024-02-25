package application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private User manager;
    private String type;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private Address address;
    private List<Table> tables;

    public Restaurant(String name, User manager, String type, LocalTime startTime, LocalTime endTime,
                      String description, Address address) {
        this.name = name;
        this.manager = manager;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.address = address;
        this.tables = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public User getManager() {
        return manager;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Table getTable(int tableNumber) {
        for (Table t : tables) {
            if (t.getTableNumber() == tableNumber) {
                return t;
            }
        }
        return null;
    }

    public void addTable(Table table) {
        tables.add(table);
    }

    public JsonNode toJson() {
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("name", name);
        node.put("type", type);
        node.put("startTime", startTime.toString());
        node.put("endTime", endTime.toString());
        node.put("description", description);

        ObjectNode address_ = new ObjectMapper().createObjectNode();
        address_.put("country", address.getCountry());
        address_.put("city", address.getCity());
        address_.put("street", address.getStreet());
        node.set("address", address_);

        return node;
    }
}
