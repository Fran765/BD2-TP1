package ar.unrn.tp.jpa.services;

import ar.unrn.tp.api.TransactionService;
import ar.unrn.tp.domain.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {
    @InjectMocks
    private ClientServiceImpl clientService;
    @Mock
    private TransactionService transactionServiceMock;
    @Mock
    private EntityManager emMock;

    @BeforeEach
    void setUp() {
        this.transactionServiceMock = mock(TransactionService.class);
        this.clientService = new ClientServiceImpl(transactionServiceMock);
    }

    @Test
    @DisplayName("Crear un cliente de manera exitosa.")
    void test_Create_Successful_Client() {

        when(emMock.createQuery(Mockito.anyString(), eq(Client.class))).thenReturn(mock(TypedQuery.class));

        doAnswer(invocation -> {
            Consumer<EntityManager> action = invocation.getArgument(0);
            action.accept(emMock);
            return null;
        }).when(transactionServiceMock).executeInTransaction(Mockito.any());

        clientService.crearCliente("Juan", "Perez", 12345678, "juan.perez@example.com");

        verify(emMock).persist(Mockito.any(Client.class));
    }

    @Test
    @DisplayName("Crear un cliene con dni ya registrado.")
    void test_Create_Client_Whit_Existing_Dni() {

        TypedQuery queryMock = mock(TypedQuery.class);

        when(emMock.createQuery(Mockito.anyString(), eq(Client.class))).thenReturn(queryMock);

        when(queryMock.getSingleResult())
                .thenReturn(new Client(12345678,
                        "Existente",
                        "Cliente",
                        "existente@example.com",
                        new ArrayList<>())
                );

        doAnswer(invocation -> {
            Consumer<EntityManager> action = invocation.getArgument(0);
            action.accept(emMock);
            return null;
        }).when(transactionServiceMock).executeInTransaction(Mockito.any());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clientService.crearCliente("Juan", "Perez", 12345678, "juan.perez@example.com");
        });

        assertAll(
                () -> assertEquals(IllegalArgumentException.class, exception.getClass()),
                () -> assertEquals("Ya existe un cliente con el DNI proporcionado.", exception.getMessage())
        );
    }

    @Test
    @DisplayName("Actualizar los datos de un cliente de manera exitosa.")
    void test_Update_Data_Successful() {

    }

    @Test
    @DisplayName("Actualizar los datos de un cliente incorrectamente.")
    void test_Invalid_Update_Data() {

    }

}
