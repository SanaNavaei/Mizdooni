package exceptions;

public class InvalidSeatsNumber extends Exception {
    public InvalidSeatsNumber() {
        super("Seats number must be positive integer.");
    }
}
