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
        request.setAttribute("username", mizdooni.getCurrentUser().getUsername());
        request.setAttribute("restaurants", mizdooni.getRestaurants());
        request.getRequestDispatcher("restaurants.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        switch (action) {
            case "search_by_type":
                String type = request.getParameter("search");
                request.setAttribute("restaurants", mizdooni.searchRestaurantsByType(type));
                break;
            case "search_by_name":
                String name = request.getParameter("search");
                request.setAttribute("restaurants", mizdooni.searchRestaurantsByName(name));
                break;
            case "search_by_city":
                String city = request.getParameter("search");
                request.setAttribute("restaurants", mizdooni.searchRestaurantsByCity(city));
                break;
            case "clear":
                request.setAttribute("restaurants", mizdooni.getRestaurants());
                break;
            case "sort_by_rate":
                request.setAttribute("restaurants", mizdooni.sortRestaurantsByRate());
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
        }

        request.setAttribute("username", mizdooni.getCurrentUser().getUsername());
        request.getRequestDispatcher("restaurants.jsp").forward(request, response);
    }
}
