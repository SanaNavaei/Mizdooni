package mizdooni.service;

import mizdooni.exceptions.DuplicatedUsernameEmail;
import mizdooni.exceptions.InvalidEmailFormat;
import mizdooni.exceptions.InvalidUsernameFormat;
import mizdooni.model.Address;
import mizdooni.model.user.Client;
import mizdooni.model.user.Manager;
import mizdooni.model.user.User;
import mizdooni.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private User currentUser = null;

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.checkPassword(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void signup(String username, String password, String email, Address address, User.Role role)
            throws InvalidEmailFormat, InvalidUsernameFormat, DuplicatedUsernameEmail {
        if (!ServiceUtils.validateUsername(username)) {
            throw new InvalidUsernameFormat();
        }
        if (!ServiceUtils.validateEmail(email)) {
            throw new InvalidEmailFormat();
        }
        if (userRepository.existsByUsernameOrEmail(username, email)) {
            throw new DuplicatedUsernameEmail();
        }

        User user = null;
        int id = (int) userRepository.count();
        password = ServiceUtils.hashPassword(password);
        if (role == User.Role.client) {
            user = new Client(id, username, password, email, address);
        } else if (role == User.Role.manager) {
            user = new Manager(id, username, password, email, address);
        }
        userRepository.save(user);
    }

    public boolean logout() {
        if (currentUser != null) {
            currentUser = null;
            return true;
        }
        return false;
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
