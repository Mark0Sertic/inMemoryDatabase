package com.n26.database.transaction.utils;

import com.n26.model.Statistics;
import com.n26.model.Transaction;

import java.math.BigDecimal;

public class TransactionStatisticUnitUtils {

    public TransactionStatisticUnitUtils() {}

    public void convertTransactionToStatistics(Transaction transaction, Statistics statistics) {
        statistics.setAvg(transaction.getAmount());
        statistics.setCount(1);
        statistics.setMax(transaction.getAmount());
        statistics.setMin(transaction.getAmount());
        statistics.setSum(transaction.getAmount());
    }

    public void updateStatistics(Statistics result, Statistics statistics) {
        result.setSum(result.getSum().add(statistics.getSum()));
        result.setCount(result.getCount() + statistics.getCount());
        result.setAvg(result.getSum().divide(BigDecimal.valueOf(result.getCount()),2, BigDecimal.ROUND_HALF_UP));

        if (isMin(result.getMin(),statistics.getMin())){
            result.setMin(statistics.getMin());
        }

        if(result.getMin().compareTo(BigDecimal.valueOf(0).setScale(2, BigDecimal.ROUND_HALF_UP)) == 0) result.setMin(statistics.getMin());

        if (isMax(result.getMax(),statistics.getMax())){
            result.setMax(statistics.getMax());
        }

        if(result.getMax().compareTo(BigDecimal.valueOf(0).setScale(2, BigDecimal.ROUND_HALF_UP)) == 0) result.setMax(statistics.getMax());

    }

    public void mergeStatisticsWithTransaction(Statistics statistics, Transaction transaction) {
        statistics.setSum(statistics.getSum().add(transaction.getAmount()));
        statistics.setCount(statistics.getCount() + 1);
        statistics.setAvg(statistics.getSum().divide(BigDecimal.valueOf(statistics.getCount()),2, BigDecimal.ROUND_HALF_UP));


        if (isMin(statistics.getMin(),transaction.getAmount())){
            statistics.setMin(transaction.getAmount());
        }

        if(statistics.getMin().compareTo(BigDecimal.valueOf(0).setScale(2, BigDecimal.ROUND_HALF_UP)) == 0) statistics.setMin(transaction.getAmount());

        if (isMax(statistics.getMax(),transaction.getAmount())){
            statistics.setMax(transaction.getAmount());
        }

        if(statistics.getMax().compareTo(BigDecimal.valueOf(0).setScale(2, BigDecimal.ROUND_HALF_UP)) == 0) statistics.setMax(transaction.getAmount());

    }

    private boolean isMin(BigDecimal a, BigDecimal b) {
        if(a.compareTo(b) == 1) return true;
        return false;
    }

    private  boolean isMax(BigDecimal a, BigDecimal b) {
        if(a.compareTo(b) == -1) return true;
        return false;
    }
}
