package mizdooni.filters;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mizdooni.response.ResponseException;
import mizdooni.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

public class AuthInterceptor implements HandlerInterceptor {
    private static final String LOGIN_REQUIRED = "login required";
    private JwtService jwtService;

    public AuthInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        if (!method.isAnnotationPresent(LoginRequired.class) &&
                !method.getDeclaringClass().isAnnotationPresent(LoginRequired.class)) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseException(HttpStatus.UNAUTHORIZED, LOGIN_REQUIRED);
        }
        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            throw new ResponseException(HttpStatus.UNAUTHORIZED, LOGIN_REQUIRED);
        }

        int userId = jwtService.getUserId(token);
        request.setAttribute("userId", userId);
        return true;
    }
}
