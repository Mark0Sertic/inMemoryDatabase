package com.n26.database;

import com.n26.error.NotSupportedTimeFormat;
import com.n26.error.TransactionIsInFuture;
import com.n26.error.TransactionOutOfInterval;
import com.n26.model.Transaction;

import java.util.List;

public interface TransactionInMemoryDatabase {

    void addTransaction(Transaction transaction, long timestamp) throws TransactionIsInFuture, TransactionOutOfInterval, NotSupportedTimeFormat;

    void clear();

    List<TransactionStatisticUnit> getTransactions(long currentTimestamp);
}
