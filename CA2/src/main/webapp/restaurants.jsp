<%@ page import="java.util.List" %>
<%@ page import="model.Restaurant" %>
<%@ page import="model.Rating" %>

<%
  List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Restaurants</title>
</head>

<body>
  <p id="username">username: ${username} <a href="/">Home</a> <a href="/logout" style="color: red">Log Out</a></p>
  <br><br>

  <form action="" method="POST">
    <label>Search:</label>
    <input type="text" name="search" value="">
    <button type="submit" name="action" value="search_by_type">Search By Type</button>
    <button type="submit" name="action" value="search_by_name">Search By Name</button>
    <button type="submit" name="action" value="search_by_city">Search By City</button>
    <button type="submit" name="action" value="clear">Clear Search</button>
  </form>
  <br><br>

  <form action="" method="POST">
    <label>Sort By:</label>
    <button type="submit" name="action" value="sort_by_rate">Overall Score</button>
  </form>
  <br><br>

  <table border="1" style="width:100%; text-align:center;">
    <tr>
      <th>Id</th>
      <th>Name</th>
      <th>City</th>
      <th>Type</th>
      <th>Time</th>
      <th>Service Score</th>
      <th>Food Score</th>
      <th>Ambiance Score</th>
      <th>Overall Score</th>
    </tr>

    <%
      for (Restaurant r : restaurants) {
        Rating rating = r.getAverageRating();
    %>
      <tr>
        <td><%=r.getId()%></td>
        <td><a href="/restaurants/<%=r.getId()%>"><%=r.getName()%></a></td>
        <td><%=r.getAddress().getCity()%></td>
        <td><%=r.getType()%></td>
        <td><%=r.getWorkingTime()%></td>
        <td><%=rating.service%></td>
        <td><%=rating.food%></td>
        <td><%=rating.ambiance%></td>
        <td><%=rating.overall%></td>
      </tr>
    <% } %>
  </table>
</body>

</html>
