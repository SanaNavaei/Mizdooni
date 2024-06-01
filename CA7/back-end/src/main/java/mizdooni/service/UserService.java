package mizdooni.service;

import co.elastic.apm.api.CaptureSpan;
import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Span;
import mizdooni.exceptions.DuplicatedUsernameEmail;
import mizdooni.exceptions.InvalidEmailFormat;
import mizdooni.exceptions.InvalidUsernameFormat;
import mizdooni.model.Address;
import mizdooni.model.user.Client;
import mizdooni.model.user.Manager;
import mizdooni.model.user.User;
import mizdooni.repository.user.UserRepository;
import mizdooni.utils.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private GoogleAuthService googleAuthService;
    @Autowired
    private UserRepository userRepository;

    public record UserTokenPair(User user, String token) {

    }

    public User getUser(int userId) {
        return userRepository.findById(userId);
    }

    public UserTokenPair login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.checkPassword(password)) {
            return new UserTokenPair(user, jwtService.createToken(user));
        }
        return null;
    }

    public UserTokenPair loginWithGoogle(String code) {
        String accessToken = googleAuthService.getAccessToken(code);
        if (accessToken == null) {
            return null;
        }
        Map<String, String> userInfo = googleAuthService.getUserInfo(accessToken);
        String name = userInfo.get("name");
        String email = userInfo.get("email");

        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmail(email);
            return new UserTokenPair(user, jwtService.createToken(user));
        }

        if (userRepository.existsByUsername(name)) {
            return null;
        }

        int id = (int) userRepository.count();
        User user = new Client(id, name, null, email, new Address("", "", null));
        userRepository.save(user);
        return new UserTokenPair(user, jwtService.createToken(user));
    }

    @CaptureSpan
    public void signup(String username, String password, String email, Address address, User.Role role)
            throws InvalidEmailFormat, InvalidUsernameFormat, DuplicatedUsernameEmail {
        Span signupSpan = ElasticApm.currentSpan();

        if (!ServiceUtils.validateUsername(username)) {
            throw new InvalidUsernameFormat();
        }
        if (!ServiceUtils.validateEmail(email)) {
            throw new InvalidEmailFormat();
        }
        if (userRepository.existsByUsernameOrEmail(username, email)) {
            throw new DuplicatedUsernameEmail();
        }

        String passwordHash = Crypto.hash(password);
        User user = null;
        int id = (int) userRepository.count();

        Span newUserSpan = signupSpan.startSpan().setName("create user");
        if (role == User.Role.manager) {
            user = new Manager(id, username, passwordHash, email, address);
        } else {
            user = new Client(id, username, passwordHash, email, address);
        }
        userRepository.save(user);
        newUserSpan.end();
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
