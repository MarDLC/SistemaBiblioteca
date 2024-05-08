package com.example.myproject.controller;

import com.example.myproject.config.DatabaseConnection;
import com.example.myproject.model.Utente;
import com.example.myproject.model.Ruolo;
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

public class LoginServletTest {

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

    private LoginServlet loginServlet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        loginServlet = new LoginServlet();
        loginServlet.setDatabaseConnection(dbConnection);
    }

    @Test
    public void testDoPost() throws Exception {
        when(request.getParameter("username")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("test");
        when(request.getRequestDispatcher(stringArgumentCaptor.capture())).thenReturn(requestDispatcher);

        Utente utente = new Utente();
        utente.setUsername("test");
        utente.setPassword("test");
        utente.setEmail("test@test.com");
        utente.setRole(Ruolo.UTENTE_REGISTRATO);

        when(dbConnection.autenticaUtente("test", "test")).thenReturn(utente);

        loginServlet.doPost(request, response);

        verify(dbConnection, times(1)).autenticaUtente(anyString(), anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
        assertEquals("/home.jsp", stringArgumentCaptor.getValue());
    }

    @Test
    public void testDoGet() throws Exception {
        when(request.getRequestDispatcher(stringArgumentCaptor.capture())).thenReturn(requestDispatcher);

        loginServlet.doGet(request, response);

        verify(requestDispatcher, times(1)).forward(request, response);
        assertEquals("/login_registrazione.jsp", stringArgumentCaptor.getValue());
    }
}
