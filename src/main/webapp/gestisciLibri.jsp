<%@ page import="com.example.myproject.model.Libro" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Visualizza Libri</title>
    <link rel="stylesheet" type="text/css" href="visualizzaUtenti.css">
</head>
<body>
<h1>Libri Disponibili</h1>
<div class="table-container">
    <table>
        <tr>
            <th>Titolo</th>
            <th>Autore</th>
            <th>Genere</th>
            <th>Anno</th>
            <th>Prezzo</th>
            <th>Disponibilità</th>
            <th>Azione</th>
        </tr>
        <%
            List<Libro> listaLibri = (List<Libro>) request.getAttribute("listaLibri");
            if (listaLibri != null) {
                for (Libro libro : listaLibri) {
        %>
        <tr>
            <td><%= libro.getTitolo() %></td>
            <td><%= libro.getAutore() %></td>
            <td><%= libro.getGenere() %></td>
            <td><%= libro.getAnno() %></td>
            <td><%= libro.getPrezzo() %> euro</td>
            <td><%= libro.getDisponibilita() %></td>
            <td>
                <form action="GestisciLibriServlet" method="post" onsubmit="return confermaEliminazione()">
                    <input type="hidden" name="titolo" value="<%= libro.getTitolo() %>">
                    <input type="submit" value="Elimina">
                </form>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
<script>
    function confermaEliminazione() {
        var conferma = confirm("Sei sicuro di voler eliminare questo libro? Questa è un'operazione permanente.");
        return conferma; // Se l'utente clicca "OK", restituisce true. Altrimenti, restituisce false.
    }
</script>
</body>
</html>
