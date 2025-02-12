package com.karan.userservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

//    public Map<String , Object> getProp(){
//        Map<String , Object> prop = new HashMap<>();
//
//        prop.put(
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG ,
//                com.karan.userservice.deserializer.UserInfoDeserializer.class
//        );
//
//        return prop;
//    }
}
