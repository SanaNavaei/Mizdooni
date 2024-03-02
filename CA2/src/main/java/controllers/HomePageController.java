package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.MizDooni;

import java.io.IOException;

@WebServlet("/")
public class HomePageController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User.Role role = MizDooni.getInstance().getCurrentUser().getRole();
        String page;
        if (role == User.Role.client) {
            page = "client_home.jsp";
        } else {
            page = "manager_home.jsp";
        }
        request.getRequestDispatcher(page).forward(request, response);
    }
}
