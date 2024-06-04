package com.example.myproject.config;

import com.example.myproject.model.Libro;
import com.example.myproject.model.Ruolo;
import com.example.myproject.model.Utente;
import com.example.myproject.model.Disponibilita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/bibliotecadb";
    private static String DATABASE_USER;
    private static String DATABASE_PASSWORD;
    private static Connection connection;

    // Logger
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            DATABASE_USER = prop.getProperty("DATABASE_USER");
            DATABASE_PASSWORD = prop.getProperty("DATABASE_PASSWORD");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
    }

    // Metodo per ottenere la connessione al database
    public static Connection getConnessione() throws SQLException, ClassNotFoundException {
        // Se la connessione è null o chiusa, crea una nuova connessione
        if (connection == null || connection.isClosed()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        }
        // Restituisce l'oggetto Connection
        return connection;
    }
    // Metodo per impostare la connessione al database
    public void setConnessione(Connection connection) {
        this.connection = connection;
    }

    // Metodo per inserire un nuovo utente nel database
    public void inserisciUtente(Utente utente) throws SQLException, ClassNotFoundException {
        // Query SQL per l'inserimento di un nuovo utente
        String sql = "INSERT INTO utenti (username, password, email, ruolo) VALUES (?, ?, ?, ?)";
        // Creazione di un oggetto PreparedStatement per l'esecuzione della query SQL
        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Impostazione dei parametri della query SQL
            stmt.setString(1, utente.getUsername());
            stmt.setString(2, utente.getPassword());
            stmt.setString(3, utente.getEmail());
            stmt.setString(4, utente.getRole().toString());

            // Esecuzione della query SQL
            stmt.executeUpdate();
        }
    }

    // Metodo per autenticare un utente
    public Utente autenticaUtente(String username, String password) throws SQLException, ClassNotFoundException {
        // Inizialmente, l'oggetto Utente è impostato a null
        Utente utente = null;
        // La query SQL seleziona tutti i campi dalla tabella 'utenti' dove il nome utente e la password corrispondono ai parametri forniti
        String sql = "SELECT * FROM utenti WHERE username = ? AND password = ?";

        // Si tenta di stabilire una connessione al database e di preparare la query SQL
        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // I parametri della query SQL vengono impostati con il nome utente e la password forniti
            stmt.setString(1, username);
            stmt.setString(2, password);
            // Si esegue la query e si ottiene un ResultSet
            ResultSet rs = stmt.executeQuery();

            // Se il ResultSet contiene almeno una riga, significa che un utente con quel nome utente e password esiste
            if (rs.next()) {
                // Si crea un nuovo oggetto Utente e si impostano i suoi campi con i valori ottenuti dal ResultSet
                utente = new Utente();
                utente.setUsername(rs.getString("username"));
                utente.setPassword(rs.getString("password"));
                utente.setEmail(rs.getString("email"));
                utente.setRole(Ruolo.valueOf(rs.getString("ruolo")));
            }
        }
        // Si restituisce l'oggetto Utente. Se non è stato trovato alcun utente, verrà restituito null
        return utente;
    }

    // Metodo per ottenere la lista di tutti gli utenti
    public List<Utente> getUtenti() throws SQLException, ClassNotFoundException {
        // Si crea una nuova lista vuota di utenti
        List<Utente> utenti = new ArrayList<>();
        // La query SQL seleziona tutti i campi dalla tabella 'utenti'
        String sql = "SELECT * FROM utenti";

        // Si tenta di stabilire una connessione al database e di preparare la query SQL
        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql);
             // Si esegue la query e si ottiene un ResultSet
             ResultSet rs = stmt.executeQuery()) {

            // Per ogni riga nel ResultSet, si crea un nuovo oggetto Utente e si impostano i suoi campi con i valori ottenuti dal ResultSet
            while (rs.next()) {
                Utente utente = new Utente();
                utente.setUsername(rs.getString("username"));
                utente.setPassword(rs.getString("password"));
                utente.setEmail(rs.getString("email"));
                utente.setRole(Ruolo.valueOf(rs.getString("ruolo")));
                // Si aggiunge l'oggetto Utente alla lista di utenti
                utenti.add(utente);
            }
        }

        // Si restituisce la lista di utenti
        return utenti;
    }

    // Metodo per eliminare un utente dal database
    public void eliminaUtente(String username) throws SQLException, ClassNotFoundException {
        // Query SQL per eliminare un utente in base al suo username
        String sql = "DELETE FROM utenti WHERE username = ?";
        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Impostazione del parametro della query SQL
            stmt.setString(1, username);
            // Esecuzione della query SQL
            stmt.executeUpdate();
        }
    }

    // Metodo per inserire un nuovo libro nel database
    public void inserisciLibro(Libro libro) throws SQLException, ClassNotFoundException {
        // Query SQL per l'inserimento di un nuovo libro
        String sql = "INSERT INTO libri (titolo, autore, genere, anno, immagineCopertina, prezzo, disponibilita) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Impostazione dei parametri della query SQL
            stmt.setString(1, libro.getTitolo());
            stmt.setString(2, libro.getAutore());
            stmt.setString(3, libro.getGenere());
            stmt.setInt(4, libro.getAnno()); // Modificato per accettare int
            stmt.setBytes(5, libro.getImmagineCopertina());
            stmt.setDouble(6, libro.getPrezzo());
            stmt.setString(7, libro.getDisponibilita().toString()); // Nuovo campo

            // Esecuzione della query SQL
            stmt.executeUpdate();
        }
    }

    // Metodo per eliminare un libro dal database
    public void eliminaLibro(String titolo) throws SQLException, ClassNotFoundException {
        // Query SQL per eliminare un libro in base al suo titolo
        String sql = "DELETE FROM libri WHERE titolo = ?";
        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Impostazione del parametro della query SQL
            stmt.setString(1, titolo);
            // Esecuzione della query SQL
            stmt.executeUpdate();
        }
    }

    // Metodo per ottenere la lista di tutti i libri
    public List<Libro> getLibri() throws SQLException, ClassNotFoundException {
        // Creazione di una nuova lista vuota di libri
        List<Libro> libri = new ArrayList<>();
        // Query SQL per selezionare tutti i libri
        String sql = "SELECT * FROM libri";

        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql);
             // Esecuzione della query SQL e ottenimento del ResultSet
             ResultSet rs = stmt.executeQuery()) {

            // Per ogni riga nel ResultSet, crea un nuovo oggetto Libro e imposta i suoi campi con i valori ottenuti dal ResultSet
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitolo(rs.getString("titolo"));
                libro.setAutore(rs.getString("autore"));
                libro.setGenere(rs.getString("genere"));
                libro.setAnno(rs.getInt("anno"));
                libro.setImmagineCopertina(rs.getBytes("immagineCopertina"));
                libro.setPrezzo(rs.getDouble("prezzo"));
                libro.setDisponibilita(Disponibilita.valueOf(rs.getString("disponibilita"))); // Nuovo campo
                // Aggiunta del libro alla lista di libri
                libri.add(libro);
            }
        }

        // Restituzione della lista di libri
        return libri;
    }

    // Metodo per aggiungere un libro ai preferiti di un utente
    public void aggiungiLibroAiPreferiti(int idUtente, int idLibro) throws SQLException, ClassNotFoundException {
        // Query SQL per l'inserimento di un libro ai preferiti di un utente
        String sql = "INSERT INTO libri_preferiti (id_utente, id_libro) VALUES (?, ?)";
        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Impostazione dei parametri della query SQL
            stmt.setInt(1, idUtente);
            stmt.setInt(2, idLibro);
            // Esecuzione della query SQL
            stmt.executeUpdate();
        }
    }

    // Metodo per rimuovere un libro dai preferiti di un utente
    public void rimuoviLibroDaiPreferiti(int idUtente, int idLibro) throws SQLException, ClassNotFoundException {
        // Query SQL per la rimozione di un libro dai preferiti di un utente
        String sql = "DELETE FROM libri_preferiti WHERE id_utente = ? AND id_libro = ?";
        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Impostazione dei parametri della query SQL
            stmt.setInt(1, idUtente);
            stmt.setInt(2, idLibro);
            // Esecuzione della query SQL
            stmt.executeUpdate();
        }
    }

    // Metodo per ottenere la lista dei libri preferiti di un utente
    public List<Libro> getLibriPreferiti(Utente utente) throws SQLException, ClassNotFoundException {
        // Creazione di una nuova lista vuota di libri preferiti
        List<Libro> libriPreferiti = new ArrayList<>();
        // Query SQL per selezionare i libri preferiti di un utente
        String sql = "SELECT l.* FROM libri l INNER JOIN libri_preferiti lp ON l.id = lp.id_libro WHERE lp.id_utente = ?";

        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Impostazione del parametro della query SQL
            stmt.setInt(1, utente.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                // Per ogni riga nel ResultSet, crea un nuovo oggetto Libro e imposta i suoi campi con i valori ottenuti dal ResultSet
                while (rs.next()) {
                    Libro libro = new Libro();
                    libro.setId(rs.getInt("id"));
                    libro.setTitolo(rs.getString("titolo"));
                    libro.setAutore(rs.getString("autore"));
                    libro.setGenere(rs.getString("genere"));
                    libro.setAnno(rs.getInt("anno"));
                    libro.setImmagineCopertina(rs.getBytes("immagineCopertina"));
                    libro.setPrezzo(rs.getDouble("prezzo"));
                    libro.setDisponibilita(Disponibilita.valueOf(rs.getString("disponibilita")));
                    // Aggiunta del libro alla lista di libri preferiti
                    libriPreferiti.add(libro);
                }
            }
        }

        // Restituzione della lista di libri preferiti
        return libriPreferiti;
    }

    // Metodo per ottenere un utente in base al suo username
    public Utente getUtenteByUsername(String username) throws SQLException, ClassNotFoundException {
        // Inizialmente, l'oggetto Utente è impostato a null
        Utente utente = null;
        // Query SQL per selezionare un utente in base al suo username
        String sql = "SELECT * FROM utenti WHERE username = ?";

        try (Connection conn = getConnessione();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Impostazione del parametro della query SQL
            stmt.setString(1, username);
            // Esecuzione della query SQL e ottenimento del ResultSet
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                utente = new Utente();
                utente.setId(rs.getInt("id"));
                utente.setUsername(rs.getString("username"));
                utente.setPassword(rs.getString("password"));
                utente.setEmail(rs.getString("email"));
                utente.setRole(Ruolo.valueOf(rs.getString("ruolo")));
            }
        }
        return utente;
    }

}