<%@ page import="java.util.List" %>
<%@ page import="model.Restaurant" %>
<%@ page import="model.Address" %>
<%@ page import="model.Table" %>

<%
  List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
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

<%
  for (Restaurant r : restaurants) {
    Address address = r.getAddress();
%>
<ul>
  <li id="id">Id: <%=r.getId()%></li>
  <li id="name">Name: <%=r.getName()%></li>
  <li id="type">Type: <%=r.getType()%></li>
  <li id="time">Time: <%=r.getWorkingTime()%></li>
  <li id="description">Description: <%=r.getDescription()%></li>
  <li id="address">Address: <%=address.getStreet()%>, <%=address.getCity()%>, <%=address.getCountry()%></li>
  <li id="tables">Tables:</li>
  <ul>
    <% for (Table t : r.getTables()) { %>
    <li><%=t.getTableNumber()%></li>
    <% } %>
  </ul>
</ul>

<table border="1" cellpadding="10">
  <tr>
    <td>
      <h3>Add Table:</h3>
      <form method="post" action="">
        <input name="restaurant_id" value="<%=r.getId()%>" type="hidden">
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

<hr>
<% } %>
</body>

</html>
