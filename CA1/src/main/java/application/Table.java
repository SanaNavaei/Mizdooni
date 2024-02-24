package application;

public class Table {
    private int tableNumber;
    private String restaurantName;
    private User manager;
    private int seatsNumber;

    public Table(int tableNumber, String restaurantName, User manager, int seatsNumber) {
        this.tableNumber = tableNumber;
        this.restaurantName = restaurantName;
        this.manager = manager;
        this.seatsNumber = seatsNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }
}
