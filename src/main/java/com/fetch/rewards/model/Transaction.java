package com.fetch.rewards.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String payer;

    private Integer points;

    private Date timestamp;

}
