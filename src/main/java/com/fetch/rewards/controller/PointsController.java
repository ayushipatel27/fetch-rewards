package com.fetch.rewards.controller;

import com.fetch.rewards.model.Balance;
import com.fetch.rewards.model.Points;
import com.fetch.rewards.model.Transaction;
import com.fetch.rewards.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PointsController {

    @Autowired
    PointsService pointsService;

    @PostMapping("/transaction/add")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        try {
            pointsService.addTransaction(transaction);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping("/points/spend")
    public ResponseEntity<List<Points>> spendPoints(@RequestBody Balance balance) {
        try {
            if (balance.getPoints() <= 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // cannot spend negative amount
            } else if (balance.getPoints() > pointsService.getTotalBalance()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // cannot have negative balance
            } else {
                List<Points> points = pointsService.spendPoints(balance.getPoints());
                return new ResponseEntity<>(points, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/points/balance")
    public ResponseEntity<Map<String, Integer>> getBalance() {
        try {
            Map<String, Integer> balance = pointsService.getBalance();
            return new ResponseEntity<>(balance, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
