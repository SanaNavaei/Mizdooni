<%@ page import="java.util.List" %>
<%@ page import="model.Restaurant" %>
<%@ page import="model.Address" %>
<%@ page import="model.Table" %>

<%
  Restaurant restaurant = (Restaurant) request.getAttribute("restaurant");
  Address address = restaurant.getAddress();
  List<Table> tables = (List<Table>) request.getAttribute("tables");
%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Manager Home</title>
</head>

<body>
  <h1>Welcome ${username} <a href="/logout" style="color: red">Log Out</a></h1>

  <h2>Your Restaurant Information:</h2>
  <ul>
    <li id="id">Id: <%=restaurant.getId()%></li>
    <li id="name">Name: <%=restaurant.getName()%></li>
    <li id="type">Type: <%=restaurant.getType()%></li>
    <li id="time">Time: <%=restaurant.getWorkingTime()%></li>
    <li id="description">Description: <%=restaurant.getDescription()%></li>
    <li id="address">Address: <%=address.getStreet()%>, <%=address.getCity()%>, <%=address.getCountry()%></li>
    <li id="tables">Tables:</li>
    <ul>
      <% for (Table t : tables) { %>
      <li><%=t.getTableNumber()%></li>
      <% } %>
    </ul>
  </ul>

  <table border="1" cellpadding="10">
    <tr>
      <td>
        <h3>Add Table:</h3>
        <form method="post" action="">
          <label>Table Number:</label>
          <input name="table_number" type="number" min="0">
          <br>
          <label>Seats Number:</label>
          <input name="seats_number" type="number" min="1">
          <br>
          <button type="submit">Add</button>
        </form>
      </td>
    </tr>
  </table>
</body>

</html>
