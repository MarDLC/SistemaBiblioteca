<%@ page import="com.example.myproject.model.Libro" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Base64" %>
<%
    String username = request.getParameter("username");
%>
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
            <li><a href="LibriPreferitiServlet?username=<%= username %>">Libri preferiti</a></li>
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
        <div class="book" data-id="<%= libro.getId() %>">
            <h3><%= libro.getTitolo() %></h3>
            <img class="book-cover" src="data:image/png;base64,<%= Base64.getEncoder().encodeToString(libro.getImmagineCopertina()) %>" alt="Copertina del libro">
            <p>Autore: <%= libro.getAutore() %></p>
            <p>Genere: <%= libro.getGenere() %></p>
            <p>Anno: <%= libro.getAnno() %></p>
            <p>Prezzo: &euro;<%= libro.getPrezzo() %></p>
            <p>Disponibilita: <%= libro.getDisponibilita() %></p>
            <div class="book-action">
                <button class="btn">Compra</button>
                <span class="heart-icon" onclick='console.log("idLibro: " + "<%= libro.getId() %>"); toggleHeart(this, "<%= libro.getTitolo() %>", "<%= username %>", "<%= libro.getId() %>")'>&#9829;</span>
            </div>
        </div>
        <% } %>
    </div>
</main>
<footer>
    <p>2024 Libri Online</p>
</footer>

<script>
    function toggleHeart(element, titoloLibro, username, idLibro) {
        console.log('toggleHeart called with titoloLibro e idLibro: ' + titoloLibro + idLibro); //riga per debug
        element.classList.toggle('liked');
        var action = element.classList.contains('liked') ? 'add' : 'remove';

        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'LibriPreferitiServlet', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send('action=' + action + '&titoloLibro=' + encodeURIComponent(titoloLibro) + '&username=' + encodeURIComponent(username) + '&idLibro=' + idLibro);
    }
</script>
</body>
</html>