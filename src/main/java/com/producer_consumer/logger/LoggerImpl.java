package com.producer_consumer.logger;

import com.producer_consumer.model.TimeStamp;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

/**
 * User: Alexander Nazarenko
 */
@Singleton
@Named
public class LoggerImpl implements Logger {
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LoggerImpl.class);
    @Override
    public void showConnectionFailed(final Exception e) {
        LOGGER.warn("Connection failed");
    }

    @Override
    public void printContent(final List<TimeStamp> content) {
        LOGGER.info(Arrays.toString(content.toArray(new TimeStamp[0])));
    }
}
