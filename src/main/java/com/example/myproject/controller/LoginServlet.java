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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Utente utente = dbConnection.autenticaUtente(username, password);

            if (utente != null) {
                // Ottieni il nome utente dell'utente autenticato
                String nomeUtente = utente.getUsername();

                if (utente.getRole() == Ruolo.UTENTE_ADMIN) {
                    // L'utente è un amministratore. Reindirizza alla pagina degli utenti.
                    request.setAttribute("utenti", dbConnection.getUtenti());
                    request.getRequestDispatcher("/homepageAdmin.jsp").forward(request, response);
                } else {
                    // L'utente è autenticato con successo. Recupera i libri dal database.
                    List<Libro> libri = dbConnection.getLibri();
                    request.setAttribute("libri", libri);
                    request.getRequestDispatcher("/home.jsp").forward(request, response);
                }
            } else {
                // Autenticazione fallita. Reindirizza alla pagina di login con un messaggio di errore.
                request.setAttribute("errorMessage", "username o password invalidi");
                request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");
            request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Questo metodo può essere utilizzato per mostrare la pagina di login.
        request.getRequestDispatcher("/login_registrazione.jsp").forward(request, response);
    }
}

