<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Restaurant</title>
</head>

<body>
  <p id="username">username: ali <a href="/">Home</a> <a href="/logout" style="color: red">Log Out</a></p>
  <br>

  <h2>Restaurant Info:</h2>
  <ul>
    <li id="id">Id: 1</li>
    <li id="name">Name: Fast Food</li>
    <li id="type">Type: Italian</li>
    <li id="time">Time: 08:00 - 23:00</li>
    <li id="rate">Scores:</li>
    <ul>
      <li>Food: 3.45</li>
      <li>Service: 2.5</li>
      <li>Ambiance: 4.59</li>
      <li>Overall: 4.1</li>
    </ul>
    <li id="address">Address: North Kargar, Tehran, Iran</li>
    <li id="description">Description: Best food you can eat. Best Italian food</li>
  </ul>

  <table border="1" cellpadding="10">
    <tr>
      <td>
        <label>Reserve Table:</label>
        <form action="" method="post">
          <label>Table:</label>
          <select id="table_number" name="table_number">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
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
    <tr>
      <td>user1</td>
      <td>Food was not bad</td>
      <td>2022-07-25</td>
      <td>4.5</td>
      <td>3</td>
      <td>4.5</td>
      <td>4</td>
    </tr>
  </table>
</body>

</html>
