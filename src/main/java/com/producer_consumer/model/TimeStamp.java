package com.producer_consumer.model;

import java.util.Date;

/**
 * User: Alexander Nazarenko
 */
public class TimeStamp {
    private final Date date = new Date();

    public Date getDate() {
        return (Date) date.clone();
    }

    @Override
    public String toString() {
        return "TimeStamp{" +
                "date=" + date.toString() +
                '}';
    }
}
