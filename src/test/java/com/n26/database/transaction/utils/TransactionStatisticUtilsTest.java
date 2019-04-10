package com.n26.database.transaction.utils;

import com.n26.database.TransactionStatisticUnit;
import com.n26.database.transaction.TransactionStatisticUnitImpl;
import com.n26.error.NotSupportedTimeFormat;
import com.n26.model.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TransactionStatisticUtilsTest {

    @Test
    public void calculateIndexTest() throws NotSupportedTimeFormat {

        Instant currentTimestamp = Instant.now();
        Transaction transaction = new Transaction(BigDecimal.valueOf(12.43),DateTimeFormatter.ISO_INSTANT.format(currentTimestamp));
        int index = TransactionStatisticUtils.calculateIndex(transaction,1000,60000,currentTimestamp.toEpochMilli() + 1000);
        assertTrue(index == 1);
    }

    @Test
    public void isTransactionValidValidTest() throws NotSupportedTimeFormat {
        Instant currentTimestamp = Instant.now();
        Transaction transaction = new Transaction(BigDecimal.valueOf(12.43),DateTimeFormatter.ISO_INSTANT.format(currentTimestamp)) ;
        boolean valid = TransactionStatisticUtils.isTransactionValid(transaction, 60000, currentTimestamp.toEpochMilli());
        assertTrue(valid);
    }

    @Test
    public void isTransactionValidInvalidTest() throws NotSupportedTimeFormat {
        Instant currentTimestamp = Instant.now();
        Transaction transaction = new Transaction(BigDecimal.valueOf(12.43),DateTimeFormatter.ISO_INSTANT.format(currentTimestamp)) ;
        boolean valid = TransactionStatisticUtils.isTransactionValid(transaction, 60000, currentTimestamp.toEpochMilli() + 61000);
        assertFalse(valid);
    }

    @Test
    public void isInFutureTrueTest() throws NotSupportedTimeFormat {
        Instant currentTimestamp = Instant.now();
        Transaction transaction = new Transaction(BigDecimal.valueOf(12.43),DateTimeFormatter.ISO_INSTANT.format(currentTimestamp)) ;
        boolean isInFuture = TransactionStatisticUtils.isInFuture(transaction, currentTimestamp.toEpochMilli() - 1000);
        assertTrue(isInFuture);
    }

    @Test
    public void isInFutureFalseTest() throws NotSupportedTimeFormat {
        Instant currentTimestamp = Instant.now();
        Transaction transaction = new Transaction(BigDecimal.valueOf(12.43),DateTimeFormatter.ISO_INSTANT.format(currentTimestamp)) ;
        boolean isInFuture = TransactionStatisticUtils.isInFuture(transaction, currentTimestamp.toEpochMilli() + 1000);
        assertFalse(isInFuture);
    }

    @Test
    public void isTransactionValidValidTransactionStatistcUnitTest() throws NotSupportedTimeFormat {
        Instant currentTimestamp = Instant.now();
        Transaction transaction = new Transaction(BigDecimal.valueOf(12.43),DateTimeFormatter.ISO_INSTANT.format(currentTimestamp));
        TransactionStatisticUnit transactionStatisticUnit = new TransactionStatisticUnitImpl();
        transactionStatisticUnit.create(transaction);
        boolean valid = TransactionStatisticUtils.isTransactionValid(transactionStatisticUnit, 60000, currentTimestamp.toEpochMilli());
        assertTrue(valid);
    }

    @Test
    public void isTransactionValidInvalidTransactionStatisticUnitTest() throws NotSupportedTimeFormat {
        Instant currentTimestamp = Instant.now();
        Transaction transaction = new Transaction(BigDecimal.valueOf(12.43),DateTimeFormatter.ISO_INSTANT.format(currentTimestamp));
        TransactionStatisticUnit transactionStatisticUnit = new TransactionStatisticUnitImpl();
        transactionStatisticUnit.create(transaction);
        boolean valid = TransactionStatisticUtils.isTransactionValid(transactionStatisticUnit, 60000, currentTimestamp.toEpochMilli() + 61000);
        assertFalse(valid);
    }
}
