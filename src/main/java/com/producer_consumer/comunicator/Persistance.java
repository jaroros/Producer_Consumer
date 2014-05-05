package com.producer_consumer.comunicator;

import com.producer_consumer.model.TimeStamp;

import java.util.List;

/**
 * User: Alexander Nazarenko
 */
public interface Persistance {

    public boolean save(TimeStamp item);

    public List<TimeStamp> getAll();
}
