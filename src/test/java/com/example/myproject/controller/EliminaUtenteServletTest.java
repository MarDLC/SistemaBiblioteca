package com.example.myproject.controller;

import com.example.myproject.config.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EliminaUtenteServletTest {

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

    private EliminaUtenteServlet eliminaUtenteServlet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        eliminaUtenteServlet = new EliminaUtenteServlet();
        eliminaUtenteServlet.setDatabaseConnection(dbConnection);
    }

    @Test
    public void testDoPost() throws Exception {
        when(request.getParameter("username")).thenReturn("test");

        eliminaUtenteServlet.doPost(request, response);

        verify(dbConnection, times(1)).eliminaUtente(anyString());
        verify(response, times(1)).sendRedirect(stringArgumentCaptor.capture());
        assertEquals("VisualizzaUtentiServlet", stringArgumentCaptor.getValue());
    }
}

