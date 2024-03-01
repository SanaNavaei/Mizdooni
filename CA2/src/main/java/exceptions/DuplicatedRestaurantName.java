package exceptions;

public class DuplicatedRestaurantName extends Exception {
    public DuplicatedRestaurantName() {
        super("Restaurant name is already taken.");
    }
}
