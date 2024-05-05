<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pannello di Amministrazione</title>
    <link rel="stylesheet" href="homepageAdmin.css">
</head>
<body>
<header>
    <h1> Benvenuto Amministratore</h1>
</header>

<main>
    <div class="buttons">
        <button id="viewUsersButton" onclick="window.location.href='VisualizzaUtentiServlet'">Visualizza Utenti</button>
        <button id="banUsersButton" onclick="window.location.href='bannaUtenti.jsp'">Banna Utenti</button>
        <button id="addBookButton" onclick="window.location.href='inserisciLibro.jsp'">Inserisci Libro</button>
        <button id="removeBookButton" onclick="window.location.href='rimuoviLibro.jsp'">Rimuovi Libro</button>

    </div>
</main>

<footer>
    <p>Pannello di Amministrazione</p>
</footer>

<script>
    // Seleziona tutti i bottoni
    var buttons = document.querySelectorAll("button");

    // Aggiungi event listener a ciascun bottone
    for (var i = 0; i < buttons.length; i++) {
        buttons[i].addEventListener("mouseover", function() {
            this.style.backgroundColor = "#7d48b1"; // Cambia il colore di sfondo al passaggio del mouse
        });
        buttons[i].addEventListener("mouseout", function() {
            this.style.backgroundColor = "#663399"; // Ripristina il colore di sfondo originale quando il mouse esce
        });
    }
</script>
</body>
</html>
