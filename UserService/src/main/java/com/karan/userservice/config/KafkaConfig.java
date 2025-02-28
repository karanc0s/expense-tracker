package com.karan.userservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.karan.userservice.Dto.UserInfoDTO;
import com.karan.userservice.deserializer.UserInfoDeserializer;

@Configuration
public class KafkaConfig {

    // @Value(value = "${spring.kafka.bootstrap-servers}")
    private final String bootstrapAddress = "localhost:9092";

    // @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId = "event-user-creation";

    @Bean
    public ConsumerFactory<String, UserInfoDTO> getConfigFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UserInfoDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean("ListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, UserInfoDTO> kafkaListenerContainerFactory(ConsumerFactory<String, UserInfoDTO> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, UserInfoDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}
