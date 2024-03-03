<%@ page import="java.util.List" %>
<%@ page import="model.Restaurant" %>
<%@ page import="model.Rating" %>
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

  <table style="width:100%; text-align:center;" border="1">
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
      List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
      for (var i = 0; i < restaurants.size(); i++) {
        Rating rating = restaurants.get(i).getAverageRating();
    %>
      <tr>
        <td><%=restaurants.get(i).getId()%></td>
        <td><a href="/restaurants/<%=restaurants.get(i).getId()%>"><%=restaurants.get(i).getName()%></a></td>
        <td><%=restaurants.get(i).getCity()%></td>
        <td><%=restaurants.get(i).getType()%></td>
        <td><%=restaurants.get(i).getTime()%></td>
        <td><%=rating.service%></td>
        <td><%=rating.food%></td>
        <td><%=rating.ambiance%></td>
        <td><%=rating.overall%></td>
      </tr>
    <% } %>
  </table>
</body>

</html>
