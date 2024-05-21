<%@ page import="com.example.myproject.model.Utente" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Visualizza Utenti</title>
    <link rel="stylesheet" type="text/css" href="visualizzaUtenti.css">
</head>
<body>
<h1>Utenti Nel Sistema</h1>
<div class="table-container">
    <table>
        <tr>
            <th>Username</th>
            <th>Email</th>
            <th>Password</th>
            <th>Ruolo</th>
            <th>Azione</th>
        </tr>
        <%
            List<Utente> listaUtenti = (List<Utente>) request.getAttribute("listaUtenti");
            if (listaUtenti != null) {
                for (Utente utente : listaUtenti) {
                    String password = utente.getPassword();
                    String hiddenPassword = password.replaceAll(".", "*");
        %>
        <tr>
            <td><%= utente.getUsername() %>
            </td>
            <td><%= utente.getEmail() %>
            </td>
            <td><%= hiddenPassword %>
            </td>
            <td><%= utente.getRole() %>
            </td>
            <td>
                <form action="EliminaUtenteServlet" method="post" onsubmit="return confermaEliminazione()">
                    <input type="hidden" name="username" value="<%= utente.getUsername() %>">
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
        var conferma = confirm("Sei sicuro di voler eliminare questo utente? Questa è un'operazione permanente.");
        return conferma; // Se l'utente clicca "OK", restituisce true. Altrimenti, restituisce false.
    }
</script>
</body>
</html>

