package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Rating;
import model.Restaurant;
import service.MizDooni;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/restaurants/*")
public class RestaurantController extends HttpServlet {
    private MizDooni mizdooni = MizDooni.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Restaurant restaurant = getRestaurant(request, response);
        if (restaurant == null) {
            return;
        }

        request.setAttribute("username", mizdooni.getCurrentUser().getUsername());
        request.setAttribute("restaurant", restaurant);
        request.getRequestDispatcher("/restaurant.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Restaurant restaurant = getRestaurant(request, response);
        if (restaurant == null) {
            return;
        }

        String action = request.getParameter("action");
        if (action == null || action.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        switch (action) {
            case "reserve":
                reserve(request, response, restaurant);
                break;
            case "feedback":
                feedback(request, response, restaurant);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private Restaurant getRestaurant(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String restaurantIdStr = request.getPathInfo().split("/")[1];
        int restaurantId;
        try {
            restaurantId = Integer.parseUnsignedInt(restaurantIdStr);
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        Restaurant restaurant = mizdooni.getRestaurant(restaurantId);
        if (restaurant == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        return restaurant;
    }

    private void reserve(HttpServletRequest request, HttpServletResponse response, Restaurant restaurant) throws ServletException, IOException {
        int tableNumber;
        LocalDateTime datetime;
        DateTimeFormatter htmlDatetimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        try {
            tableNumber = Integer.parseUnsignedInt(request.getParameter("table_number"));
            datetime = LocalDateTime.parse(request.getParameter("date_time"), htmlDatetimeFormat);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String username = mizdooni.getCurrentUser().getUsername();
        String restaurantName = restaurant.getName();

        try {
            mizdooni.reserveTable(username, restaurantName, tableNumber, datetime);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/errors/error.jsp").forward(request, response);
            return;
        }
        response.sendRedirect("/reservations");
    }

    private void feedback(HttpServletRequest request, HttpServletResponse response, Restaurant restaurant) throws ServletException, IOException {
        Rating rating = new Rating();
        try {
            rating.food = Double.parseDouble(request.getParameter("food_rate"));
            rating.service = Double.parseDouble(request.getParameter("service_rate"));
            rating.ambiance = Double.parseDouble(request.getParameter("ambiance_rate"));
            rating.overall = Double.parseDouble(request.getParameter("overall_rate"));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String comment = request.getParameter("comment");
        if (comment == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            mizdooni.addReview(mizdooni.getCurrentUser().getUsername(), restaurant.getName(),
                    rating.food, rating.service, rating.ambiance, rating.overall, comment);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/errors/error.jsp").forward(request, response);
            return;
        }
        response.sendRedirect("/restaurants/" + restaurant.getId());
    }
}
