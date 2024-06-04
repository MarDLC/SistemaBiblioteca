package com.example.myproject.controller;

import com.example.myproject.config.DatabaseConnection;
import com.example.myproject.model.Libro;
import com.example.myproject.model.Disponibilita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InserisciLibroServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private DatabaseConnection dbConnection;
    @Mock
    private Part filePart;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    private InserisciLibroServlet inserisciLibroServlet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        inserisciLibroServlet = new InserisciLibroServlet();
        inserisciLibroServlet.setDatabaseConnection(dbConnection);
    }

    @Test
    public void testDoPost() throws Exception {
        when(request.getParameter("titolo")).thenReturn("test");
        when(request.getParameter("autore")).thenReturn("test");
        when(request.getParameter("genere")).thenReturn("test");
        when(request.getParameter("anno")).thenReturn("2000");
        when(request.getParameter("prezzo")).thenReturn("10.0");
        when(request.getParameter("disponibilita")).thenReturn("DISPONIBILE");
        when(request.getPart("immagineCopertina")).thenReturn(filePart);

        InputStream inputStream = new ByteArrayInputStream(new byte[0]);
        when(filePart.getInputStream()).thenReturn(inputStream);

        inserisciLibroServlet.doPost(request, response);

        verify(dbConnection, times(1)).inserisciLibro(any(Libro.class));
        verify(response, times(1)).sendRedirect(stringArgumentCaptor.capture());
        assertEquals("homepageAdmin.jsp", stringArgumentCaptor.getValue());
    }

    @Test
    public void testDoGet() throws Exception {
        when(request.getRequestDispatcher(stringArgumentCaptor.capture())).thenReturn(requestDispatcher);

        inserisciLibroServlet.doGet(request, response);

        verify(requestDispatcher, times(1)).forward(request, response);
        assertEquals("/inserisciLibro.jsp", stringArgumentCaptor.getValue());
    }
}