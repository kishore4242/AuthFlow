package com.example.signup.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic createTopic(){
        return new NewTopic("forget-password",3,(short)1);
    }

    @Bean
    public NewTopic createNotificationTopic(){
        return new NewTopic("login-notification",3,(short)1);
    }
}
