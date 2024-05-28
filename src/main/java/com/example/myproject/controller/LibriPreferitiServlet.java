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
        System.out.println("doPost called"); //riga aggiunta per debug
        String username = request.getParameter("username");
        /*String password = request.getParameter("password"); */
        System.out.println("utente2: " + username); // riga debug

        try {
            if (dbConnection == null) {
                dbConnection = new DatabaseConnection();
            }

            Utente utente = dbConnection.getUtenteByUsername(username);
            /*Utente utente = dbConnection.autenticaUtente(username, password); */

            if (utente != null) {
                String action = request.getParameter("action");
                if (action == null) {
                    System.out.println("action is null");
                    return;
                }

                String idLibroStr = request.getParameter("idLibro");
                System.out.println("idLibroStr: " + idLibroStr); //riga di debug per idLibro
                if (idLibroStr == null) {
                    System.out.println("idLibro is null");
                    return;
                }

                int idLibro;
                try {
                    idLibro = Integer.parseInt(idLibroStr);
                } catch (NumberFormatException e) {
                    System.out.println("idLibro is not a valid number");
                    return;
                }

                System.out.println("action: " + action); // Aggiungi questa linea
                System.out.println("idLibro: " + idLibro); // Aggiungi questa linea


                if ("add".equals(action)) {
                    System.out.println("Aggiungendo libro ai preferiti"); // Aggiungi questa linea
                    System.out.println("utente: " + utente.getUsername()); // Aggiungi questa linea
                    System.out.println("idUtente: " + utente.getId()); // Aggiungi questa linea
                    System.out.println("idLibro: " + idLibro); // Aggiungi questa linea
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
