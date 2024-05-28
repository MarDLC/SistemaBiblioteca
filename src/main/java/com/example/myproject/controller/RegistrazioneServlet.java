package com.example.myproject.controller;

import com.example.myproject.config.DatabaseConnection;
import com.example.myproject.model.Utente;
import com.example.myproject.model.Ruolo;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Definizione del servlet
@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {

    // Espressione regolare per la validazione dell'email
    private static final String EMAIL_REGEX = "^(.+)@(\\S+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Connessione al database
    private DatabaseConnection dbConnection;

    // Metodo per impostare la connessione al database
    public void setDatabaseConnection(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Metodo POST del servlet
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupero l'email, il nome utente e la password dalla richiesta
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Verifico se l'email è valida
        if (!isValidEmail(email)) {
            // Se l'email non è valida, reindirizzo alla pagina di registrazione con un messaggio di errore
            request.setAttribute("errorMessage", "indirizzo email non valido.");
            request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
            return;
        }

        // Creo un nuovo utente
        Utente utente = new Utente();
        utente.setEmail(email);
        utente.setUsername(username);
        utente.setPassword(password);
        utente.setRole(Ruolo.UTENTE_REGISTRATO);

        try {
            // Se la connessione al database non è stata ancora stabilita, la creo
            if (dbConnection == null) {
                dbConnection = new DatabaseConnection();
            }
            // Inserisco l'utente nel database
            dbConnection.inserisciUtente(utente);

            // Utente registrato con successo. Reindirizzo alla pagina di login
            response.sendRedirect("login_registrazione.jsp");
        } catch (SQLException | ClassNotFoundException e) {
            // Gestione delle eccezioni
            e.printStackTrace();
            // Imposto un messaggio di errore
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");
            // Inoltro la richiesta alla pagina di registrazione
            request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
        }
    }

    // Metodo GET del servlet
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Questo metodo può essere utilizzato per mostrare la pagina di registrazione
        request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
    }

    // Metodo per la validazione dell'email
    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        // Verifico se l'email corrisponde all'espressione regolare
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}