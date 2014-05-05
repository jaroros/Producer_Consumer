package com.producer_consumer.cache;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: Alexander Nazarenko
 */
@Singleton
@Named
public class TemporaryCache {
    private final static Logger LOGGER = LoggerFactory.getLogger(TemporaryCache.class);

    public void save(String item, int numberOffile) {
        try {
            IOUtils.write(item, new FileOutputStream(new File("temp-" + numberOffile + ".dat")));
        } catch (IOException e) {
            LOGGER.warn(null, e);
        }
    }

    public String get(int numberOffile) {
        try {
            final File file = new File("temp-" + numberOffile + ".dat");
            final String content = IOUtils.toString(new FileInputStream(file));
            boolean isDeleted = file.delete();
            if (!isDeleted) {
                LOGGER.warn("file is not deleted" + numberOffile);
            }
            return content;
        } catch (IOException e) {
            LOGGER.warn(null, e);
        }
        return null;
    }
}
