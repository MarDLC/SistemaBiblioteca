package com.example.myproject.controller;

import com.example.myproject.config.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/EliminaUtenteServlet")  // Annotazione per mappare questa servlet all'URL "/EliminaUtenteServlet"

public class EliminaUtenteServlet extends HttpServlet {  // La classe estende HttpServlet, quindi è una servlet
    private DatabaseConnection dbConnection;  // Variabile per la connessione al database

    // Logger
    private static final Logger LOGGER = Logger.getLogger(EliminaUtenteServlet.class.getName());

    public void setDatabaseConnection(DatabaseConnection dbConnection) {  // Metodo per impostare la connessione al database
        this.dbConnection = dbConnection;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServletException {
        try {
            if (dbConnection == null) {  // Se la connessione al database è null
                dbConnection = new DatabaseConnection();  // Crea una nuova connessione al database
            }

            String username = request.getParameter("username");  // Ottiene l'username dell'utente da eliminare dalla richiesta
            dbConnection.eliminaUtente(username);  // Chiama il metodo per eliminare l'utente dal database

            response.sendRedirect("VisualizzaUtentiServlet");  // Reindirizza alla servlet che visualizza gli utenti
        } catch (SQLException | ClassNotFoundException | IOException e) {  // Gestisce le eccezioni
            LOGGER.log(Level.SEVERE, e.toString(), e);
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");  // Imposta un attributo di errore nella richiesta
            request.getRequestDispatcher("/homepageAdmin.jsp").forward(request, response);  // Inoltra la richiesta e la risposta alla pagina JSP
        }
    }
}