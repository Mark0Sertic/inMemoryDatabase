package com.n26.service.statistics;

import com.n26.database.TransactionInMemoryDatabase;
import com.n26.database.TransactionStatisticUnit;
import com.n26.model.Statistics;
import com.n26.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticService {

    private final TransactionInMemoryDatabase transactionInMemoryDatabase;

    @Autowired
    public StatisticsServiceImpl(TransactionInMemoryDatabase transactionInMemoryDatabase) {
        this.transactionInMemoryDatabase = transactionInMemoryDatabase;
    }

    @Override
    public Statistics generateStatistic() {
        long currentTimestamp = Instant.now().toEpochMilli();
        Statistics statistics = new Statistics();

        List<TransactionStatisticUnit> transactionStatisticUnitList = this.transactionInMemoryDatabase.getTransactions(currentTimestamp);

        transactionStatisticUnitList.forEach(tsu -> tsu.updateStatistic(statistics));

        return statistics;
    }
}
