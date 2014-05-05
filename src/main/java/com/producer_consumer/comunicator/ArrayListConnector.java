package com.producer_consumer.comunicator;

import com.google.common.collect.Lists;
import com.producer_consumer.model.TimeStamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * User: Alexander Nazarenko
 */
public class ArrayListConnector implements Connector {
    private List<TimeStamp> list = new ArrayList<TimeStamp>();
    private final static Logger LOGGER = LoggerFactory.getLogger(ArrayListConnector.class);

    @Override
    public void save(final TimeStamp item) throws Exception {
        list.add(item);
        if (list.size() % 10 == 0) {
            System.out.println(list.size());
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                LOGGER.warn(null, e);
            }
        }

        if (list.size() % 20 == 0) {
            throw new Exception("Database failed");
        }
    }

    @Override
    public List<TimeStamp> getAll() {
        return Lists.newArrayList(list);
    }
}
