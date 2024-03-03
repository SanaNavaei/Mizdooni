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
        String manager = mizdooni.getCurrentUser().getUsername();
        Restaurant restaurant = mizdooni.searchRestaurantByManager(manager);
        String page;
        if (role == User.Role.client) {
            page = "client_home.jsp";
        } else {
            request.setAttribute("restaurant", restaurant);
            request.setAttribute("tables", restaurant.getTables());
            page = "manager_home.jsp";
        }
        request.setAttribute("username", mizdooni.getCurrentUser().getUsername());
        request.getRequestDispatcher(page).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int tableNumber = Integer.parseInt(request.getParameter("table_number"));
        String seatsNumber = request.getParameter("seats_number");
        String manager = mizdooni.getCurrentUser().getUsername();
        Restaurant restaurant = mizdooni.searchRestaurantByManager(manager);

        try {
            mizdooni.addTable(tableNumber, restaurant.getName(), manager, seatsNumber);
            response.sendRedirect("/");
        } catch (Exception ex) {
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/errors/error.jsp").forward(request, response);
        }
    }
}
