package com.karan.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Set;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }





    @KafkaListener(topics = "T11TEST" , groupId = "event-test-group")
    public void listen(Set<String> event){
        System.out.println("Received event: " + event);
    }
}
