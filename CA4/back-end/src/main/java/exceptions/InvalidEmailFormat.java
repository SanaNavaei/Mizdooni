package exceptions;

public class InvalidEmailFormat extends Exception {
    public InvalidEmailFormat() {
        super("Invalid email format.");
    }
}
