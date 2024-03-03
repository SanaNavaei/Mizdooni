package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Restaurant;
import service.MizDooni;

import java.io.IOException;

@WebServlet("/restaurants/*")
public class RestaurantController extends HttpServlet {
    private MizDooni mizdooni = MizDooni.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String restaurantIdStr = request.getPathInfo().split("/")[1];
        int restaurantId;
        try {
            restaurantId = Integer.parseUnsignedInt(restaurantIdStr);
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Restaurant restaurant = mizdooni.getRestaurant(restaurantId);
        if (restaurant == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setAttribute("username", mizdooni.getCurrentUser().getUsername());
        request.setAttribute("restaurant", restaurant);
        request.getRequestDispatcher("/restaurant.jsp").forward(request, response);
    }
}
