package com.n26.database.transaction;

import com.n26.database.TransactionStatisticUnit;
import com.n26.database.transaction.utils.TransactionStatisticUnitUtils;
import com.n26.error.NotSupportedTimeFormat;
import com.n26.model.Statistics;
import com.n26.model.Transaction;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TransactionStatisticUnitImpl implements TransactionStatisticUnit {
    private ReadWriteLock readWriteLock;

    private Statistics statistics;

    private long timestamp;

    private TransactionStatisticUnitUtils transactionStatisticUnitUtils = new TransactionStatisticUnitUtils();

    public TransactionStatisticUnitImpl() {
        this.readWriteLock = new ReentrantReadWriteLock();
        this.statistics = new Statistics();
    }

    /**
     * Creating new statics from transaction
     * @param transaction
     * @throws NotSupportedTimeFormat
     */
    @Override
    public void create(Transaction transaction) throws NotSupportedTimeFormat {
        transactionStatisticUnitUtils.convertTransactionToStatistics(transaction,this.statistics);
        this.timestamp = transaction.getTimestampAsLong();
    }

    /**
     * Update statistic with another one, retrieving result
     * @param result
     */
    @Override
    public void updateStatistic(Statistics result) {
        try {
            this.getReadLock().lock();
            transactionStatisticUnitUtils.updateStatistics(result,this.getStatistics());
        } finally {
            this.getReadLock().unlock();
        }
    }

    /**
     * Combing already existing statistic with new transaction
     * @param transaction
     */
    @Override
    public void combineTransaction(Transaction transaction) {
        try {
            getWriteLock().lock();
            transactionStatisticUnitUtils.mergeStatisticsWithTransaction(this.getStatistics(),transaction);
        } finally {
            getWriteLock().unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return this.statistics.getCount() == 0;
    }

    @Override
    public void reset() {
        this.statistics.initialize();
        this.timestamp = 0;
    }

    public ReadWriteLock getLock() {
        return this.readWriteLock;
    }

    @Override
    public Lock getReadLock() {
        return this.readWriteLock.readLock();
    }

    @Override
    public Lock getWriteLock() {
        return  this.readWriteLock.writeLock();
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
