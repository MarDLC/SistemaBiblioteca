package com.example.myproject.controller;

import com.example.myproject.config.DatabaseConnection;
import com.example.myproject.model.Libro;
import com.example.myproject.model.Utente;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// Definizione del servlet
@WebServlet("/LibriPreferitiServlet")
public class LibriPreferitiServlet extends HttpServlet {
    // Connessione al database
    private DatabaseConnection dbConnection;

    // Logger
    private static final Logger LOGGER = Logger.getLogger(LibriPreferitiServlet.class.getName());

    // Metodo per impostare la connessione al database
    public void setDatabaseConnection(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Metodo POST del servlet
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupero il nome utente dalla richiesta
        String username = request.getParameter("username");

        try {
            // Se la connessione al database non è stata ancora stabilita, la creo
            if (dbConnection == null) {
                dbConnection = new DatabaseConnection();
            }

            // Recupero l'utente dal database
            Utente utente = dbConnection.getUtenteByUsername(username);

            // Se l'utente esiste
            if (utente != null) {
                // Recupero l'azione dalla richiesta
                String action = request.getParameter("action");
                // Se non c'è un'azione, termino
                if (action == null) {
                    return;
                }

                // Recupero l'ID del libro dalla richiesta
                String idLibroStr = request.getParameter("idLibro");
                // Se non c'è un ID del libro, termino
                if (idLibroStr == null) {
                    return;
                }

                // Converto l'ID del libro in un intero
                int idLibro;
                try {
                    idLibro = Integer.parseInt(idLibroStr);
                } catch (NumberFormatException e) {
                    return;
                }

                // Se l'azione è "add", aggiungo il libro ai preferiti
                if ("add".equals(action)) {
                    dbConnection.aggiungiLibroAiPreferiti(utente.getId(), idLibro);
                }
                // Se l'azione è "remove", rimuovo il libro dai preferiti
                else if ("remove".equals(action)) {
                    dbConnection.rimuoviLibroDaiPreferiti(utente.getId(), idLibro);
                }

                // Recupero la lista dei libri preferiti
                List<Libro> libriPreferiti = dbConnection.getLibriPreferiti(utente);
                // Imposto la lista dei libri preferiti come attributo della richiesta
                request.setAttribute("libriPreferiti", libriPreferiti);
                // Inoltro la richiesta alla pagina JSP
                request.getRequestDispatcher("/libriPreferiti.jsp").forward(request, response);
            } else {
                // Se l'utente non esiste, imposto un messaggio di errore
                request.setAttribute("errorMessage", "username o password invalidi");
                // Inoltro la richiesta alla pagina di login
                request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Gestione delle eccezioni
            LOGGER.log(Level.SEVERE, e.toString(), e);
            // Imposto un messaggio di errore
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");
            // Inoltro la richiesta alla pagina JSP
            request.getRequestDispatcher("/libriPreferiti.jsp").forward(request, response);
        }
    }

    // Metodo GET del servlet
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupero il nome utente dalla richiesta
        String username = request.getParameter("username");

        try {
            // Se la connessione al database non è stata ancora stabilita, la creo
            if (dbConnection == null) {
                dbConnection = new DatabaseConnection();
            }

            // Recupero l'utente dal database
            Utente utente = dbConnection.getUtenteByUsername(username);

            // Se l'utente esiste
            if (utente != null) {
                // Recupero la lista dei libri preferiti
                List<Libro> libriPreferiti = dbConnection.getLibriPreferiti(utente);
                // Imposto la lista dei libri preferiti come attributo della richiesta
                request.setAttribute("libriPreferiti", libriPreferiti);
                // Inoltro la richiesta alla pagina JSP
                request.getRequestDispatcher("/libriPreferiti.jsp").forward(request, response);
            } else {
                // Se l'utente non esiste, imposto un messaggio di errore
                request.setAttribute("errorMessage", "Utente non trovato");
                // Inoltro la richiesta alla pagina di login
                request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Gestione delle eccezioni
            LOGGER.log(Level.SEVERE, e.toString(), e);
            // Imposto un messaggio di errore
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");
            // Inoltro la richiesta alla pagina JSP
            request.getRequestDispatcher("/libriPreferiti.jsp").forward(request, response);
        }
    }
}
