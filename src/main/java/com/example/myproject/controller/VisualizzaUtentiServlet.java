package com.example.myproject.controller;

import com.example.myproject.config.DatabaseConnection;
import com.example.myproject.model.Utente;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// Definizione del servlet
@WebServlet("/VisualizzaUtentiServlet")
public class VisualizzaUtentiServlet extends HttpServlet {
    // Connessione al database
    private DatabaseConnection dbConnection;

    // Logger
    private static final Logger LOGGER = Logger.getLogger(VisualizzaUtentiServlet.class.getName());

    // Metodo per impostare la connessione al database
    public void setDatabaseConnection(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Metodo GET del servlet
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Se la connessione al database non è stata ancora stabilita, la creo
            if (dbConnection == null) {
                dbConnection = new DatabaseConnection();
            }

            // Ottengo tutti gli utenti dal database
            List<Utente> listaUtenti = dbConnection.getUtenti();
            // Imposto la lista degli utenti come attributo della richiesta
            request.setAttribute("listaUtenti", listaUtenti);
            // Reindirizzo alla pagina JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("visualizzaUtenti.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            // Gestione delle eccezioni
            LOGGER.log(Level.SEVERE, e.toString(), e);
            // Imposto un messaggio di errore
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");
            // Inoltro la richiesta alla pagina JSP
            request.getRequestDispatcher("/homepageAdmin.jsp").forward(request, response);
        }
    }
}
