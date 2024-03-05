<%@ page import="model.Reservation" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>

<%
  List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Reservations</title>
  <style>
    button[value="cancel"] {
      color: white;
      background-color: red;
      border: none;
      border-radius: 4px;
      padding: 4px 8px;
    }

    button[value="cancel"]:hover {
      cursor: pointer;
    }
  </style>
</head>

<body>
  <p id="username">username: ${username} <a href="/">Home</a> <a href="/logout" style="color: red">Log Out</a></p>
  <br>

  <h1>Your Reservations:</h1>

  <table border="1" style="width:100%; text-align:center;">
    <tr>
      <th>Reservation ID</th>
      <th>Restaurant Name</th>
      <th>Table Number</th>
      <th>Date & Time</th>
      <th>Cancellation</th>
    </tr>

    <% for (Reservation r : reservations) { %>
      <tr>
        <td><%=r.getReservationNumber()%></td>
        <td><a href="/restaurants/<%=r.getRestaurant().getId()%>"><%=r.getRestaurant().getName()%></a></td>
        <td><%=r.getTable().getTableNumber()%></td>
        <td><%=r.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))%></td>
        <td>
          <form action="" method="POST">
            <input type="hidden" name="reservationNumber" value="<%=r.getReservationNumber()%>">
            <button type="submit" name="action" value="cancel">Cancel</button>
          </form>
        </td>
      </tr>
    <% } %>
  </table>
</body>

</html>
