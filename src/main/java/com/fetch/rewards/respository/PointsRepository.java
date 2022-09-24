package com.fetch.rewards.respository;

import com.fetch.rewards.model.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PointsRepository {

    @PersistenceContext
    EntityManager entityManager;
    @Transactional
    public void addTransaction(Transaction transaction) {
        entityManager.persist(transaction);
    }

    public List<Transaction> getTransactions() {
        return entityManager.createQuery("SELECT t FROM Transaction t").getResultList();
    }
    @Transactional
    public void updateTransaction(Transaction transaction) {
        entityManager.createQuery("UPDATE Transaction SET points = ?0 WHERE id = ?1")
                .setParameter(0, transaction.getPoints())
                .setParameter(1, transaction.getId())
                .executeUpdate();
    }
}
