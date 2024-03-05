package controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    private UserService userService = UserService.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.logout();
        response.sendRedirect("/login");
    }
}
