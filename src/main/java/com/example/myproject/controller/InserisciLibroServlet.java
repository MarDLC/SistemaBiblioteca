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
import java.sql.SQLException;

@WebServlet("/InserisciLibroServlet")  // Annotazione per mappare questa servlet all'URL "/InserisciLibroServlet"
@MultipartConfig(fileSizeThreshold=1024*1024*2)  // Annotazione per configurare il caricamento di file di dimensioni fino a 2MB

public class InserisciLibroServlet extends HttpServlet {  // La classe estende HttpServlet, quindi è una servlet
    private DatabaseConnection dbConnection;  // Variabile per la connessione al database

    public void setDatabaseConnection(DatabaseConnection dbConnection) {  // Metodo per impostare la connessione al database
        this.dbConnection = dbConnection;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera i parametri dalla richiesta
        String titolo = request.getParameter("titolo");
        String autore = request.getParameter("autore");
        String genere = request.getParameter("genere");
        int anno = Integer.parseInt(request.getParameter("anno"));  // Converte l'anno in int
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));  // Converte il prezzo in double
        Part filePart = request.getPart("immagineCopertina");  // Ottiene il file dell'immagine di copertina
        InputStream inputStream = filePart.getInputStream();  // Ottiene un InputStream dal file
        byte[] immagineCopertina = inputStream.readAllBytes();  // Legge tutti i byte dall'InputStream
        Disponibilita disponibilita = Disponibilita.valueOf(request.getParameter("disponibilita"));  // Ottiene la disponibilità dal parametro della richiesta

        // Crea un nuovo oggetto Libro e imposta i suoi campi
        Libro libro = new Libro();
        libro.setTitolo(titolo);
        libro.setAutore(autore);
        libro.setGenere(genere);
        libro.setAnno(anno);
        libro.setPrezzo(prezzo);
        libro.setImmagineCopertina(immagineCopertina);
        libro.setDisponibilita(disponibilita);  // Imposta la disponibilità

        try {
            if (dbConnection == null) {  // Se la connessione al database è null
                dbConnection = new DatabaseConnection();  // Crea una nuova connessione al database
            }
            dbConnection.inserisciLibro(libro);  // Inserisce il libro nel database

            // Libro inserito con successo. Reindirizza alla pagina di amministrazione.
            response.sendRedirect("homepageAdmin.jsp");
        } catch (SQLException | ClassNotFoundException e) {  // Gestisce le eccezioni
            e.printStackTrace();  // Stampa la traccia dello stack dell'eccezione
            request.setAttribute("errorMessage", "Si è verificato un errore. Riprova.");  // Imposta un attributo di errore nella richiesta
            request.getRequestDispatcher("/inserisciLibro.jsp").forward(request, response);  // Inoltra la richiesta e la risposta alla pagina JSP
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Questo metodo può essere utilizzato per mostrare la pagina di inserimento del libro.
        request.getRequestDispatcher("/inserisciLibro.jsp").forward(request, response);  // Inoltra la richiesta e la risposta alla pagina JSP
    }
}