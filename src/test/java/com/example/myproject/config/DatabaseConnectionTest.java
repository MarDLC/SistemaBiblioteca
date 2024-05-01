package com.example.myproject.config;

import com.example.myproject.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseConnectionTest {
    private DatabaseConnection databaseConnection;
    private Utente utente;

    @BeforeEach
    public void setUp() {
        databaseConnection = new DatabaseConnection();
        utente = new Utente();
        utente.setUsername("testUser");
        utente.setPassword("testPassword");
    }

    @Test
    public void inserisciUtenteTest() {
        assertDoesNotThrow(() -> {
            databaseConnection.inserisciUtente(utente);
        });
    }

    @Test
    public void getConnessioneTest() {
        assertDoesNotThrow(() -> {
            Connection conn = DatabaseConnection.getConnessione();
            assertNotNull(conn);
        });
    }

}
