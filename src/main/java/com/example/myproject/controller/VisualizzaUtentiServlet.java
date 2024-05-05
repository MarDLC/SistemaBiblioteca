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

@WebServlet("/VisualizzaUtentiServlet")

public class VisualizzaUtentiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();

            List<Utente> listaUtenti = dbConnection.getUtenti(); // Ottieni tutti gli utenti dal database
            System.out.println("Lista Utenti: " + listaUtenti);
            request.setAttribute("listaUtenti", listaUtenti); // Imposta la lista degli utenti come attributo della richiesta
            RequestDispatcher dispatcher = request.getRequestDispatcher("visualizzaUtenti.jsp"); // Reindirizza alla pagina JSP
            dispatcher.forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Stampa lo stack trace dell'eccezione
            System.out.println("Messaggio di errore: " + e.getMessage()); // Stampa il messaggio di errore
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");
            request.getRequestDispatcher("/homepageAdmin.jsp").forward(request, response);
        }
    }
}
