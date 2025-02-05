package com.karan.userservice.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karan.userservice.Dto.UserInfoDTO;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Arrays;

public class UserInfoDeserializer implements Deserializer<UserInfoDTO> {

    @Override
    public UserInfoDTO deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        UserInfoDTO result = null;
        try{
            if(data == null){
                throw new RuntimeException("data is null");
            }
            System.out.println("Deserializing..." + Arrays.toString(data));
            result = mapper.readValue(new String(data, "UTF-8"), UserInfoDTO.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return result;
    }
}
