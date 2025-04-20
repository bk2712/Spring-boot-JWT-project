package com.jwt_revision.Test_jwt_methods.service;

import com.jwt_revision.Test_jwt_methods.configuration.KafkaConstants;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger= LoggerFactory.getLogger(KafkaService.class);

//    1. topic name
//            2. message
    public boolean updateLocation(String location){
        this.kafkaTemplate.send(KafkaConstants.topicName, location);
        this.logger.info("Message produced");
        return true;

    }
}
