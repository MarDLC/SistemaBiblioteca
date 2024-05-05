package com.example.myproject.config;

import com.example.myproject.model.Libro;
import com.example.myproject.model.Ruolo;
import com.example.myproject.model.Utente;
import com.example.myproject.model.Disponibilita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/BibliotecaDB";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "admin";
    private static Connection connection;

    public static Connection getConnessione() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        }
        return connection;
    }

    public void inserisciUtente(Utente utente) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO utenti (username, password, email, ruolo) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, utente.getUsername());
            stmt.setString(2, utente.getPassword());
            stmt.setString(3, utente.getEmail());
            stmt.setString(4, utente.getRole().toString());

            stmt.executeUpdate();
        }
    }

    public Utente autenticaUtente(String username, String password) throws SQLException, ClassNotFoundException {
        Utente utente = null;
        String sql = "SELECT * FROM utenti WHERE username = ? AND password = ?";

        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                utente = new Utente();
                utente.setUsername(rs.getString("username"));
                utente.setPassword(rs.getString("password"));
                utente.setEmail(rs.getString("email"));
                utente.setRole(Ruolo.valueOf(rs.getString("ruolo")));
            }
        }
        return utente;
    }

    public List<Utente> getUtenti() throws SQLException, ClassNotFoundException {
        List<Utente> utenti = new ArrayList<>();
        String sql = "SELECT * FROM utenti";

        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Utente utente = new Utente();
                utente.setUsername(rs.getString("username"));
                utente.setPassword(rs.getString("password"));
                utente.setEmail(rs.getString("email"));
                utente.setRole(Ruolo.valueOf(rs.getString("ruolo")));
                utenti.add(utente);
                System.out.println("Utente aggiunto: " + utente.getUsername()); // Aggiunto per il debug
            }
        }

        return utenti;
    }
    public void inserisciLibro(Libro libro) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO libri (titolo, autore, genere, anno, immagineCopertina, prezzo, disponibilita) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitolo());
            stmt.setString(2, libro.getAutore());
            stmt.setString(3, libro.getGenere());
            stmt.setInt(4, libro.getAnno()); // Modificato per accettare int
            stmt.setBytes(5, libro.getImmagineCopertina());
            stmt.setDouble(6, libro.getPrezzo());
            stmt.setString(7, libro.getDisponibilita().toString()); // Nuovo campo

            stmt.executeUpdate();
        }
    }

    public List<Libro> getLibri() throws SQLException, ClassNotFoundException {
        List<Libro> libri = new ArrayList<>();
        String sql = "SELECT * FROM libri";

        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Libro libro = new Libro();
                libro.setTitolo(rs.getString("titolo"));
                libro.setAutore(rs.getString("autore"));
                libro.setGenere(rs.getString("genere"));
                libro.setAnno(rs.getInt("anno"));
                libro.setImmagineCopertina(rs.getBytes("immagineCopertina"));
                libro.setPrezzo(rs.getDouble("prezzo"));
                libro.setDisponibilita(Disponibilita.valueOf(rs.getString("disponibilita"))); // Nuovo campo
                libri.add(libro);
            }
        }

        return libri;
    }
}
