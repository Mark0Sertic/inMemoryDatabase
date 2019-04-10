package com.n26.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.n26.model.serializer.BigDecimalCustomSerializer;

import java.math.BigDecimal;

public class Statistics {

    @JsonSerialize(using = BigDecimalCustomSerializer.class)
    private BigDecimal sum;

    @JsonSerialize(using = BigDecimalCustomSerializer.class)
    private BigDecimal avg;

    @JsonSerialize(using = BigDecimalCustomSerializer.class)
    private BigDecimal max;

    @JsonSerialize(using = BigDecimalCustomSerializer.class)
    private BigDecimal min;

    private long count;

    public Statistics(){
        initialize();
    }

    public void initialize(){
        this.sum = BigDecimal.valueOf(0).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.avg = BigDecimal.valueOf(0).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.max = BigDecimal.valueOf(0).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.min = BigDecimal.valueOf(0).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.count = 0;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
