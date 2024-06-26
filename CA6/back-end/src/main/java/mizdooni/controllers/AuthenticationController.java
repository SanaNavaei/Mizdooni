package mizdooni.controllers;

import mizdooni.model.Address;
import mizdooni.model.user.User;
import mizdooni.response.Response;
import mizdooni.response.ResponseException;
import mizdooni.service.JwtService;
import mizdooni.service.ServiceUtils;
import mizdooni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static mizdooni.controllers.ControllerUtils.PARAMS_BAD_TYPE;
import static mizdooni.controllers.ControllerUtils.PARAMS_MISSING;

@RestController
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/user")
    public Response user(@RequestHeader(value = "Authorization", required = false) String token) {
        User user = null;
        if (token != null && token.startsWith("Bearer ") && jwtService.validateToken(token.substring(7))) {
            int userId = jwtService.getUserId(token.substring(7));
            user = userService.getUser(userId);
        }
        if (user == null) {
            throw new ResponseException(HttpStatus.UNAUTHORIZED, "user not logged in");
        }
        return Response.ok("user information", user);
    }

    @PostMapping("/login")
    public Response login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        if (!ControllerUtils.doExist(username, password)) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, PARAMS_MISSING);
        }

        UserService.UserTokenPair result = userService.login(username, password);
        if (result == null) {
            throw new ResponseException(HttpStatus.UNAUTHORIZED, "invalid username or password");
        }
        return Response.ok("login successful", result.user()).token(result.token());
    }

    @PostMapping("/login/google")
    public Response loginWithGoogle(@RequestBody Map<String, String> params) {
        String code = params.get("code");

        if (!ControllerUtils.doExist(code)) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, PARAMS_MISSING);
        }

        UserService.UserTokenPair result = userService.loginWithGoogle(code);
        if (result == null) {
            throw new ResponseException(HttpStatus.UNAUTHORIZED, "oauth validation failed");
        }
        return Response.ok("login successful", result.user()).token(result.token());
    }

    @PostMapping("/signup")
    public Response signup(@RequestBody Map<String, Object> params) {
        if (!ControllerUtils.containsKeys(params, "username", "password", "email", "address", "role")) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, PARAMS_MISSING);
        }

        String username, password, email;
        Address address;
        User.Role role;

        try {
            username = (String) params.get("username");
            password = (String) params.get("password");
            email = (String) params.get("email");
            role = User.Role.valueOf((String) params.get("role"));
            Map<String, String> addr = (Map<String, String>) params.get("address");
            address = new Address(addr.get("country"), addr.get("city"), null);
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, PARAMS_BAD_TYPE);
        }

        if (!ControllerUtils.doExist(username, password, email, address.getCountry(), address.getCity())) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, PARAMS_MISSING);
        }

        try {
            userService.signup(username, password, email, address, role);
            UserService.UserTokenPair result = userService.login(username, password);
            return Response.ok("signup successful", result.user()).token(result.token());
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ex);
        }
    }

    @GetMapping("/validate/username")
    public Response validateUsername(@RequestParam("data") String username) {
        if (!ServiceUtils.validateUsername(username)) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "invalid username format");
        }
        if (userService.usernameExists(username)) {
            throw new ResponseException(HttpStatus.CONFLICT, "username already exists");
        }
        return Response.ok("username is available");
    }

    @GetMapping("/validate/email")
    public Response validateEmail(@RequestParam("data") String email) {
        if (!ServiceUtils.validateEmail(email)) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "invalid email format");
        }
        if (userService.emailExists(email)) {
            throw new ResponseException(HttpStatus.CONFLICT, "email already registered");
        }
        return Response.ok("email not registered");
    }
}
