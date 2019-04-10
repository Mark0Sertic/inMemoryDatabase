package com.n26.controller;

import com.n26.model.Statistics;
import com.n26.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StatisticsController {

    private final StatisticService statisticService;

    @Autowired
    public StatisticsController (StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    /**
     * REST point for retrieving transaction statistics
     * @return
     */
    @GetMapping("/statistics")
    public Statistics getStatistics(){
        return statisticService.generateStatistic();
    }
}
