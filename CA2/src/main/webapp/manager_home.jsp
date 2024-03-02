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
    <li id="id">Id: 1</li>
    <li id="name">Name: Fast Food</li>
    <li id="type">Type: Italian</li>
    <li id="time">Time: 08:00 - 23:00</li>
    <li id="description">Description: "Best food you can eat"</li>
    <li id="address">Address: North Kargar, Tehran, Iran</li>
    <li id="tables">Tables:</li>
    <ul>
      <li>table1</li>
      <li>table2</li>
      <li>table3</li>
      <li>table4</li>
      <li>table5</li>
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
