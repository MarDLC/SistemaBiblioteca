<%@ page import="com.example.myproject.model.Libro" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Base64" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String username = request.getParameter("username");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Libri Preferiti</title>
    <link rel="stylesheet" href="libriPreferiti.css">
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
<h1>La tua lista di libri preferiti</h1>
<table class="book-table" aria-label="Attributi libri preferiti" >
    <thead>
     <tr>
        <th>Titolo</th>
        <th>Autore</th>
        <th>Genere</th>
        <th>Anno</th>
        <th>Prezzo</th>
        <th>Disponibilita</th>
     </tr>
    </thead>
<tbody>
    <%
        List<Libro> libriPreferiti = (List<Libro>) request.getAttribute("libriPreferiti");
        if (libriPreferiti != null) {
            for (Libro libro : libriPreferiti) {
    %>
    <tr>
        <td><%= libro.getTitolo() %></td>
        <td><%= libro.getAutore() %></td>
        <td><%= libro.getGenere() %></td>
        <td><%= libro.getAnno() %></td>
        <td>&euro;<%= libro.getPrezzo() %></td>
        <td><%= libro.getDisponibilita() %></td>
    </tr>

    <%
            }
        }
    %>
</tbody>
</table>
</body>
</html>