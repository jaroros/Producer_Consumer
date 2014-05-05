package com.producer_consumer;

import com.producer_consumer.cache.TemporaryCache;
import com.producer_consumer.exchange.ExchangeFacadeTimeStamp;
import com.producer_consumer.model.TimeStamp;
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

/**
 * User: Alexander Nazarenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ExchangeFacedTest {
    private static final List<String> list = new ArrayList<String>();
    @Inject
    private ExchangeFacadeTimeStamp exchangeFacadeTimeStamp;

    @Before
    public void beforeTest() {
        list.clear();
    }

    @Test
    public void testSuccessful() {
        exchangeFacadeTimeStamp.offer(new TimeStamp());
        exchangeFacadeTimeStamp.offer(new TimeStamp());
        exchangeFacadeTimeStamp.offer(new TimeStamp());
        exchangeFacadeTimeStamp.offer(new TimeStamp());
        exchangeFacadeTimeStamp.offer(new TimeStamp());

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
