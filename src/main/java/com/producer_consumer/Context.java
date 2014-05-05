package com.producer_consumer;

import com.producer_consumer.comunicator.PersistenceFacade;
import com.producer_consumer.consumers.TimeStampConsumer;
import com.producer_consumer.exchange.ExchangeFacadeTimeStamp;
import com.producer_consumer.logger.Logger;
import com.producer_consumer.model.TimeStamp;
import com.producer_consumer.producers.TimeStampProducer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: Alexander Nazarenko
 */
@Singleton
@Named
public class Context {
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    @Inject
    private ExchangeFacadeTimeStamp exchangeFacadeTimeStamp;
    @Inject
    private PersistenceFacade persistenceFacade;
    @Inject
    private Logger logger;

    public void start() {
        executorService.submit(new TimeStampProducer(exchangeFacadeTimeStamp));
        executorService.submit(new TimeStampConsumer(exchangeFacadeTimeStamp, persistenceFacade));
    }

    public void getAll() {
        final List<TimeStamp> timeStamps = persistenceFacade.getAll();
        logger.printContent(timeStamps);
    }
}
