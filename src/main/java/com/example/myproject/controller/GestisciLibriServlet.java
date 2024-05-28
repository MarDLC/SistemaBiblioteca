package com.example.myproject.controller;

import com.example.myproject.config.DatabaseConnection;
import com.example.myproject.model.Libro;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/GestisciLibriServlet")  // Annotazione per mappare questa servlet all'URL "/GestisciLibriServlet"

public class GestisciLibriServlet extends HttpServlet {  // La classe estende HttpServlet, quindi è una servlet
    private DatabaseConnection dbConnection;  // Variabile per la connessione al database

    public void setDatabaseConnection(DatabaseConnection dbConnection) {  // Metodo per impostare la connessione al database
        this.dbConnection = dbConnection;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (dbConnection == null) {  // Se la connessione al database è null
                dbConnection = new DatabaseConnection();  // Crea una nuova connessione al database
            }

            // Recupera tutti i libri dal database.
            List<Libro> listaLibri = dbConnection.getLibri();
            request.setAttribute("listaLibri", listaLibri);  // Imposta l'attributo "listaLibri" nella richiesta con la lista dei libri
            RequestDispatcher dispatcher = request.getRequestDispatcher("gestisciLibri.jsp");  // Ottiene un RequestDispatcher per la pagina JSP
            dispatcher.forward(request, response);  // Inoltra la richiesta e la risposta alla pagina JSP
        } catch (SQLException | ClassNotFoundException e) {  // Gestisce le eccezioni
            e.printStackTrace();  // Stampa la traccia dello stack dell'eccezione
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");  // Imposta un attributo di errore nella richiesta
            request.getRequestDispatcher("/homepageAdmin.jsp").forward(request, response);  // Inoltra la richiesta e la risposta alla pagina JSP
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titolo = request.getParameter("titolo");  // Ottiene il titolo del libro dalla richiesta

        try {
            if (dbConnection == null) {  // Se la connessione al database è null
                dbConnection = new DatabaseConnection();  // Crea una nuova connessione al database
            }

            // Elimina il libro dal database.
            dbConnection.eliminaLibro(titolo);

            // Reindirizza alla pagina di gestione dei libri.
            response.sendRedirect("GestisciLibriServlet");
        } catch (SQLException | ClassNotFoundException e) {  // Gestisce le eccezioni
            e.printStackTrace();  // Stampa la traccia dello stack dell'eccezione
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");  // Imposta un attributo di errore nella richiesta
            request.getRequestDispatcher("/gestisciLibri.jsp").forward(request, response);  // Inoltra la richiesta e la risposta alla pagina JSP
        }
    }
}