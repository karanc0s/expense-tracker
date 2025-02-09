package com.karan.userservice.controller;

import org.springframework.kafka.annotation.KafkaListener;

import java.util.Set;

public class AuthServiceConsumer {

    @KafkaListener(topics = "T11TEST" , groupId = "event-test-group")
    public void listen(Set<String> event){
        System.out.println("Received event: " + event);
    }
}
