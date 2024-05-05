package com.example.myproject.model;


public class Libro {
    private int id;
    private String titolo;
    private String autore;
    private String genere;
    private int anno;
    private byte[] immagineCopertina;
    private double prezzo;
    private Disponibilita disponibilita;

    // Costruttore vuoto
    public Libro() {
    }

    // Costruttore con parametri
    public Libro(int id, String titolo, String autore, String genere, int anno, byte[] immagineCopertina, double prezzo, Disponibilita disponibilita) {
        this.id = id;
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.anno = anno;
        this.immagineCopertina = immagineCopertina;
        this.prezzo = prezzo;
        this.disponibilita = disponibilita;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public byte[] getImmagineCopertina() {
        return immagineCopertina;
    }

    public void setImmagineCopertina(byte[] immagineCopertina) {
        this.immagineCopertina = immagineCopertina;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public Disponibilita getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(Disponibilita disponibilita) {
        this.disponibilita = disponibilita;
    }
}
