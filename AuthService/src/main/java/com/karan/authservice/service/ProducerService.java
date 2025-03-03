package com.karan.authservice.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.karan.authservice.Dto.UserInfoDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProducerService {


    // @Value(value = "${spring.kafka.topic.user-creation}")
    private final String topicName = "user-creation";
    private final KafkaTemplate<String, UserInfoDTO> producer;
    
    

    public void sendUserEvent(UserInfoDTO userInfoDTO){
        try{
            SendResult<String, UserInfoDTO> result = producer.send(
                topicName,
                userInfoDTO
            ).get();

            System.out.println("Sent message=[" + userInfoDTO + "] with offset=[" + result.getRecordMetadata().offset() + "]");
        }catch(Exception e){
            System.out.println(e);
        }
        // ProducerRecord<String , UserInfoDTO> record = new ProducerRecord<>(
        //     topicName,
        //     "USER_EVENT_CREATED" ,
        //     userInfoDTO
        // );

        // producer.send(record , (metadata , exception)->{
        //     if (exception != null) {
        //         System.err.println("Error sending message to Kafka: " + exception.getMessage());
        //     } else {
        //         System.out.println("Message sent to Kafka, offset: " + metadata.offset());
        //     }
        // });
    }



}
