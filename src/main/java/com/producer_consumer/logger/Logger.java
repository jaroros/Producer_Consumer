package com.producer_consumer.logger;

import com.producer_consumer.model.TimeStamp;

import java.util.List;

/**
 * User: Alexander Nazarenko
 */
public interface Logger {

    public void showConnectionFailed(Exception e);

    public void printContent(final List<TimeStamp> content);
}
