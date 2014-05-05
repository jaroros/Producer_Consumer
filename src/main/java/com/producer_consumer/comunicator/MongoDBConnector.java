package com.producer_consumer.comunicator;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.producer_consumer.model.TimeStamp;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import java.net.UnknownHostException;
import java.util.List;

/**
 * User: Alexander Nazarenko
 */
@Singleton
@Named
public class MongoDBConnector implements Connector {
    private final static Logger LOGGER = LoggerFactory.getLogger(MongoDBConnector.class);
    private MongoCollection mongoCollection;
    @Value("${mongo.timeout}")
    private int mongoDbConnectionTimeOut;


    @PostConstruct
    private void postConstruct(){
        final MongoClientOptions mongoOptions = new MongoClientOptions.Builder().connectTimeout(mongoDbConnectionTimeOut).build();
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(ServerAddress.defaultHost(), mongoOptions);
            mongoClient.setWriteConcern(WriteConcern.FSYNCED);
        } catch (UnknownHostException e) {
            LOGGER.warn(null, e);
        } catch (NullPointerException e) {
            LOGGER.warn(null, e);
        }

        Jongo jongo = new Jongo(mongoClient.getDB("timestamps"));
        mongoCollection = jongo.getCollection("timestamps");
    }


    @Override
    public void save(final TimeStamp item) {
        mongoCollection.save(item);
    }

    @Override
    public List<TimeStamp> getAll() {
        return Lists.newArrayList(mongoCollection.find().as(TimeStamp.class));
    }


}
