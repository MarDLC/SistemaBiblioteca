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

@WebServlet("/LibriPreferitiServlet")
public class LibriPreferitiServlet extends HttpServlet {
    private DatabaseConnection dbConnection;

    public void setDatabaseConnection(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        try {
            if (dbConnection == null) {
                dbConnection = new DatabaseConnection();
            }

            Utente utente = dbConnection.getUtenteByUsername(username);
            /*Utente utente = dbConnection.autenticaUtente(username, password); */

            if (utente != null) {
                String action = request.getParameter("action");
                if (action == null) {

                    return;
                }

                String idLibroStr = request.getParameter("idLibro");
                if (idLibroStr == null) {
                    return;
                }

                int idLibro;
                try {
                    idLibro = Integer.parseInt(idLibroStr);
                } catch (NumberFormatException e) {
                    return;
                }


                if ("add".equals(action)) {
                    // aggiungi il libro ai preferiti nel database
                    dbConnection.aggiungiLibroAiPreferiti(utente.getId(), idLibro);
                } else if ("remove".equals(action)) {
                    dbConnection.rimuoviLibroDaiPreferiti(utente.getId(), idLibro);
                }

                List<Libro> libriPreferiti = dbConnection.getLibriPreferiti(utente);
                request.setAttribute("libriPreferiti", libriPreferiti);
                request.getRequestDispatcher("/libriPreferiti.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "username o password invalidi");
                request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");
            request.getRequestDispatcher("/libriPreferiti.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        try {
            if (dbConnection == null) {
                dbConnection = new DatabaseConnection();
            }

            Utente utente = dbConnection.getUtenteByUsername(username);

            if (utente != null) {
                List<Libro> libriPreferiti = dbConnection.getLibriPreferiti(utente);
                request.setAttribute("libriPreferiti", libriPreferiti);
                request.getRequestDispatcher("/libriPreferiti.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Utente non trovato");
                request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");
            request.getRequestDispatcher("/libriPreferiti.jsp").forward(request, response);
        }
    }
}
