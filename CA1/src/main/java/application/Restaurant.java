package application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

    public String getName() { return name; }

    public String getType() { return type; }

    public LocalTime getStartTime() { return startTime; }

    public LocalTime getEndTime() { return endTime; }

    public String getDescription() { return description; }

    public Address getAddress() { return address; }

    public ArrayList<Table> getTables() { return tables; }

    public Object getManager() { return manager; }

    public void addTable(Table table) {
        tables.add(table);
    }

    public JsonNode createRestaurantJson() {
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
