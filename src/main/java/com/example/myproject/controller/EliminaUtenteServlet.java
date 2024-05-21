package com.example.myproject.controller;

import com.example.myproject.config.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/EliminaUtenteServlet")

public class EliminaUtenteServlet extends HttpServlet {
    private DatabaseConnection dbConnection;

    public void setDatabaseConnection(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServletException {
        try {
            if (dbConnection == null) {
                dbConnection = new DatabaseConnection();
            }

            String username = request.getParameter("username"); // Ottieni l'username dell'utente da eliminare
            dbConnection.eliminaUtente(username); // Chiama il metodo per eliminare l'utente

            response.sendRedirect("VisualizzaUtentiServlet"); // Reindirizza alla pagina di visualizzazione degli utenti
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
            System.out.println("Messaggio di errore: " + e.getMessage());
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");
            request.getRequestDispatcher("/homepageAdmin.jsp").forward(request, response);
        }
    }
}
