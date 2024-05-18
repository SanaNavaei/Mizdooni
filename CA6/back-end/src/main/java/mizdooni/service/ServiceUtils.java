package mizdooni.service;

import java.time.LocalTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ServiceUtils {
    static final int REVIEW_PAGE_SIZE = 5;
    static final int RESTAURANT_PAGE_SIZE = 12;

    public static boolean validateUsername(String username) {
        String usernameFormat = "^\\w+$";
        return username.matches(usernameFormat);
    }

    public static boolean validateEmail(String email) {
        String emailFormat = "^\\w+@\\w+\\.\\w+$";
        return email.matches(emailFormat);
    }

    static boolean validateWorkingTime(LocalTime time) {
        return time.getMinute() == 0;
    }

    public static String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
