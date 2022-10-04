package com.example.fetchrewards.controller;

import com.example.fetchrewards.constants.Constants;
import com.example.fetchrewards.exception.FetchRewardsException;
import com.example.fetchrewards.model.AddTxnRequest;
import com.example.fetchrewards.model.SpendPointsRequest;
import com.example.fetchrewards.service.FetchRewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FetchRewardsController {

    @Autowired
    private FetchRewardsService service;

    /**
     * Adds a new transaction.
     * @param request - $payer, $points, $timestamp
     * @return - Returns a string indicating successful addition os the transaction
     * @throws FetchRewardsException
     */
    @PostMapping("/add-txn")
    public ResponseEntity addTxn(@RequestBody AddTxnRequest request) throws FetchRewardsException {
        service.addTxn(request);
        return ResponseEntity.ok(Constants.ADD_TXN_SUCCESS);
    }

    /**
     *
     * @param request - $points
     * @return - list of payer and points used
     * @throws FetchRewardsException
     */
    @PostMapping("/spend")
    public ResponseEntity spend(@RequestBody SpendPointsRequest request) throws FetchRewardsException {
        return ResponseEntity.ok(service.spendPoints(request));
    }

    /**
     * @return - map of all payers and their points left
     */
    @GetMapping("/balance")
    public ResponseEntity balance() {
        return ResponseEntity.ok(service.fetchBalance());
    }
}
