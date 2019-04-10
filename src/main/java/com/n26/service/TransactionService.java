package com.n26.service;

import com.n26.error.NotSupportedTimeFormat;
import com.n26.error.TransactionIsInFuture;
import com.n26.error.TransactionOutOfInterval;
import com.n26.model.Transaction;

public interface TransactionService {
    void addTransaction(Transaction transaction) throws TransactionIsInFuture, TransactionOutOfInterval, NotSupportedTimeFormat;
    void deleteTransactions();
}
