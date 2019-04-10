package com.n26.database.transaction;

import com.n26.error.NotSupportedTimeFormat;
import com.n26.model.Statistics;
import com.n26.model.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertTrue;

public class TransactionStatisticUnitImplTest {
    private TransactionStatisticUnitImpl transactionStatisticUnitImplTest = new TransactionStatisticUnitImpl();

    @Test
    public void createTest() throws NotSupportedTimeFormat {
        Instant currentTimestamp = Instant.now();
        BigDecimal value = BigDecimal.valueOf(12.43);
        Transaction transaction = new Transaction(value, DateTimeFormatter.ISO_INSTANT.format(currentTimestamp));
        this.transactionStatisticUnitImplTest.create(transaction);
        Statistics statistic = this.transactionStatisticUnitImplTest.getStatistics() ;

        assertTrue(statistic.getMax().compareTo(value) == 0);
        assertTrue(statistic.getMin().compareTo(value) == 0);
        assertTrue(statistic.getCount() == 1);
        assertTrue(statistic.getSum().compareTo(value) == 0);
        assertTrue(statistic.getAvg().compareTo(value) == 0);
        assertTrue(this.transactionStatisticUnitImplTest.getTimestamp() == currentTimestamp.toEpochMilli());

        this.transactionStatisticUnitImplTest.reset();
    }

    @Test
    public void updateStatisticTest() throws NotSupportedTimeFormat {
        Statistics statistic = new Statistics();

        Instant currentTimestamp = Instant.now();
        BigDecimal value = BigDecimal.valueOf(12.43);
        Transaction transaction = new Transaction(value, DateTimeFormatter.ISO_INSTANT.format(currentTimestamp));
        this.transactionStatisticUnitImplTest.create(transaction);

        this.transactionStatisticUnitImplTest.updateStatistic(statistic);

        assertTrue(statistic.getMax().compareTo(value) == 0);
        assertTrue(statistic.getMin().compareTo(value) == 0);
        assertTrue(statistic.getCount() == 1);
        assertTrue(statistic.getSum().compareTo(value) == 0);
        assertTrue(statistic.getAvg().compareTo(value) == 0);

        this.transactionStatisticUnitImplTest.reset();
    }

    @Test
    public void combineTransactionTest() {
        Instant currentTimestamp = Instant.now();
        BigDecimal value = BigDecimal.valueOf(12.43);
        Transaction transaction = new Transaction(value, DateTimeFormatter.ISO_INSTANT.format(currentTimestamp));

        this.transactionStatisticUnitImplTest.combineTransaction(transaction);

        Statistics statistic = this.transactionStatisticUnitImplTest.getStatistics();

        assertTrue(statistic.getMax().compareTo(value) == 0);
        assertTrue(statistic.getMin().compareTo(value) == 0);
        assertTrue(statistic.getCount() == 1);
        assertTrue(statistic.getSum().compareTo(value) == 0);
        assertTrue(statistic.getAvg().compareTo(value) == 0);

        this.transactionStatisticUnitImplTest.reset();


    }

    @Test
    public void isEmptyTest() {
        this.transactionStatisticUnitImplTest.reset();
        assertTrue(this.transactionStatisticUnitImplTest.isEmpty());
    }

    @Test
    public void resetTest() {
        this.transactionStatisticUnitImplTest.reset();
        assertTrue(this.transactionStatisticUnitImplTest.isEmpty());
    }

    @Test
    public void getLockTest() {
        assertTrue(this.transactionStatisticUnitImplTest.getLock() != null);
    }

    @Test
    public void getReadLockTest() {
        assertTrue(this.transactionStatisticUnitImplTest.getReadLock() != null);
    }

    @Test
    public void getWriteLockTest() {
        assertTrue(this.transactionStatisticUnitImplTest.getWriteLock() != null);
    }

    @Test
    public void getStatisticsTest() {
        assertTrue(this.transactionStatisticUnitImplTest.getStatistics() != null);
    }

}
