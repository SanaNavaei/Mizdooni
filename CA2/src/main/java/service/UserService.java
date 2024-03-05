package service;

import database.Database;
import exceptions.DuplicatedUsernameEmail;
import exceptions.InvalidEmailFormat;
import exceptions.InvalidUsernameFormat;
import model.Address;
import model.User;

import static service.Utils.*;

public class UserService {
    private Database db;
    private User currentUser;

    private static final UserService instance = new UserService();

    private UserService() {
        db = Database.getInstance();
        currentUser = null;
    }

    public static UserService getInstance() {
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean login(String username, String password) {
        User user = findUser(username, db.users);
        if (user != null && user.checkPassword(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public void addUser(String username, String password, String email, Address address,
                        User.Role role) throws InvalidEmailFormat, InvalidUsernameFormat, DuplicatedUsernameEmail {
        if (!validateUsername(username)) {
            throw new InvalidUsernameFormat();
        }
        if (!validateEmail(email)) {
            throw new InvalidEmailFormat();
        }
        if (!userIsTaken(username, email, db.users)) {
            throw new DuplicatedUsernameEmail();
        }

        User user = new User(username, password, email, address, role);
        db.users.add(user);
    }
}
