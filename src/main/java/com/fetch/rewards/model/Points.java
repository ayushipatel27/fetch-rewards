package com.fetch.rewards.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Points {

    private String payer;
    private int points;

}
