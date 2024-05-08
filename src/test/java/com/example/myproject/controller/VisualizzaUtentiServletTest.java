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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VisualizzaUtentiServletTest {

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
    @Captor
    private ArgumentCaptor<List<Utente>> listArgumentCaptor;

    private VisualizzaUtentiServlet visualizzaUtentiServlet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        visualizzaUtentiServlet = new VisualizzaUtentiServlet();
        visualizzaUtentiServlet.setDatabaseConnection(dbConnection);
    }

    @Test
    public void testDoGet() throws Exception {
        List<Utente> listaUtenti = new ArrayList<>();
        Utente utente = new Utente();
        utente.setUsername("test");
        utente.setPassword("test");
        utente.setEmail("test@test.com");
        utente.setRole(Ruolo.UTENTE_REGISTRATO);
        listaUtenti.add(utente);

        when(dbConnection.getUtenti()).thenReturn(listaUtenti);
        when(request.getRequestDispatcher(stringArgumentCaptor.capture())).thenReturn(requestDispatcher);

        visualizzaUtentiServlet.doGet(request, response);

        verify(request, times(1)).setAttribute(eq("listaUtenti"), listArgumentCaptor.capture());
        verify(requestDispatcher, times(1)).forward(request, response);
        assertEquals("visualizzaUtenti.jsp", stringArgumentCaptor.getValue());
        assertEquals(1, listArgumentCaptor.getValue().size());
        assertEquals("test", listArgumentCaptor.getValue().get(0).getUsername());
        assertEquals("test", listArgumentCaptor.getValue().get(0).getPassword());
        assertEquals("test@test.com", listArgumentCaptor.getValue().get(0).getEmail());
        assertEquals(Ruolo.UTENTE_REGISTRATO, listArgumentCaptor.getValue().get(0).getRole());
    }
}
