package com.producer_consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: Alexander Nazarenko
 */
public class Main {
    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {
        LOGGER.info("Started");
        final ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring.xml");
        final Context context = classPathXmlApplicationContext.getBean(Context.class);
        if (args.length > 0 && args[0].equals("-b")) {
            context.getAll();
        } else {
            context.start();
        }

    }
}
