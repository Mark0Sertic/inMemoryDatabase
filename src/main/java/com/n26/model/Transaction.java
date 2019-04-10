package com.n26.model;

import com.n26.error.NotSupportedTimeFormat;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Transaction {
    private BigDecimal amount;

    private String timestamp;

    public Transaction(){}

    public Transaction(BigDecimal amount, String timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public long getTimestampAsLong() throws NotSupportedTimeFormat {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        String[] permissFormats = new String[]{"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'","yyyy-MM-dd'T'HH:mm:ss'Z'"};

        for (int i = 0; i < permissFormats.length; i++)
        {
            try
            {
                DateFormat df = new SimpleDateFormat(permissFormats[i]);
                df.setTimeZone(tz);
                return df.parse(this.timestamp).getTime();
            }
            catch(ParseException e)
            {
                e.printStackTrace();
            }
        }

        throw new NotSupportedTimeFormat();

    };

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestampFromLong(long timestamp) {
        Date date = new Date(timestamp);
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        df.setTimeZone(tz);
        this.timestamp = df.format(date);
    }
}
