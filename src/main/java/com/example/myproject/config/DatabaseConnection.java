package com.example.myproject.config;

import com.example.myproject.model.Ruolo;
import com.example.myproject.model.Utente;

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
            }
        }

        return utenti;
    }
}

