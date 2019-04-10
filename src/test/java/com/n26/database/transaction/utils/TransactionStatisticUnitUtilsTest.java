package com.n26.database.transaction.utils;

import com.n26.model.Statistics;
import com.n26.model.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertTrue;


public class TransactionStatisticUnitUtilsTest {
    private  TransactionStatisticUnitUtils transactionStatisticUnitUtils = new TransactionStatisticUnitUtils();

    @Test
    public void convertTransactionToStatisticsTest() {
        Instant currentTimestamp = Instant.now();
        BigDecimal value = BigDecimal.valueOf(12.43);
        Transaction transaction = new Transaction(value, DateTimeFormatter.ISO_INSTANT.format(currentTimestamp));
        Statistics statistic = new Statistics();
        transactionStatisticUnitUtils.convertTransactionToStatistics(transaction,statistic);

        assertTrue(statistic.getMax().compareTo(value) == 0);
        assertTrue(statistic.getMin().compareTo(value) == 0);
        assertTrue(statistic.getCount() == 1);
        assertTrue(statistic.getSum().compareTo(value) == 0);
        assertTrue(statistic.getAvg().compareTo(value) == 0);
    }

    @Test
    public void updateStatisticsTest() {
        Statistics statistic1 = new Statistics();
        Statistics statistic2 = new Statistics();

        BigDecimal value = BigDecimal.valueOf(12.43);

        statistic2.setMin(value);
        statistic2.setMax(value);
        statistic2.setSum(value);
        statistic2.setAvg(value);
        statistic2.setCount(1);

        transactionStatisticUnitUtils.updateStatistics(statistic1,statistic2);

        assertTrue(statistic1.getMax().compareTo(value) == 0);
        assertTrue(statistic1.getMin().compareTo(value) == 0);
        assertTrue(statistic1.getCount() == 1);
        assertTrue(statistic1.getSum().compareTo(value) == 0);
        assertTrue(statistic1.getAvg().compareTo(value) == 0);
    }

    @Test
    public void mergeStatisticsWithTransactionTest() {
        Instant currentTimestamp = Instant.now();
        BigDecimal value = BigDecimal.valueOf(12.43);
        Transaction transaction = new Transaction(value, DateTimeFormatter.ISO_INSTANT.format(currentTimestamp));
        Statistics statistic = new Statistics();
        transactionStatisticUnitUtils.mergeStatisticsWithTransaction(statistic,transaction);

        assertTrue(statistic.getMax().compareTo(value) == 0);
        assertTrue(statistic.getMin().compareTo(value) == 0);
        assertTrue(statistic.getCount() == 1);
        assertTrue(statistic.getSum().compareTo(value) == 0);
        assertTrue(statistic.getAvg().compareTo(value) == 0);
    }
}
