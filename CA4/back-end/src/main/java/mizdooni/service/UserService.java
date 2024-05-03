package mizdooni.service;

import mizdooni.database.Database;
import mizdooni.exceptions.DuplicatedUsernameEmail;
import mizdooni.exceptions.InvalidEmailFormat;
import mizdooni.exceptions.InvalidUsernameFormat;
import mizdooni.model.Address;
import mizdooni.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static mizdooni.service.Utils.*;

@Service
public class UserService {
    @Autowired
    private Database db;
    private User currentUser = null;

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

    public boolean logout() {
        if (currentUser != null) {
            currentUser = null;
            return true;
        }
        return false;
    }

    public void signup(String username, String password, String email, Address address,
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
