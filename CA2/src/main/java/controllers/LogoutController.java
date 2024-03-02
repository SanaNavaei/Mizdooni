package controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MizDooni;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    private MizDooni mizdooni = MizDooni.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        mizdooni.logout();
        response.sendRedirect("/login");
    }
}
