package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MizDooni;

import java.io.IOException;

@WebServlet("/restaurants")
public class RestaurantsController extends HttpServlet {
    private MizDooni mizdooni = MizDooni.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (mizdooni.getCurrentUser() == null) {
            response.sendRedirect("/login");
            return;
        }
        request.setAttribute("username", mizdooni.getCurrentUser().getUsername());
        request.setAttribute("restaurants", mizdooni.getRestaurants());
        request.getRequestDispatcher("restaurants.jsp").forward(request, response);
    }
}
