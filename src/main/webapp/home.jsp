<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Libri Online</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            background-color: #f2e6ff; /* Colore di sfondo viola chiaro */
            color: #333; /* Testo nero */
        }

        header {
            background: #663399; /* Viola scuro */
            color: #fff;
            padding: 20px;
        }

        header h1 {
            margin: 0;
        }

        nav ul {
            list-style-type: none;
        }

        nav ul li {
            display: inline;
            margin-right: 20px;
        }

        nav ul li a {
            color: #fff;
            text-decoration: none;
        }

        main {
            padding: 20px;
        }

        .book-list {
            margin-bottom: 30px;
        }

        .book {
            border: 1px solid #ccc;
            padding: 20px;
            margin-bottom: 20px;
            background-color: #fff; /* Sfondo bianco */
        }

        .book img {
            max-width: 100%;
            height: auto;
            margin-bottom: 10px;
        }

        .book h3 {
            color: #663399; /* Testo viola scuro */
        }

        .action-buttons {
            text-align: center;
            margin-top: 20px;
        }

        .sell-button,
        .borrow-button {
            display: inline-block;
            background-color: #663399; /* Viola scuro */
            color: #fff;
            padding: 10px 20px;
            text-decoration: none;
            margin-right: 10px;
            border-radius: 5px;
        }

        .sell-button:hover,
        .borrow-button:hover {
            background-color: #4b0082; /* Viola più scuro al passaggio del mouse */
        }

        footer {
            background: #663399; /* Viola scuro */
            color: #fff;
            padding: 20px;
            text-align: center;
            position: fixed;
            bottom: 0;
            width: 100%;
        }

    </style>
</head>
<body>
<header>
    <h1>Libri Online</h1>
    <nav>
        <ul>
            <li><a href="#">Home</a></li>
            <li><a href="#">Vendi Libro</a></li>
            <li><a href="#">Prendi in Prestito</a></li>
            <li><a href="#">Acquista Libro</a></li>
            <li><a href="#">Il Mio Account</a></li>
        </ul>
    </nav>
</header>
<main>
    <section class="book-list">
        <h2>Libri in vendita</h2>
        <div class="book">
            <img src="book1.jpg" alt="Book 1">
            <h3>Titolo del Libro</h3>
            <p>Autore: Nome Autore</p>
            <p>Prezzo: $XX.XX</p>
            <button>Compra</button>
        </div>
        <!-- Altri libri possono essere elencati qui -->
    </section>
    <section class="action-buttons">
        <a href="#" class="sell-button">Vendi Libro</a>
        <a href="#" class="borrow-button">Prendi in Prestito</a>
    </section>
</main>
<footer>
    <p>&copy; 2024 Libri Online</p>
</footer>
</body>
</html>
