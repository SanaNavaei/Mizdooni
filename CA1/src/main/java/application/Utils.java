package application;

public class Utils {
    static public boolean validateUsername(String username) {
        String usernameFormat = "^[a-zA-Z0-9]+$";
        return username.matches(usernameFormat);
    }

    static public boolean validateEmail(String email) {
        return true;
    }
}
