package com.example.myproject.controller;

import com.example.myproject.config.DatabaseConnection;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("uname");
        String password = request.getParameter("psw");

        try (Connection conn = DatabaseConnection.getConnessione()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // L'utente è autenticato con successo. Reindirizza alla pagina principale.
                response.sendRedirect("/home");
            } else {
                // Autenticazione fallita. Reindirizza alla pagina di login con un messaggio di errore.
                request.setAttribute("errorMessage", "Invalid username or password.");
                request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Questo metodo può essere utilizzato per mostrare la pagina di login.
        request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
    }
}
