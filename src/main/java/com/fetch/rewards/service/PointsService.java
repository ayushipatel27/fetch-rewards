package com.fetch.rewards.service;

import com.fetch.rewards.model.Points;
import com.fetch.rewards.model.Transaction;
import com.fetch.rewards.respository.PointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class PointsService {

    @Autowired
    PointsRepository pointsRepository;

    public void addTransaction(Transaction transaction) {
        pointsRepository.addTransaction(transaction);
    }

    public List<Points> spendPoints(int spendingPoints) {
        List<Points> points = new ArrayList<>();
        Map<String, Integer> pointsMap = new HashMap<>();

        List<Transaction> transactions = pointsRepository.getTransactions().stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                .collect(Collectors.toList());
        Iterator<Transaction> iterator = transactions.iterator();

        while (spendingPoints > 0) {
            Transaction transaction = iterator.next();
            String payer = transaction.getPayer();
            int transactionPoints = transaction.getPoints();
            int spending = pointsMap.getOrDefault(payer, 0);

            if (spendingPoints >= transactionPoints) {
                pointsMap.put(payer, spending + transactionPoints);
                transaction.setPoints(0);
                spendingPoints -= transactionPoints;
            } else {
                pointsMap.put(payer, spending + spendingPoints);
                transaction.setPoints(transactionPoints - spendingPoints);
                spendingPoints = 0;
            }
            pointsRepository.updateTransaction(transaction);
        }

        for (String key : pointsMap.keySet()) {
            points.add(new Points(key, -1 * pointsMap.get(key)));
        }

        return points;
    }

    public Map<String, Integer> getBalance() {
        Map<String, Integer> balance = new HashMap<>();
        pointsRepository.getTransactions().forEach(transaction -> {
            String payer = transaction.getPayer();
            int points = balance.getOrDefault(payer, 0);
            balance.put(payer, points + transaction.getPoints());
        });

        return balance;
    }

    public int getTotalBalance() {
        AtomicInteger total = new AtomicInteger();
        pointsRepository.getTransactions().forEach(transaction -> {
            total.addAndGet(transaction.getPoints());
        });
        return total.get();
    }
}
