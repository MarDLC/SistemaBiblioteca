
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Homepage</title>
  <link rel="stylesheet" href="homepageAdmin.css">
</head>
<body>
<header>
  <h1><%= request.getAttribute("messaggioBenvenuto") %></h1>
  <nav>
    <ul>
      <li><a href="#">Dashboard</a></li>
      <li><a href="#">Users</a></li>
      <li><a href="#">Settings</a></li>
    </ul>
  </nav>
</header>

<main>
  <h2>Recent Activity</h2>
  <div class="activity">
    <!-- Activity items here -->
  </div>
</main>

<footer>
  <p>&copy; 2024 Admin Panel</p>
</footer>
</body>
</html>

