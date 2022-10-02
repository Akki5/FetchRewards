package com.example.fetchrewards.service;

import com.example.fetchrewards.constants.ErrorCodes;
import com.example.fetchrewards.dao.FetchRewardsDao;
import com.example.fetchrewards.exception.FetchRewardsException;
import com.example.fetchrewards.model.*;
import com.example.fetchrewards.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FetchRewardsService {

    @Autowired
    private FetchRewardsDao dao;

    @PostConstruct
    public void init() {
    }

    public void addTxn(AddTxnRequest request) throws FetchRewardsException {
        // Validate request
        ValidationUtil.validateAddTxnRequest(request);
        log.info("Add transaction request for payer: " + request.getPayer()
                + ", points: " + request.getPoints() + ", date: " + request.getTimestamp());

        // Transform request object to transaction object to be stored
        Transaction newTxn = Transaction.builder()
                .payer(request.getPayer())
                .points(request.getPoints())
                .pointsLeft(request.getPoints())
                .timestamp(request.getTimestamp())
                .build();

        // adding transaction to in-memory data structure
        dao.addTxn(newTxn);
    }

    public SpendPointsResponse spendPoints(SpendPointsRequest request) throws FetchRewardsException {
        // Validating request
        ValidationUtil.validateSpendPointsRequest(request);
        log.info("Spend points request for points: " + request.getPoints());

        // initializing points to spend (keeps updating during computation), and points used per each transaction
        int pointsRemainingToSpend = request.getPoints(), pointsUsedForCurrTxn = 0;

        // preparing response map to collect points per payer to build final response
        Map<String, Integer> responseMap = new HashMap<>();
        for(Transaction currTxn : dao.getAllTxns()) {     // iterating on all transactions
            if(currTxn.getPointsLeft() == 0)      // transactions with 0 points left cannot be used further
                continue;

            // calculate points that should be used for current transaction
            pointsUsedForCurrTxn = Math.min(pointsRemainingToSpend, currTxn.getPointsLeft());
            // update response map for payer with actual points used for current transaction
            responseMap.put(currTxn.getPayer(), responseMap.getOrDefault(currTxn.getPayer(), 0) - pointsUsedForCurrTxn);
            currTxn.setPointsLeft(currTxn.getPointsLeft() - pointsUsedForCurrTxn);
            pointsRemainingToSpend -= pointsUsedForCurrTxn;
            if(pointsRemainingToSpend == 0)
                break;
        }

        // if total points in request cannot be spent, throw exception
        if(pointsRemainingToSpend > 0)
            throw new FetchRewardsException(ErrorCodes.INVALID_SPEND_POINTS, "Spend points are more than total available points.");

        // building final response in expected format
        List<PayerPoints> response = new ArrayList<>();
        for(String payer : responseMap.keySet()) {
            response.add(PayerPoints.builder()
                    .payer(payer)
                    .points(responseMap.get(payer))
                    .build());
        }
        return SpendPointsResponse.builder().pointsSpentPerPayer(response).build();
    }

    public BalanceResponse fetchBalance() {
        log.info("Fetch balance request called");
        Map<String, Integer> response = new HashMap<>();
        // iterate on all transaction and build response
        for(Transaction t : dao.getAllTxns()) {
            response.put(t.getPayer(), response.getOrDefault(t.getPayer(), 0) + t.getPointsLeft());
        }
        return BalanceResponse.builder().balance(response).build();
    }
}
