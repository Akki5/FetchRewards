package com.example.fetchrewards.controller;

import com.example.fetchrewards.constants.Constants;
import com.example.fetchrewards.exception.FetchRewardsException;
import com.example.fetchrewards.model.AddTxnRequest;
import com.example.fetchrewards.model.BalanceResponse;
import com.example.fetchrewards.model.SpendPointsRequest;
import com.example.fetchrewards.model.SpendPointsResponse;
import com.example.fetchrewards.service.FetchRewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FetchRewardsController {

    @Autowired
    private FetchRewardsService service;

    @PostMapping("/add-txn")
    public ResponseEntity addTxn(@RequestBody AddTxnRequest request) throws FetchRewardsException {
        service.addTxn(request);
        return ResponseEntity.ok(Constants.ADD_TXN_SUCCESS);
    }

    @PostMapping("/spend")
    public ResponseEntity spend(@RequestBody SpendPointsRequest request) throws FetchRewardsException {
        return ResponseEntity.ok(service.spendPoints(request));
    }

    @GetMapping("/balance")
    public ResponseEntity balance() {
        return ResponseEntity.ok(service.fetchBalance());
    }
}
