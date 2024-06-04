<%@ page import="com.example.myproject.model.Libro" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Base64" %>
<%
    // Recupero il nome utente dalla richiesta
    String username = request.getParameter("username");
%>
<!DOCTYPE html>
<html lang="it">
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
            <!-- Link ai libri preferiti dell'utente -->
            <li><a href="LibriPreferitiServlet?username=<%= username %>">Libri preferiti</a></li>
            <li><a href="#">Il Mio Account</a></li>
        </ul>
    </nav>
</header>
<main>
    <h2>Libri Online</h2>
    <div class="book-container">
        <%
            // Recupero la lista dei libri dalla richiesta
            List<Libro> libri = (List<Libro>) request.getAttribute("libri");
            for (Libro libro : libri) {
        %>
        <div class="book" data-id="<%= libro.getId() %>">
            <h3><%= libro.getTitolo() %></h3>
            <!-- Immagine di copertina del libro -->
            <img class="book-cover" src="data:image/png;base64,<%= Base64.getEncoder().encodeToString(libro.getImmagineCopertina()) %>" alt="Copertina del libro">
            <p>Autore: <%= libro.getAutore() %></p>
            <p>Genere: <%= libro.getGenere() %></p>
            <p>Anno: <%= libro.getAnno() %></p>
            <p>Prezzo: &euro;<%= libro.getPrezzo() %></p>
            <p>Disponibilita: <%= libro.getDisponibilita() %></p>
            <div class="book-action">
                <!-- Bottone per l'acquisto del libro -->
                <button class="btn">Compra</button>
                <!-- Icona del cuore per aggiungere/rimuovere il libro dai preferiti -->
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
    // Definizione della funzione toggleHeart. Questa funzione viene chiamata quando l'utente clicca sull'icona del cuore per un libro.
    // Prende in input quattro parametri: l'elemento HTML del cuore, il titolo del libro, il nome utente e l'ID del libro.
    function toggleHeart(element, titoloLibro, username, idLibro) {
        // Stampa un messaggio di debug nel console del browser. Questo può essere utile durante lo sviluppo per verificare che la funzione sia chiamata correttamente.
        console.log('toggleHeart called with titoloLibro e idLibro: ' + titoloLibro + idLibro);

        // Alterna la classe 'liked' sull'elemento del cuore. Questo cambierà l'aspetto del cuore se l'utente ha già messo 'mi piace' al libro.
        element.classList.toggle('liked');

        // Determina l'azione da eseguire. Se l'elemento del cuore ha la classe 'liked', l'azione sarà 'add'. Altrimenti, sarà 'remove'.
        var action = element.classList.contains('liked') ? 'add' : 'remove';

        // Crea un nuovo oggetto XMLHttpRequest. Questo oggetto viene utilizzato per inviare una richiesta HTTP al server.
        var xhr = new XMLHttpRequest();

        // Configura la richiesta come POST alla servlet 'LibriPreferitiServlet'.
        xhr.open('POST', 'LibriPreferitiServlet', true);

        // Imposta l'intestazione 'Content-Type' della richiesta come 'application/x-www-form-urlencoded'. Questo indica al server che i dati inviati con la richiesta sono codificati come stringhe URL.
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        // Invia la richiesta al server. I dati inviati con la richiesta includono l'azione, il titolo del libro, il nome utente e l'ID del libro. Questi dati sono codificati come stringhe URL.
        xhr.send('action=' + action + '&titoloLibro=' + encodeURIComponent(titoloLibro) + '&username=' + encodeURIComponent(username) + '&idLibro=' + idLibro);
    }
</script>
</body>
</html>