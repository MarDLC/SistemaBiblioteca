<!DOCTYPE html>
<html>
<head>
    <title>Inserisci Libro</title>
    <link rel="stylesheet" type="text/css" href="inserisciLibro.css">
</head>
<body>
<div class="wrapper">
    <div class="title">Inserisci Libro</div>
    <div class="form-container">
        <div class="form-inner">
            <form action="InserisciLibroServlet" method="post" class="inserisci" enctype="multipart/form-data">
                <div class="form-left">
                    <div class="field">
                        <input type="text" name="titolo" placeholder="Titolo" required>
                    </div>
                    <div class="field">
                        <input type="text" name="autore" placeholder="Autore" required>
                    </div>
                    <div class="field">
                        <input type="text" name="genere" placeholder="Genere" required>
                    </div>
                </div>
                <div class="form-right">
                    <div class="field">
                        <input type="number" name="anno" placeholder="Anno" required>
                    </div>
                    <div class="field">
                        <input type="number" step="0.01" name="prezzo" placeholder="Prezzo" required>
                    </div>
                    <div class="field">
                        <input type="file" name="immagineCopertina" required>
                    </div>
                    <div class="field">
                        <select name="disponibilita" required>
                            <option value="DISPONIBILE">Disponibile</option>
                            <option value="VENDUTO">Venduto</option>
                            <option value="IN_PRESTITO">In prestito</option>
                        </select>
                    </div>
                </div>
                <div class="field btn">
                    <div class="btn-layer"></div>
                    <input type="submit" value="Inserisci">
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
