<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Restaurants</title>
</head>

<body>
  <p id="username">username: ali <a href="/">Home</a> <a href="/logout" style="color: red">Log Out</a></p>
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
    <tr>
      <td>1</td>
      <td><a href="/restaurants/1">Fast Food</a></td>
      <td>Tehran</td>
      <td>Italian</td>
      <td>8:00 - 23:00</td>
      <td>3.45</td>
      <td>4.45</td>
      <td>3.5</td>
      <td>4.75</td>
    </tr>
    <tr>
      <td>14</td>
      <td><a href="/restaurants/14">Akbar Agha</a></td>
      <td>Esfahan</td>
      <td>Iranian</td>
      <td>8:00 - 22:30</td>
      <td>2.45</td>
      <td>3.45</td>
      <td>4.5</td>
      <td>4.75</td>
    </tr>
  </table>
</body>

</html>
