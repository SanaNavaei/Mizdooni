package exceptions;

public class ReservationNotFound extends Exception {
    public ReservationNotFound() {
        super("Reservation not found.");
    }
}
