package com.example.myproject.controller;

import com.example.myproject.config.DatabaseConnection;
import com.example.myproject.model.Libro;
import com.example.myproject.model.Utente;
import com.example.myproject.model.Ruolo;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// Definizione del servlet
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    // Connessione al database
    private DatabaseConnection dbConnection;

    // Logger
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    // Metodo per impostare la connessione al database
    public void setDatabaseConnection(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Metodo POST del servlet
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupero il nome utente e la password dalla richiesta
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Se la connessione al database non è stata ancora stabilita, la creo
            if (dbConnection == null) {
                dbConnection = new DatabaseConnection();
            }

            // Autentico l'utente
            Utente utente = dbConnection.autenticaUtente(username, password);

            // Se l'utente è autenticato
            if (utente != null) {
                // Recupero il nome utente
                String nomeUtente = utente.getUsername();

                // Se l'utente è un amministratore, reindirizzo alla pagina degli utenti
                if (utente.getRole() == Ruolo.UTENTE_ADMIN) {
                    request.setAttribute("utenti", dbConnection.getUtenti());
                    request.setAttribute("libri", dbConnection.getLibri());
                    request.getRequestDispatcher("/homepageAdmin.jsp").forward(request, response);
                } else {
                    // Se l'utente è autenticato, recupero i libri dal database
                    List<Libro> libri = dbConnection.getLibri();
                    request.setAttribute("libri", libri);
                    request.getRequestDispatcher("/home.jsp").forward(request, response);
                }
            } else {
                // Se l'autenticazione fallisce, reindirizzo alla pagina di login con un messaggio di errore
                request.setAttribute("errorMessage", "username o password invalidi");
                request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Gestione delle eccezioni
            LOGGER.log(Level.SEVERE, e.toString(), e);
            // Imposto un messaggio di errore
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");
            // Inoltro la richiesta alla pagina di login
            request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
        }
    }

    // Metodo GET del servlet
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Questo metodo può essere utilizzato per mostrare la pagina di login
        request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
    }
}
