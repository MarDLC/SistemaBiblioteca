package com.example.myproject.controller;

import com.example.myproject.config.DatabaseConnection;
import com.example.myproject.model.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GestisciLibriServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private DatabaseConnection dbConnection;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    private GestisciLibriServlet gestisciLibriServlet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        gestisciLibriServlet = new GestisciLibriServlet();
        gestisciLibriServlet.setDatabaseConnection(dbConnection);
    }

    @Test
    public void testDoGet() throws Exception {
        List<Libro> listaLibri = new ArrayList<>();
        when(dbConnection.getLibri()).thenReturn(listaLibri);
        when(request.getRequestDispatcher(stringArgumentCaptor.capture())).thenReturn(requestDispatcher);

        gestisciLibriServlet.doGet(request, response);

        verify(requestDispatcher, times(1)).forward(request, response);
        assertEquals("gestisciLibri.jsp", stringArgumentCaptor.getValue());
    }

    @Test
    public void testDoPost() throws Exception {
        when(request.getParameter("titolo")).thenReturn("test");

        gestisciLibriServlet.doPost(request, response);

        verify(dbConnection, times(1)).eliminaLibro(anyString());
        verify(response, times(1)).sendRedirect(stringArgumentCaptor.capture());
        assertEquals("GestisciLibriServlet", stringArgumentCaptor.getValue());
    }
}

