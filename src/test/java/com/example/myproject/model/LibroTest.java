package com.example.myproject.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class LibroTest {
    @Test
    public void LibroSenzaParametri() {
        Libro l = new Libro();
        assertEquals(0, l.getId());
        assertNull(l.getTitolo());
        assertNull(l.getAutore());
        assertNull(l.getGenere());
        assertEquals(0, l.getAnno());
        assertNull(l.getImmagineCopertina());
        assertEquals(0.0, l.getPrezzo(), 0.001);
        assertNull(l.getDisponibilita());
    }

    @Test
    public void getSetId() {
        Libro l = new Libro();
        l.setId(1);
        assertEquals(1, l.getId());
    }

    @Test
    public void getSetTitolo() {
        Libro l = new Libro();
        l.setTitolo("Il nome della rosa");
        assertEquals("Il nome della rosa", l.getTitolo());
    }

    @Test
    public void getSetAutore() {
        Libro l = new Libro();
        l.setAutore("Umberto Eco");
        assertEquals("Umberto Eco", l.getAutore());
    }

    @Test
    public void getSetGenere() {
        Libro l = new Libro();
        l.setGenere("Giallo");
        assertEquals("Giallo", l.getGenere());
    }

    @Test
    public void getSetAnno() {
        Libro l = new Libro();
        l.setAnno(1980);
        assertEquals(1980, l.getAnno());
    }

    @Test
    public void getSetImmagineCopertina() {
        Libro l = new Libro();
        byte[] immagineCopertina = new byte[]{0, 1, 2, 3, 4, 5}; // Un esempio di array di byte
        l.setImmagineCopertina(immagineCopertina);
        assertArrayEquals(immagineCopertina, l.getImmagineCopertina());
    }

    @Test
    public void getSetPrezzo() {
        Libro l = new Libro();
        l.setPrezzo(19.99);
        assertEquals(19.99, l.getPrezzo(), 0.001);
    }

    @Test
    public void getSetDisponibilita() {
        Libro l = new Libro();
        l.setDisponibilita(Disponibilita.DISPONIBILE);
        assertEquals(Disponibilita.DISPONIBILE, l.getDisponibilita());
    }
}

