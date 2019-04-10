package com.n26.database.transaction;

import com.n26.database.TransactionInMemoryDatabase;
import com.n26.database.TransactionStatisticUnit;
import com.n26.database.transaction.utils.TransactionStatisticUtils;
import com.n26.error.NotSupportedTimeFormat;
import com.n26.error.TransactionIsInFuture;
import com.n26.error.TransactionOutOfInterval;
import com.n26.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionInMemoryDatabaseImpl implements TransactionInMemoryDatabase {

    private TransactionStatisticUnit[] transactionStatisticUnit;

    @Value("${time.frame.unit.interval}")
    private int unitInterval;

    @Value("${time.frame.duration}")
    private int timeFrameInterval;

    private TransactionInMemoryDatabaseImpl() {};

    @PostConstruct
    private void TransactionInMemoryDatabaseImplPostConstruct(){
        validateIntervals(unitInterval,timeFrameInterval);
        this.transactionStatisticUnit = new TransactionStatisticUnitImpl[timeFrameInterval / unitInterval];

        initDatabase();


    }

    private void validateIntervals(long unitInterval, long timeFrameInterval) {
        if (unitInterval <= 0 || timeFrameInterval <= 0) throw new IllegalArgumentException("Invalid intervals in properties (Intervals cannot be less or equals to 0)");
        if(unitInterval > timeFrameInterval) throw new IllegalArgumentException("Unit Interval cannot be greater than 0");
    }

    private void initDatabase() {
        for(int transactionStatisticUnitNo = 0; transactionStatisticUnitNo < transactionStatisticUnit.length; transactionStatisticUnitNo++) {
            this.transactionStatisticUnit [transactionStatisticUnitNo] = new TransactionStatisticUnitImpl();
        }
    }

    /**
     * Starting point of adding to database logic, if transaction validation is passed code continues add
     * adds transaction to database
     * @param transaction
     * @param currentTime
     * @throws TransactionIsInFuture
     * @throws TransactionOutOfInterval
     * @throws NotSupportedTimeFormat
     */
    @Override
    public void addTransaction(Transaction transaction, long currentTime) throws TransactionIsInFuture, TransactionOutOfInterval, NotSupportedTimeFormat {
        if(TransactionStatisticUtils.isInFuture(transaction,currentTime)) throw new TransactionIsInFuture();
        else if(!TransactionStatisticUtils.isTransactionValid(transaction,timeFrameInterval,currentTime)) throw new TransactionOutOfInterval();
        else addToDatabase(transaction, currentTime);

    }

    /**
     * Calculating transaction key getting locks and adding transaction to alreaday existing one
     * Creating new if transaction is first on given index
     * Or invlaidating old and creating new if old is out of date
     * @param transaction
     * @param currentTime
     * @throws NotSupportedTimeFormat
     */
    private void addToDatabase(Transaction transaction, long currentTime) throws NotSupportedTimeFormat {
        int key = TransactionStatisticUtils.calculateIndex(transaction,unitInterval,timeFrameInterval,currentTime);
        TransactionStatisticUnit transactionStatisticUnit = this.transactionStatisticUnit[key];

        try {
            transactionStatisticUnit.getWriteLock().lock();
            if(transactionStatisticUnit.isEmpty()) {
                transactionStatisticUnit.create(transaction);
            }else {
                if (TransactionStatisticUtils.isTransactionValid(transactionStatisticUnit,this.timeFrameInterval, currentTime)) {
                    transactionStatisticUnit.combineTransaction(transaction);
                } else {
                    transactionStatisticUnit.reset();
                    transactionStatisticUnit.create(transaction);
                }
            }
        } finally {
            transactionStatisticUnit.getWriteLock().unlock();
        }
    }

    /**
     * Clearing whole database
     */
    @Override
    public void clear() {
        initDatabase();
    }


    /**
     * Getting all transactions from database, by timestamp
     * @param currentTimestamp
     * @return
     */
    @Override
    public List<TransactionStatisticUnit> getTransactions(long currentTimestamp) {
        List<TransactionStatisticUnit> transactionStatisticUnitList = new ArrayList<TransactionStatisticUnit>();

        for(int i = 0; i < this.transactionStatisticUnit.length; i++) {
            if(TransactionStatisticUtils.isTransactionValid(this.transactionStatisticUnit[i],timeFrameInterval,currentTimestamp))
                transactionStatisticUnitList.add(this.transactionStatisticUnit[i]);
        }

        return transactionStatisticUnitList;

    }
}
