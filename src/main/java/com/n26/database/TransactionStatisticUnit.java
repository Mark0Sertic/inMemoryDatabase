package com.n26.database;

import com.n26.error.NotSupportedTimeFormat;
import com.n26.model.Statistics;
import com.n26.model.Transaction;

import java.util.concurrent.locks.Lock;

public interface TransactionStatisticUnit {
     void create(Transaction transaction) throws NotSupportedTimeFormat;
     void updateStatistic(Statistics statistics);
     void combineTransaction(Transaction transaction);
     boolean isEmpty();
     void reset();
     Lock getWriteLock();
     Lock getReadLock();
     long getTimestamp();

}
