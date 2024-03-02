package application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Rating {
    public double food;
    public double service;
    public double ambiance;
    public double overall;

    public JsonNode toJson() {
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("food", food);
        node.put("service", service);
        node.put("ambiance", ambiance);
        node.put("overall", overall);

        return node;
    }
}
