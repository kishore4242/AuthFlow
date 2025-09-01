package com.example.signup.kafka;


import com.example.signup.dto.LoginNotificationDto;
import com.example.signup.dto.PasswordResetRequest;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

//    ---------------------- Password reset notification ---------------------------------

    @Bean
    public ProducerFactory<String, PasswordResetRequest> passwordResetRequestProducerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MessageSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean(name = "passwordResetNotification")
    public KafkaTemplate<String, PasswordResetRequest> passwordResetNotification() {
        return new KafkaTemplate<>(passwordResetRequestProducerFactory());
    }

//    ---------------------- Login notification services -----------------------------------

    @Bean
    public ProducerFactory<String, LoginNotificationDto> loginNotificationProducerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, NotificationSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean(name = "loginNotificationRequestBean")
    public KafkaTemplate<String, LoginNotificationDto> loginNotification() {
        return new KafkaTemplate<>(loginNotificationProducerFactory());
    }
}
