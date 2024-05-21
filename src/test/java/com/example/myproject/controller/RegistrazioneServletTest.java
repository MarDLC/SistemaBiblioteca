package com.example.myproject.controller;

import com.example.myproject.config.DatabaseConnection;
import com.example.myproject.model.Utente;
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

public class RegistrazioneServletTest {

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

    private RegistrazioneServlet registrazioneServlet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        registrazioneServlet = new RegistrazioneServlet();
        registrazioneServlet.setDatabaseConnection(dbConnection);
    }
 //
    @Test
    public void testDoPost() throws Exception {
        when(request.getParameter("email")).thenReturn("test@test.com");
        when(request.getParameter("username")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("test");
        when(request.getRequestDispatcher(stringArgumentCaptor.capture())).thenReturn(requestDispatcher);

        registrazioneServlet.doPost(request, response);

        verify(dbConnection, times(1)).inserisciUtente(any(Utente.class));
        verify(response, times(1)).sendRedirect(stringArgumentCaptor.capture());
        assertEquals("login_registrazione.jsp", stringArgumentCaptor.getValue());
    }

    @Test
    public void testDoGet() throws Exception {
        when(request.getRequestDispatcher(stringArgumentCaptor.capture())).thenReturn(requestDispatcher);

        registrazioneServlet.doGet(request, response);

        verify(requestDispatcher, times(1)).forward(request, response);
        assertEquals("/login_registrazione.jsp", stringArgumentCaptor.getValue());
    }
}
