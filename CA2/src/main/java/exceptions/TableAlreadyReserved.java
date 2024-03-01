package exceptions;

public class TableAlreadyReserved extends Exception {
    public TableAlreadyReserved() {
        super("Table is already reserved.");
    }
}
