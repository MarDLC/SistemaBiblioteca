package com.example.myproject.config;

import com.example.myproject.model.Disponibilita;
import com.example.myproject.model.Libro;
import com.example.myproject.model.Ruolo;
import com.example.myproject.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DatabaseConnectionTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    private DatabaseConnection databaseConnection;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        databaseConnection = new DatabaseConnection();
        databaseConnection.setConnessione(mockConnection);
    }

    @Test
    public void testInserisciUtente() throws SQLException, ClassNotFoundException {
        Utente utente = new Utente();
        utente.setUsername("test");
        utente.setPassword("test");
        utente.setEmail("test@test.com");
        utente.setRole(Ruolo.UTENTE_REGISTRATO);

        when(mockConnection.prepareStatement(stringArgumentCaptor.capture())).thenReturn(mockPreparedStatement);

        databaseConnection.inserisciUtente(utente);

        verify(mockPreparedStatement, times(1)).executeUpdate();
        assertEquals("INSERT INTO utenti (username, password, email, ruolo) VALUES (?, ?, ?, ?)", stringArgumentCaptor.getValue());
    }

    @Test
    public void testAutenticaUtente() throws SQLException, ClassNotFoundException {
        when(mockConnection.prepareStatement(stringArgumentCaptor.capture())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("username")).thenReturn("test");
        when(mockResultSet.getString("password")).thenReturn("test");
        when(mockResultSet.getString("email")).thenReturn("test@test.com");
        when(mockResultSet.getString("ruolo")).thenReturn("UTENTE_REGISTRATO");

        Utente utente = databaseConnection.autenticaUtente("test", "test");

        verify(mockPreparedStatement, times(1)).executeQuery();
        assertEquals("SELECT * FROM utenti WHERE username = ? AND password = ?", stringArgumentCaptor.getValue());
        assertEquals("test", utente.getUsername());
        assertEquals("test", utente.getPassword());
        assertEquals("test@test.com", utente.getEmail());
        assertEquals(Ruolo.UTENTE_REGISTRATO, utente.getRole());
    }

    @Test
    public void testGetUtenti() throws SQLException, ClassNotFoundException {
        when(mockConnection.prepareStatement(stringArgumentCaptor.capture())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("username")).thenReturn("test");
        when(mockResultSet.getString("password")).thenReturn("test");
        when(mockResultSet.getString("email")).thenReturn("test@test.com");
        when(mockResultSet.getString("ruolo")).thenReturn("UTENTE_REGISTRATO");

        List<Utente> utenti = databaseConnection.getUtenti();

        verify(mockPreparedStatement, times(1)).executeQuery();
        assertEquals("SELECT * FROM utenti", stringArgumentCaptor.getValue());
        assertEquals(1, utenti.size());
        assertEquals("test", utenti.get(0).getUsername());
        assertEquals("test", utenti.get(0).getPassword());
        assertEquals("test@test.com", utenti.get(0).getEmail());
        assertEquals(Ruolo.UTENTE_REGISTRATO, utenti.get(0).getRole());
    }

    @Test
    public void testInserisciLibro() throws SQLException, ClassNotFoundException {
        Libro libro = new Libro();
        libro.setTitolo("test");
        libro.setAutore("test");
        libro.setGenere("test");
        libro.setAnno(2000);
        libro.setPrezzo(10.0);
        libro.setDisponibilita(Disponibilita.DISPONIBILE);

        when(mockConnection.prepareStatement(stringArgumentCaptor.capture())).thenReturn(mockPreparedStatement);

        databaseConnection.inserisciLibro(libro);

        verify(mockPreparedStatement, times(1)).executeUpdate();
        assertEquals("INSERT INTO libri (titolo, autore, genere, anno, immagineCopertina, prezzo, disponibilita) VALUES (?, ?, ?, ?, ?, ?, ?)", stringArgumentCaptor.getValue());
    }

    @Test
public void testGetLibri() throws SQLException, ClassNotFoundException {
    when(mockConnection.prepareStatement(stringArgumentCaptor.capture())).thenReturn(mockPreparedStatement);
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true).thenReturn(false);
    when(mockResultSet.getInt("id")).thenReturn(1); // Mock the behavior of ResultSet.getInt("id")
    when(mockResultSet.getString("titolo")).thenReturn("test");
    when(mockResultSet.getString("autore")).thenReturn("test");
    when(mockResultSet.getString("genere")).thenReturn("test");
    when(mockResultSet.getInt("anno")).thenReturn(2000);
    when(mockResultSet.getDouble("prezzo")).thenReturn(10.0);
    when(mockResultSet.getString("disponibilita")).thenReturn("DISPONIBILE");

    List<Libro> libri = databaseConnection.getLibri();

    verify(mockPreparedStatement, times(1)).executeQuery();
    assertEquals("SELECT * FROM libri", stringArgumentCaptor.getValue());
    assertEquals(1, libri.size());
    assertEquals(1, libri.get(0).getId()); // Assert that the id of the first book in the list is as expected
    assertEquals("test", libri.get(0).getTitolo());
    assertEquals("test", libri.get(0).getAutore());
    assertEquals("test", libri.get(0).getGenere());
    assertEquals(2000, libri.get(0).getAnno());
    assertEquals(10.0, libri.get(0).getPrezzo(), 0.01);
    assertEquals(Disponibilita.DISPONIBILE, libri.get(0).getDisponibilita());
}

    @Test
    public void testEliminaUtente() throws SQLException, ClassNotFoundException {
        when(mockConnection.prepareStatement(stringArgumentCaptor.capture())).thenReturn(mockPreparedStatement);

        databaseConnection.eliminaUtente("test");

        verify(mockPreparedStatement, times(1)).executeUpdate();
        assertEquals("DELETE FROM utenti WHERE username = ?", stringArgumentCaptor.getValue());
    }

    @Test
    public void testEliminaLibro() throws SQLException, ClassNotFoundException {
        when(mockConnection.prepareStatement(stringArgumentCaptor.capture())).thenReturn(mockPreparedStatement);

        databaseConnection.eliminaLibro("test");

        verify(mockPreparedStatement, times(1)).executeUpdate();
        assertEquals("DELETE FROM libri WHERE titolo = ?", stringArgumentCaptor.getValue());
    }

    @Test
    public void testAggiungiLibroAiPreferiti() throws SQLException, ClassNotFoundException {
        when(mockConnection.prepareStatement(stringArgumentCaptor.capture())).thenReturn(mockPreparedStatement);

        databaseConnection.aggiungiLibroAiPreferiti(1, 1);

        verify(mockPreparedStatement, times(1)).executeUpdate();
        assertEquals("INSERT INTO libri_preferiti (id_utente, id_libro) VALUES (?, ?)", stringArgumentCaptor.getValue());
    }

    @Test
    public void testRimuoviLibroDaiPreferiti() throws SQLException, ClassNotFoundException {
        when(mockConnection.prepareStatement(stringArgumentCaptor.capture())).thenReturn(mockPreparedStatement);

        databaseConnection.rimuoviLibroDaiPreferiti(1, 1);

        verify(mockPreparedStatement, times(1)).executeUpdate();
        assertEquals("DELETE FROM libri_preferiti WHERE id_utente = ? AND id_libro = ?", stringArgumentCaptor.getValue());
    }

    @Test
    public void testGetLibriPreferiti() throws SQLException, ClassNotFoundException {
        when(mockConnection.prepareStatement(stringArgumentCaptor.capture())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("titolo")).thenReturn("test");
        when(mockResultSet.getString("autore")).thenReturn("test");
        when(mockResultSet.getString("genere")).thenReturn("test");
        when(mockResultSet.getInt("anno")).thenReturn(2000);
        when(mockResultSet.getDouble("prezzo")).thenReturn(10.0);
        when(mockResultSet.getString("disponibilita")).thenReturn("DISPONIBILE");

        Utente utente = new Utente();
        utente.setId(1);
        List<Libro> libriPreferiti = databaseConnection.getLibriPreferiti(utente);

        verify(mockPreparedStatement, times(1)).executeQuery();
        assertEquals("SELECT l.* FROM libri l INNER JOIN libri_preferiti lp ON l.id = lp.id_libro WHERE lp.id_utente = ?", stringArgumentCaptor.getValue());
        assertEquals(1, libriPreferiti.size());
        assertEquals("test", libriPreferiti.get(0).getTitolo());
        assertEquals("test", libriPreferiti.get(0).getAutore());
        assertEquals("test", libriPreferiti.get(0).getGenere());
        assertEquals(2000, libriPreferiti.get(0).getAnno());
        assertEquals(10.0, libriPreferiti.get(0).getPrezzo(), 0.01);
        assertEquals(Disponibilita.DISPONIBILE, libriPreferiti.get(0).getDisponibilita());
    }

    @Test
    public void testGetUtenteByUsername() throws SQLException, ClassNotFoundException {
        when(mockConnection.prepareStatement(stringArgumentCaptor.capture())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("username")).thenReturn("test");
        when(mockResultSet.getString("password")).thenReturn("test");
        when(mockResultSet.getString("email")).thenReturn("test@test.com");
        when(mockResultSet.getString("ruolo")).thenReturn("UTENTE_REGISTRATO");

        Utente utente = databaseConnection.getUtenteByUsername("test");

        verify(mockPreparedStatement, times(1)).executeQuery();
        assertEquals("SELECT * FROM utenti WHERE username = ?", stringArgumentCaptor.getValue());
        assertEquals("test", utente.getUsername());
        assertEquals("test", utente.getPassword());
        assertEquals("test@test.com", utente.getEmail());
        assertEquals(Ruolo.UTENTE_REGISTRATO, utente.getRole());
    }
}




