package application;

import java.util.ArrayList;

public class Utils {
    static public boolean validateUsername(String username) {
        String usernameFormat = "^[a-zA-Z0-9]+$";
        return username.matches(usernameFormat);
    }

    static public boolean validateEmail(String email) {
        String emailFormat = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.com$";
        return email.matches(emailFormat);
    }

    static public boolean userIsTaken(String username, String email, ArrayList<User> users) {
        for (User u : users) {
            if (u.getUsername().equals(username) || u.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }
}
