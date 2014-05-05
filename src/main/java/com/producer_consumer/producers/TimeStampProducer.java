package com.producer_consumer.producers;

import com.producer_consumer.exchange.ExchangeFacadeTimeStamp;
import com.producer_consumer.model.TimeStamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * User: Alexander Nazarenko
 */

public class TimeStampProducer implements Producer {
    private final static Logger LOGGER = LoggerFactory.getLogger(TimeStampProducer.class);
    private final ExchangeFacadeTimeStamp priorityBlockingQueue;


    public TimeStampProducer(final ExchangeFacadeTimeStamp priorityBlockingQueue) {
        this.priorityBlockingQueue = priorityBlockingQueue;
    }

    @Override
    public void run() {
        boolean interrupted = false;
        while (!interrupted) {
            final TimeStamp timeStamp = new TimeStamp();
            priorityBlockingQueue.offer(timeStamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                interrupted = true;
                LOGGER.warn(null, e);
            }
        }

    }

}
