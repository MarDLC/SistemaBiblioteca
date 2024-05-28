package com.example.myproject.controller;

import com.example.myproject.config.DatabaseConnection;
import com.example.myproject.model.Libro;
import com.example.myproject.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static org.mockito.Mockito.*;

public class LibriPreferitiServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private DatabaseConnection dbConnection;

    private LibriPreferitiServlet libriPreferitiServlet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        libriPreferitiServlet = new LibriPreferitiServlet();
        libriPreferitiServlet.setDatabaseConnection(dbConnection);
    }

    @Test
    public void testDoPost() throws Exception {
        when(request.getParameter("username")).thenReturn("test");
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("idLibro")).thenReturn("1");

        Utente utente = new Utente();
        utente.setUsername("test");
        utente.setPassword("test");
        utente.setEmail("test@test.com");

        when(dbConnection.getUtenteByUsername("test")).thenReturn(utente);
        when(request.getRequestDispatcher("/libriPreferiti.jsp")).thenReturn(requestDispatcher);

        libriPreferitiServlet.doPost(request, response);

        verify(dbConnection, times(1)).aggiungiLibroAiPreferiti(anyInt(), anyInt());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoGet() throws Exception {
        when(request.getParameter("username")).thenReturn("test");

        Utente utente = new Utente();
        utente.setUsername("test");
        utente.setPassword("test");
        utente.setEmail("test@test.com");

        when(dbConnection.getUtenteByUsername("test")).thenReturn(utente);
        when(dbConnection.getLibriPreferiti(utente)).thenReturn(Arrays.asList(new Libro(), new Libro()));
        when(request.getRequestDispatcher("/libriPreferiti.jsp")).thenReturn(requestDispatcher);

        libriPreferitiServlet.doGet(request, response);

        verify(request, times(1)).setAttribute(eq("libriPreferiti"), anyList());
        verify(requestDispatcher, times(1)).forward(request, response);
    }
}