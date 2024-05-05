<%@ page import="com.example.myproject.model.Libro" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Base64" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Libri Online</title>
    <link rel="stylesheet" href="home.css">
</head>
<body>
<header>
    <nav>
        <ul>
            <li><a href="#">Home</a></li>
            <li><a href="#">Vendi Libro</a></li>
            <li><a href="#">Prendi in Prestito</a></li>
            <li><a href="#">Il Mio Account</a></li>
        </ul>
    </nav>
</header>
<main>
    <h2>Libri Online</h2>
    <div class="book-container">
        <% List<Libro> libri = (List<Libro>) request.getAttribute("libri");
            for (Libro libro : libri) {
        %>
        <div class="book">
            <h3><%= libro.getTitolo() %></h3>
            <img class="book-cover" src="data:image/png;base64,<%= Base64.getEncoder().encodeToString(libro.getImmagineCopertina()) %>" alt="Copertina del libro">
            <p>Autore: <%= libro.getAutore() %></p>
            <p>Genere: <%= libro.getGenere() %></p>
            <p>Anno: <%= libro.getAnno() %></p>
            <p>Prezzo: &euro;<%= libro.getPrezzo() %></p>
            <p>Disponibilita: <%= libro.getDisponibilita() %></p>
            <button class="btn">Compra</button> <!-- Aggiunta la classe "btn" -->
        </div>
        <% } %>
    </div>
</main>
<footer>
    <p>2024 Libri Online</p>
</footer>
</body>
</html>
