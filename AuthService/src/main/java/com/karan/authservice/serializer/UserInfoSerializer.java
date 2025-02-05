package com.karan.authservice.serializer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.karan.authservice.entities.UserInfo;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class UserInfoSerializer implements Serializer<UserInfo> {

    @Override
    public byte[] serialize(String s, UserInfo userInfo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if(userInfo == null){
                throw new SerializationException("userInfo is null");
            }
            return objectMapper.writeValueAsBytes(userInfo);
        }catch (Exception e){
            throw new SerializationException("Error when serializing MessageDto to byte[]");
        }
    }


}
