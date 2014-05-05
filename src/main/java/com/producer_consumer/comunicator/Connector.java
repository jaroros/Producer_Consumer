package com.producer_consumer.comunicator;

import com.producer_consumer.model.TimeStamp;

import java.util.List;

/**
 * User: Alexander Nazarenko
 */
public interface Connector {

    public void save(TimeStamp item) throws Exception;

    public List<TimeStamp> getAll();
}
