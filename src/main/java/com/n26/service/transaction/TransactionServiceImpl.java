package com.n26.service.transaction;


import com.n26.database.TransactionInMemoryDatabase;
import com.n26.error.NotSupportedTimeFormat;
import com.n26.error.TransactionIsInFuture;
import com.n26.error.TransactionOutOfInterval;
import com.n26.model.Transaction;
import com.n26.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionInMemoryDatabase transactionInMemoryDatabase;

    @Autowired
    public TransactionServiceImpl(TransactionInMemoryDatabase transactionInMemoryDatabase) {
        this.transactionInMemoryDatabase = transactionInMemoryDatabase;
    }

    @Override
    public void addTransaction(Transaction transaction) throws TransactionIsInFuture, TransactionOutOfInterval, NotSupportedTimeFormat {
        long currentTimestamp = Instant.now().toEpochMilli();
        this.transactionInMemoryDatabase.addTransaction(transaction,currentTimestamp);
    }

    @Override
    public void deleteTransactions() {
        this.transactionInMemoryDatabase.clear();
    }
}
