package com.example.signup.kafka;

import com.example.signup.dto.LoginNotificationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class NotificationSerializer implements Serializer<LoginNotificationDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String s, LoginNotificationDto loginNotificationDto) {
        try{
            return objectMapper.writeValueAsBytes(loginNotificationDto);
        }
        catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
