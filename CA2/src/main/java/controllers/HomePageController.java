package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Restaurant;
import model.User;
import service.MizDooni;

import java.io.IOException;

@WebServlet("/")
public class HomePageController extends HttpServlet {
    private MizDooni mizdooni = MizDooni.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User.Role role = mizdooni.getCurrentUser().getRole();
        String username = mizdooni.getCurrentUser().getUsername();

        String page = null;
        switch (role) {
            case client:
                page = "client_home.jsp";
                break;
            case manager:
                Restaurant restaurant = mizdooni.searchRestaurantByManager(username);
                request.setAttribute("restaurant", restaurant);
                request.setAttribute("tables", restaurant.getTables());
                page = "manager_home.jsp";
                break;
        }

        request.setAttribute("username", username);
        request.getRequestDispatcher(page).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int tableNumber;
        try {
            tableNumber = Integer.parseUnsignedInt(request.getParameter("table_number"));
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String seatsNumber = request.getParameter("seats_number");
        if (seatsNumber == null || seatsNumber.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        User.Role role = mizdooni.getCurrentUser().getRole();
        String username = mizdooni.getCurrentUser().getUsername();
        Restaurant restaurant = mizdooni.searchRestaurantByManager(username);

        if (role != User.Role.manager) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            mizdooni.addTable(tableNumber, restaurant.getName(), username, seatsNumber);
            response.sendRedirect("/");
        } catch (Exception ex) {
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/errors/error.jsp").forward(request, response);
        }
    }
}
