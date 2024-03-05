package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ReservationService;
import service.UserService;

import java.io.IOException;

@WebServlet("/reservations")
public class ReservationsController extends HttpServlet {
    private UserService userService = UserService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("username", userService.getCurrentUser().getUsername());
        request.setAttribute("reservations", userService.getCurrentUser().getReservations());
        request.getRequestDispatcher("/reservations.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || !action.equals("cancel")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int reservationNumber;
        try {
            reservationNumber = Integer.parseUnsignedInt(request.getParameter("reservationNumber"));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            reservationService.cancelReservation(userService.getCurrentUser().getUsername(), reservationNumber);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/errors/error.jsp").forward(request, response);
            return;
        }
        response.sendRedirect("/reservations");
    }
}
