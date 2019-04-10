package com.n26.service;

import com.n26.Application;
import com.n26.database.TransactionInMemoryDatabase;
import com.n26.error.NotSupportedTimeFormat;
import com.n26.error.TransactionIsInFuture;
import com.n26.error.TransactionOutOfInterval;
import com.n26.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TransactionServiceTest {

    @Autowired
    private TransactionInMemoryDatabase transactionInMemoryDatabase;

    @Autowired
    private TransactionService transactionService;

    @Before
    public void before(){
        this.transactionInMemoryDatabase.clear();
    }

    @Test
    public void addTransactionTest() throws NotSupportedTimeFormat, TransactionIsInFuture, TransactionOutOfInterval {
        Instant currentTimestamp = Instant.now().minusMillis(2000);
        Transaction transaction = new Transaction(BigDecimal.valueOf(12.43), DateTimeFormatter.ISO_INSTANT.format(currentTimestamp));
        transactionService.addTransaction(transaction);

        assertTrue(this.transactionInMemoryDatabase.getTransactions(Instant.now().toEpochMilli()).size() == 1);

    }

    @Test
    public void deleteTransactionTest() throws NotSupportedTimeFormat, TransactionIsInFuture, TransactionOutOfInterval {
        Instant currentTimestamp = Instant.now().minusMillis(2000);
        Transaction transaction = new Transaction(BigDecimal.valueOf(12.43), DateTimeFormatter.ISO_INSTANT.format(currentTimestamp));

        transactionService.addTransaction(transaction);
        transactionService.deleteTransactions();

        assertTrue(this.transactionInMemoryDatabase.getTransactions(Instant.now().toEpochMilli()).size() == 0);
    }

}
