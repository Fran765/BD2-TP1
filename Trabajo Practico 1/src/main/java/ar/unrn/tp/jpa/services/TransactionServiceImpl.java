package ar.unrn.tp.jpa.services;

import ar.unrn.tp.api.TransactionService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.function.Consumer;

public class TransactionServiceImpl implements TransactionService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-objectdb");

    @Override
    public void executeInTransaction(Consumer<EntityManager> action) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            action.accept(em);

            tx.commit();

        } catch (Exception e) {

            tx.rollback();
            throw e;

        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }
}