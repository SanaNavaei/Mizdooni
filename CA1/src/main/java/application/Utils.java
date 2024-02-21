package application;

public class Utils {
    static public boolean validateUsername(String username) {
        String usernameFormat = "^[a-zA-Z0-9]+$";
        return username.matches(usernameFormat);
    }

    static public boolean validateEmail(String email) {
        String emailFormat = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.com$";
        return email.matches(emailFormat);
    }
}
