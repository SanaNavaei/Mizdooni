package exceptions;

public class InvalidDateTime extends Exception {
    public InvalidDateTime() {
        super("Date time is before current time.");
    }
}
