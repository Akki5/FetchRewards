package com.example.fetchrewards.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    String payer;
    Integer points;
    Date timestamp;
    Integer pointsLeft;
}
