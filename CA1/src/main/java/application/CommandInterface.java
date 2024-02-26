package application;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

enum Command {
    addUser,
    addRestaurant,
    addTable,
    reserveTable,
    cancelReservation,
    showReservationHistory,
    searchRestaurantsByName,
    searchRestaurantsByType,
    showAvailableTables,
    addReview;

    static private Map<String, Command> map = Map.ofEntries(
            Map.entry("addUser", addUser),
            Map.entry("addRestaurant", addRestaurant),
            Map.entry("addTable", addTable),
            Map.entry("reserveTable", reserveTable),
            Map.entry("cancelReservation", cancelReservation),
            Map.entry("showReservationHistory", showReservationHistory),
            Map.entry("searchRestaurantsByName", searchRestaurantsByName),
            Map.entry("searchRestaurantsByType", searchRestaurantsByType),
            Map.entry("showAvailableTables", showAvailableTables),
            Map.entry("addReview", addReview)
    );

    static public Command fromString(String str) {
        return map.get(str);
    }
}

public class CommandInterface {
    private Controller controller;
    private Scanner input;
    private PrintWriter output;

    public CommandInterface(Controller controller, InputStream is, OutputStream os) {
        this.controller = controller;
        this.input = new Scanner(is);
        this.output = new PrintWriter(os, true);
    }

    public void run() {
        while (input.hasNextLine()) {
            String command = input.next();
            String data = input.nextLine().trim();
            Command cmd = Command.fromString(command);
            if (cmd == null) {
                output.println("Invalid command!");
                continue;
            }
            if (data.isEmpty()) {
                output.println("Data not provided!");
                continue;
            }
            JsonNode result = execute(cmd, data);
            output.println(result);
        }
        input.close();
    }

    private JsonNode execute(Command command, String data) {
        return switch (command) {
            case addUser -> controller.addUser(data);
            case addRestaurant -> controller.addRestaurant(data);
            case addTable -> controller.addTable(data);
            case reserveTable -> controller.reserveTable(data);
            case cancelReservation -> controller.cancelReservation(data);
            case showReservationHistory -> controller.showReservationHistory(data);
            case searchRestaurantsByName -> controller.searchRestaurantsByName(data);
            case searchRestaurantsByType -> controller.searchRestaurantsByType(data);
            case showAvailableTables -> controller.showAvailableTables(data);
            case addReview -> controller.addReview(data);
        };
    }
}
