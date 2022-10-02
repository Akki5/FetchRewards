package com.example.fetchrewards.dao;

import com.example.fetchrewards.model.Transaction;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class FetchRewardsDao {

    // In-memory data structure to store all transactions
    private List<Transaction> txnList;

    @PostConstruct
    public void init() {
        txnList = new ArrayList<>();
    }

    public List<Transaction> getAllTxns() {
        return txnList;
    }

    public void addTxn(Transaction newTxn) {
        // adding new transaction in sorted order based on dat/timestamp
        for(int i = 0; i < txnList.size(); i++) {
            Transaction t = txnList.get(i);
            if(newTxn.getTimestamp().compareTo(t.getTimestamp()) < 0) {     // finding target index to put new transaction
                txnList.add(i, newTxn);
                return;
            }
        }
        txnList.add(newTxn);    // if target index was not found, new transaction is added at last
    }
}
