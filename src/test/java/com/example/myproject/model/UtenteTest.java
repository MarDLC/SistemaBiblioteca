package com.example.myproject.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UtenteTest {
    @Test
    public void UtenteSenzaParametri() {
        Utente u = new Utente();
        assertEquals(0, u.getId());
        assertNull(u.getUsername());
        assertNull(u.getPassword());
        assertNull(u.getEmail());
        assertNull(u.getRole());
    }

    @Test
    public void getSetId() {
        Utente u = new Utente();
        u.setId(1);
        assertEquals(1, u.getId());
    }

    @Test
    public void getSetUsername() {
        Utente u = new Utente();
        u.setUsername("paperino");
        assertEquals("paperino", u.getUsername());
    }

    @Test
    public void getSetPassword() {
        Utente u = new Utente();
        u.setPassword("paperino");
    }

    @Test
    public void getSetEmail() {
        Utente u = new Utente();
        u.setEmail("paperino@gmail.com");
        assertEquals("paperino@gmail.com", u.getEmail());
    }

    @Test
    public void getSetRole() {
        Utente u = new Utente();
        u.setRole(Ruolo.UTENTE_ADMIN);
        assertEquals(Ruolo.UTENTE_ADMIN, u.getRole());
        u.setRole(Ruolo.UTENTE_REGISTRATO);
        assertEquals(Ruolo.UTENTE_REGISTRATO, u.getRole());
    }
}



