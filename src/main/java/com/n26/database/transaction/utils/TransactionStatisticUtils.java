package com.n26.database.transaction.utils;

import com.n26.database.TransactionStatisticUnit;
import com.n26.error.NotSupportedTimeFormat;
import com.n26.model.Transaction;

public class TransactionStatisticUtils {

    /**
     * Function for calculating transaction index in database
     * @param transaction
     * @param unitInterval
     * @param timeFrameInterval
     * @param currentTime
     * @return
     * @throws NotSupportedTimeFormat
     */
    public static int calculateIndex(Transaction transaction, int unitInterval, int timeFrameInterval, long currentTime) throws NotSupportedTimeFormat {
        long transactionTimestamp = transaction.getTimestampAsLong();

        int numberOfUnits = timeFrameInterval/unitInterval;
        long differenceInTime = currentTime - transactionTimestamp;
        long differenceDivideByUnit = differenceInTime / unitInterval;

        int result = (int)((differenceDivideByUnit) % (numberOfUnits));

        return result;
    }

    /**
     * Checking if transaction is valid
     * @param transaction
     * @param timeFrameInterval
     * @param currentTime
     * @return
     * @throws NotSupportedTimeFormat
     */
    public static boolean isTransactionValid(Transaction transaction, int timeFrameInterval, long currentTime) throws NotSupportedTimeFormat {
        long transactionTimestamp = transaction.getTimestampAsLong();
        boolean isValid = transactionTimestamp >= currentTime - timeFrameInterval;

        return isValid;
    }

    /**
     * Checking if transaction unit (database unit) is valid
     * @param transactionStatisticUnit
     * @param timeFrameInterval
     * @param currentTime
     * @return
     */
    public static boolean isTransactionValid(TransactionStatisticUnit transactionStatisticUnit, int timeFrameInterval, long currentTime) {
        long transactionTimestamp = transactionStatisticUnit.getTimestamp();
        boolean isValid = transactionTimestamp >= currentTime - timeFrameInterval;

        return isValid;
    }

    /**
     * Checking if transaction is in future
     * @param transaction
     * @param currentTime
     * @return
     * @throws NotSupportedTimeFormat
     */
    public static boolean isInFuture(Transaction transaction, long currentTime) throws NotSupportedTimeFormat {
        if(transaction.getTimestampAsLong() > currentTime) return true;
        return false;
    }
}
