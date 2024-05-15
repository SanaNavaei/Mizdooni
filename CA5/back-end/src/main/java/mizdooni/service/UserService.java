package mizdooni.service;

import mizdooni.database.Database;
import mizdooni.exceptions.DuplicatedUsernameEmail;
import mizdooni.exceptions.InvalidEmailFormat;
import mizdooni.exceptions.InvalidUsernameFormat;
import mizdooni.model.Address;
import mizdooni.model.user.Client;
import mizdooni.model.user.Manager;
import mizdooni.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private Database db;
    private User currentUser = null;

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean login(String username, String password) {
        User user = db.users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
        if (user != null && user.checkPassword(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void signup(String username, String password, String email, Address address,
                       User.Role role) throws InvalidEmailFormat, InvalidUsernameFormat, DuplicatedUsernameEmail {
        if (!ServiceUtils.validateUsername(username)) {
            throw new InvalidUsernameFormat();
        }
        if (!ServiceUtils.validateEmail(email)) {
            throw new InvalidEmailFormat();
        }
        if (ServiceUtils.userIsTaken(username, email, db.users)) {
            throw new DuplicatedUsernameEmail();
        }

        User user = null;
        if (role == User.Role.client) {
            user = new Client(username, password, email, address);
        } else if (role == User.Role.manager) {
            user = new Manager(username, password, email, address);
        }
        db.users.add(user);
    }

    public boolean logout() {
        if (currentUser != null) {
            currentUser = null;
            return true;
        }
        return false;
    }

    public boolean usernameExists(String username) {
        return db.users.stream().anyMatch(u -> u.getUsername().equals(username));
    }

    public boolean emailExists(String email) {
        return db.users.stream().anyMatch(u -> u.getEmail().equals(email));
    }
}
