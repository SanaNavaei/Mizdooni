package exceptions;

public class ManagerNotFound extends Exception {
    public ManagerNotFound() {
        super("Manager not found.");
    }
}
