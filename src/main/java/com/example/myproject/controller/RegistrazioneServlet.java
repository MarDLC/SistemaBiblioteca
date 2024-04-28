package com.example.myproject.controller;


import com.example.myproject.config.DatabaseConnection;
import com.example.myproject.model.Utente;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {

    private static final String EMAIL_REGEX = "^(.+)@(\\S+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ciaooo");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!isValidEmail(email)) {
            // L'email non è valida. Reindirizza alla pagina di registrazione con un messaggio di errore.
            request.setAttribute("errorMessage", "Invalid email address.");
            request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
            return;
        }

        Utente utente = new Utente();
        utente.setEmail(email);
        utente.setUsername(username);
        utente.setPassword(password);
        System.out.println(utente.getUsername());
        System.out.println(utente.getPassword());

        try (Connection conn = DatabaseConnection.getConnessione()) {
            System.out.println(conn);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO utenti (email, username, password) VALUES (?, ?, ?)");
            stmt.setString(1, email);
            stmt.setString(2, username);
            stmt.setString(3, password);
            int rowsInserted = stmt.executeUpdate();
            System.out.println("ciao" + rowsInserted);


            if (rowsInserted > 0) {
                // Utente registrato con successo. Reindirizza alla pagina di login.
                response.sendRedirect("login_registrazione.jsp");
            } else {
                // Registrazione fallita. Reindirizza alla pagina di registrazione con un messaggio di errore.
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");
            request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Questo metodo può essere utilizzato per mostrare la pagina di registrazione.
        request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
    }

    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}

