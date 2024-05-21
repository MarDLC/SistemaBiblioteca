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

@WebServlet("/GestisciLibriServlet")
public class GestisciLibriServlet extends HttpServlet {
    private DatabaseConnection dbConnection;

    public void setDatabaseConnection(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (dbConnection == null) {
                dbConnection = new DatabaseConnection();
            }

            // Recupera tutti i libri dal database.
            List<Libro> listaLibri = dbConnection.getLibri();
            System.out.println("Lista Libri: " + listaLibri);
            request.setAttribute("listaLibri", listaLibri);
            RequestDispatcher dispatcher = request.getRequestDispatcher("gestisciLibri.jsp"); // Reindirizza alla pagina JSP
            dispatcher.forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");
            request.getRequestDispatcher("/homepageAdmin.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titolo = request.getParameter("titolo");

        try {
            if (dbConnection == null) {
                dbConnection = new DatabaseConnection();
            }

            // Elimina il libro dal database.
            dbConnection.eliminaLibro(titolo);

            // Reindirizza alla pagina di gestione dei libri.
            response.sendRedirect("GestisciLibriServlet");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");
            request.getRequestDispatcher("/gestisciLibri.jsp").forward(request, response);
        }
    }
}
