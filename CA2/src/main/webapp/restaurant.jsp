<%@ page import="java.util.List" %>
<%@ page import="model.Restaurant" %>
<%@ page import="model.Address" %>
<%@ page import="model.Rating" %>
<%@ page import="model.Review" %>
<%@ page import="model.Table" %>

<%
  Restaurant restaurant = (Restaurant) request.getAttribute("restaurant");
  Address address = restaurant.getAddress();
  Rating averageRating = restaurant.getAverageRating();
  List<Review> reviews = restaurant.getReviews();
  List<Table> tables = restaurant.getTables();
%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Restaurant</title>
</head>

<body>
  <p id="username">username: ${username} <a href="/">Home</a> <a href="/logout" style="color: red">Log Out</a></p>
  <br>

  <h2>Restaurant Info:</h2>
  <ul>
    <li id="id">Id: <%=restaurant.getId()%></li>
    <li id="name">Name: <%=restaurant.getName()%></li>
    <li id="type">Type: <%=restaurant.getType()%></li>
    <li id="time">Time: <%=restaurant.getWorkingTime()%></li>
    <li id="rate">Scores:</li>
    <ul>
      <li>Food: <%=averageRating.food%></li>
      <li>Service: <%=averageRating.service%></li>
      <li>Ambiance: <%=averageRating.ambiance%></li>
      <li>Overall: <%=averageRating.overall%></li>
    </ul>
    <li id="address">Address: <%=address.getStreet()%>, <%=address.getCity()%>, <%=address.getCountry()%></li>
    <li id="description">Description: <%=restaurant.getDescription()%></li>
  </ul>

  <table border="1" cellpadding="10">
    <tr>
      <td>
        <label>Reserve Table:</label>
        <form action="" method="post">
          <label>Table:</label>
          <select id="table_number" name="table_number">
            <% for (Table t : tables) { %>
            <option value="<%=t.getTableNumber()%>"><%=t.getTableNumber()%></option>
            <% } %>
          </select>
          <label>Date & Time:</label>
          <input type="datetime-local" id="date_time" name="date_time">
          <br>
          <button type="submit" name="action" value="reserve">Reserve</button>
        </form>
      </td>
    </tr>
  </table>

  <table border="1" cellpadding="10">
    <tr>
      <td>
        <label>Feedback:</label>
        <form action="" method="post">
          <label>Food Rate:</label>
          <input type="number" id="food_rate" name="food_rate" step="0.1" min="0" max="5">
          <label>Service Rate:</label>
          <input type="number" id="service_rate" name="service_rate" step="0.1" min="0" max="5">
          <label>Ambiance Rate:</label>
          <input type="number" id="ambiance_rate" name="ambiance_rate" step="0.1" min="0" max="5">
          <label>Overall Rate:</label>
          <input type="number" id="overall_rate" name="overall_rate" step="0.1" min="0" max="5">
          <br>
          <label>Comment:</label>
          <textarea name="comment" id="" cols="30" rows="5" placeholder="Enter your comment"></textarea>
          <!-- <input type="textarea" name="comment" value=""> -->
          <br>
          <button type="submit" name="action" value="feedback">Submit</button>
        </form>
      </td>
    </tr>
  </table>
  <br><br>

  <table border="1" style="width: 100%; text-align: center;">
    <caption>
      <h2>Feedbacks</h2>
    </caption>
    <tr>
      <th>Username</th>
      <th>Comment</th>
      <th>Date</th>
      <th>Food Rate</th>
      <th>Service Rate</th>
      <th>Ambiance Rate</th>
      <th>Overall Rate</th>
    </tr>

    <%
      for (Review r : reviews) {
        Rating rating = r.getRating();
    %>
      <tr>
        <td><%=r.getUser().getUsername()%></td>
        <td><%=r.getComment()%></td>
        <td><%=r.getDatetime().toLocalDate().toString()%></td>
        <td><%=rating.food%></td>
        <td><%=rating.service%></td>
        <td><%=rating.ambiance%></td>
        <td><%=rating.overall%></td>
      </tr>
    <% } %>
  </table>
</body>

</html>
