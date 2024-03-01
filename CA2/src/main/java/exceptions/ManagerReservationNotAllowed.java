package exceptions;

public class ManagerReservationNotAllowed extends Exception {
    public ManagerReservationNotAllowed() {
        super("Manager cannot reserve tables.");
    }
}
