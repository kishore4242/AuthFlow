package com.example.signup.kafka;

import com.example.signup.dto.PasswordResetRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

@Component
public class MessageSerializer implements Serializer<PasswordResetRequest> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String s, PasswordResetRequest passwordResetRequest) {
        try{
            return objectMapper.writeValueAsBytes(passwordResetRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
