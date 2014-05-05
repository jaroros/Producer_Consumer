package com.producer_consumer.exchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.producer_consumer.cache.TemporaryCache;
import com.producer_consumer.model.TimeStamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * User: Alexander Nazarenko
 */
@Singleton
@Named
public class ExchangeFacadeTimeStamp {
    private BlockingQueue<TimeStamp> blockingQueue;
    @Value("${linked.size}")
    private int blockingQueueSize;
    private final TemporaryCache temporaryCache;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final static Logger LOGGER = LoggerFactory.getLogger(TemporaryCache.class);
    private int objectsInCache = 0;
    private int objectsInCacheHead = 0;

    @Inject
    public ExchangeFacadeTimeStamp(final TemporaryCache temporaryCache) {
        this.temporaryCache = temporaryCache;
    }

    @PostConstruct
    private void postConstruct() {
        this.blockingQueue = new LinkedBlockingQueue<TimeStamp>(blockingQueueSize);
    }

    public void offer(final TimeStamp item) {
        if ((blockingQueueSize) == blockingQueue.size()) {
            try {
                saveToFile(item);
            } catch (JsonProcessingException e) {
                LOGGER.warn(null, e);
            }
        } else {
            while (objectsInCache > 0 && (blockingQueueSize) != blockingQueue.size()) {
                try {
                    getFromFile();
                } catch (IOException e) {
                    LOGGER.warn(null, e);
                }
                if (objectsInCache == 0) {
                    objectsInCacheHead = 0;
                }
            }
            blockingQueue.offer(item);
        }
    }

    private void getFromFile() throws IOException {
        objectsInCache--;
        blockingQueue.offer(objectMapper.readValue(temporaryCache.get(objectsInCacheHead), TimeStamp.class));
        objectsInCacheHead++;
    }

    private void saveToFile(final TimeStamp item) throws JsonProcessingException {
        final String content = objectMapper.writeValueAsString(item);
        temporaryCache.save(content, objectsInCache);
        objectsInCache++;
    }

    public TimeStamp peek() {
        return blockingQueue.peek();
    }

    public TimeStamp poll() {
        return blockingQueue.poll();
    }
}
