package ar.unrn.tp.jpa.services;

import ar.unrn.tp.api.ClientService;
import ar.unrn.tp.api.TransactionService;
import ar.unrn.tp.domain.model.CardType;
import ar.unrn.tp.domain.model.Client;
import ar.unrn.tp.domain.model.CreditCard;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final TransactionService transactionService;

    @Override
    public void crearCliente(String nombre, String apellido, Integer dni, String email) {
        this.transactionService.executeInTransaction(em -> {

            if (existsDniClient(em, dni)) {
                throw new IllegalArgumentException("Ya existe un cliente con el DNI proporcionado.");
            }
            try {
                Client cliente = new Client(dni, nombre, apellido, email, new ArrayList<>());
                em.persist(cliente);

            } catch (Exception e) {
                throw new RuntimeException("Error al crear el cliente: " + e.getMessage(), e);
            }
        });
    }

    @Override
    public void modificarCliente(Long idCliente, String nombre, String apellido, String email) {
        this.transactionService.executeInTransaction(em -> {
            Client clientDB = getClientById(em, idCliente);
            clientDB.updateData(nombre, apellido, email);
        });
    }

    @Override
    public void agregarTarjeta(Long idCliente, String nro, String marca) {
        this.transactionService.executeInTransaction(em -> {
            Client clientDB = getClientById(em, idCliente);
            clientDB.addCreditCard(new CreditCard(Long.parseLong(nro), CardType.valueOf(marca.toUpperCase())));
        });
    }

    @Override
    public List<CreditCard> listarTarjetas(Long idCliente) {
        List<CreditCard> cardsByClient = new ArrayList<>();
        this.transactionService.executeInTransaction(em -> {
            Client clientDB = getClientById(em, idCliente);
            cardsByClient.addAll(clientDB.getCreditCards().toList());
        });
        return cardsByClient;
    }

    private boolean existsDniClient(EntityManager em, Integer dni) {

        String consultaCrear = "SELECT c FROM Client c WHERE c.dni = :dni";
        TypedQuery<Client> query = em.createQuery(consultaCrear, Client.class);
        query.setParameter("dni", dni);

        try {
            query.getSingleResult();
            return true;

        } catch (NoResultException e) {
            return false;
        }
    }

    private Client getClientById(EntityManager em, Long idCliente) {

        String getClient = "SELECT c FROM Client c WHERE c.id = :idClient";
        TypedQuery<Client> query = em.createQuery(getClient, Client.class);
        query.setParameter("idClient", idCliente);

        try {
            return query.getSingleResult();

        } catch (NoResultException e) {
            throw new EntityNotFoundException("Cliente con ID " + idCliente + " no encontrado.");

        } catch (NonUniqueResultException e) {
            throw new IllegalStateException("Se encontraron múltiples clientes con el mismo ID.");
        }
    }
}
