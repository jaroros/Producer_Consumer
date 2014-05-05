package com.producer_consumer;

import com.producer_consumer.cache.TemporaryCache;
import com.producer_consumer.exchange.ExchangeFacadeTimeStamp;
import com.producer_consumer.producers.TimeStampProducer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * User: Alexander Nazarenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class TimeStampProducerTest {
    private static final List<String> list = new ArrayList<String>();
    @Inject
    private ExchangeFacadeTimeStamp exchangeFacadeTimeStamp;

    private TimeStampProducer timeStampProducer;
    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Before
    public void prepare() {
        timeStampProducer = new TimeStampProducer(exchangeFacadeTimeStamp);
    }

    @Test
    public void testSuccessful() throws InterruptedException {
        executorService.submit(timeStampProducer);
        TimeUnit.SECONDS.sleep(5);
        Assert.assertTrue(list.size() == 4);
    }

    @Configuration
    @ImportResource("classpath:spring-test-properties.xml")
    static class ContextConfiguration {
        @Bean
        public TemporaryCache createCache() {
            TemporaryCache temporaryCache = new TemporaryCache() {
                public void save(String item, int numberOffile) {
                    list.add(item);
                }
            };
            return temporaryCache;
        }

        @Bean
        public ExchangeFacadeTimeStamp createExchange() {
            return new ExchangeFacadeTimeStamp(createCache());
        }


    }
}
