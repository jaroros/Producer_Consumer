package com.producer_consumer.comunicator;

import com.producer_consumer.logger.Logger;
import com.producer_consumer.model.TimeStamp;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

/**
 * User: Alexander Nazarenko
 */
@Singleton
@Named
public class PersistenceFacade implements Persistance {

    private Connector tProvider;
    private Logger tLogger;

    @Inject
    public PersistenceFacade(final Connector tProvider, final Logger logger) {
        this.tProvider = tProvider;
        this.tLogger = logger;
    }

    @Override
    // If exception was need return true
    public boolean save(final TimeStamp item) {
        boolean exceptionWas;
        try {
            tProvider.save(item);
            exceptionWas = false;
        } catch (Exception e) {
            tLogger.showConnectionFailed(e);
            exceptionWas = true;
        }
        return exceptionWas;
    }

    @Override
    public List<TimeStamp> getAll() {
        return tProvider.getAll();
    }
}
