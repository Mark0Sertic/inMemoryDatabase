package com.n26.database.transaction;

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
public class TransactionInMemoryDatabaseImplTest {

    @Autowired
    private TransactionInMemoryDatabase transactionInMemoryDatabase;

    @Before
    public void before(){
        this.transactionInMemoryDatabase.clear();
    }

    @Test
    public void addTransactionTest() throws NotSupportedTimeFormat, TransactionIsInFuture, TransactionOutOfInterval {
        Instant currentTimestamp = Instant.now();
        BigDecimal value = BigDecimal.valueOf(12.43);
        Transaction transaction = new Transaction(value, DateTimeFormatter.ISO_INSTANT.format(currentTimestamp));

        this.transactionInMemoryDatabase.addTransaction(transaction,currentTimestamp.toEpochMilli());

        assertTrue(this.transactionInMemoryDatabase.getTransactions(currentTimestamp.toEpochMilli()).size() == 1);


    }

    @Test
    public void getTransactionsTest() throws NotSupportedTimeFormat, TransactionIsInFuture, TransactionOutOfInterval {
        Instant currentTimestamp1 = Instant.now();
        Instant currentTimestamp2 = Instant.now().plusMillis(5000);
        BigDecimal value = BigDecimal.valueOf(12.43);

        Transaction transaction1 = new Transaction(value, DateTimeFormatter.ISO_INSTANT.format(currentTimestamp1));
        Transaction transaction2 = new Transaction(value, DateTimeFormatter.ISO_INSTANT.format(currentTimestamp2));

        this.transactionInMemoryDatabase.addTransaction(transaction1,currentTimestamp1.toEpochMilli() + 1000);
        this.transactionInMemoryDatabase.addTransaction(transaction2,currentTimestamp2.toEpochMilli() + 3000);


        assertTrue(this.transactionInMemoryDatabase.getTransactions(currentTimestamp2.toEpochMilli() + 3000).size() == 2);

    }

    @Test
    public void getEmptyListTransactionsTest() {
        Instant currentTimestamp = Instant.now();
        assertTrue(this.transactionInMemoryDatabase.getTransactions(currentTimestamp.toEpochMilli()).size() == 0);

    }
}
