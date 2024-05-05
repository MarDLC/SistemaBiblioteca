package com.example.myproject.controller;

import com.example.myproject.config.DatabaseConnection;
import com.example.myproject.model.Libro;
import com.example.myproject.model.Disponibilita;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/InserisciLibroServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2) // 2MB
public class InserisciLibroServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String titolo = request.getParameter("titolo");
            String autore = request.getParameter("autore");
            String genere = request.getParameter("genere");
            int anno = Integer.parseInt(request.getParameter("anno")); // Modificato in int
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            Part filePart = request.getPart("immagineCopertina");
            InputStream inputStream = filePart.getInputStream();
            byte[] immagineCopertina = inputStream.readAllBytes();
            Disponibilita disponibilita = Disponibilita.valueOf(request.getParameter("disponibilita")); // Nuovo campo

            Libro libro = new Libro();
            libro.setTitolo(titolo);
            libro.setAutore(autore);
            libro.setGenere(genere);
            libro.setAnno(anno);
            libro.setPrezzo(prezzo);
            libro.setImmagineCopertina(immagineCopertina);
            libro.setDisponibilita(disponibilita); // Nuovo campo

            DatabaseConnection dbConnection = new DatabaseConnection();
            dbConnection.inserisciLibro(libro);

            // Libro inserito con successo. Reindirizza alla pagina di amministrazione.
            response.sendRedirect("homepageAdmin.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");
            request.getRequestDispatcher("/inserisciLibro.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Questo metodo può essere utilizzato per mostrare la pagina di inserimento del libro.
        request.getRequestDispatcher("/inserisciLibro.jsp").forward(request, response);
    }
}
