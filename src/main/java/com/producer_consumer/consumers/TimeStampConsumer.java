package com.producer_consumer.consumers;

import com.producer_consumer.comunicator.PersistenceFacade;
import com.producer_consumer.exchange.ExchangeFacadeTimeStamp;
import com.producer_consumer.model.TimeStamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * User: Alexander Nazarenko
 */

public class TimeStampConsumer implements Consumer {
    private final ExchangeFacadeTimeStamp blockingQueue;
    private final static Logger LOGGER = LoggerFactory.getLogger(TimeStampConsumer.class);
    private final PersistenceFacade persistenceFacade;


    public TimeStampConsumer(final ExchangeFacadeTimeStamp blockingQueue, final PersistenceFacade persistenceFacade) {
        this.blockingQueue = blockingQueue;
        this.persistenceFacade = persistenceFacade;
    }

    @Override
    public void run() {
        boolean interrupted = false;
        while (!interrupted) {
            final TimeStamp timeStamp = blockingQueue.peek();
            if (timeStamp == null) {
                continue;
            }
            boolean wasException = persistenceFacade.save(timeStamp);
            if (wasException) {
                while (interrupted || wasException) {
                    wasException = persistenceFacade.save(timeStamp);
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        interrupted = false;
                    } catch (InterruptedException e) {
                        LOGGER.warn(null, e);
                        interrupted = true;
                    }
                }
                blockingQueue.poll();
            } else {
                blockingQueue.poll();
            }
            interrupted = Thread.currentThread().isInterrupted();
        }
    }
}
