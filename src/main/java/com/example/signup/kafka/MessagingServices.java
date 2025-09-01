package com.example.signup.kafka;


import com.example.signup.dto.LoginNotificationDto;
import com.example.signup.dto.PasswordResetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagingServices {
    private final KafkaTemplate<String, PasswordResetRequest> resetPasswordNotification;
    private final KafkaTemplate<String, LoginNotificationDto> loginNotification;

    public MessagingServices(
            @Qualifier("passwordResetNotification") KafkaTemplate<String, PasswordResetRequest> resetPasswordNotification,
            @Qualifier("loginNotificationRequestBean") KafkaTemplate<String, LoginNotificationDto> loginNotification){
        this.resetPasswordNotification = resetPasswordNotification;
        this.loginNotification = loginNotification;
    }

    public void resetPasswordNotification(PasswordResetRequest request){
        resetPasswordNotification.send("forget-password",request);
    }

    public void loginNotification(LoginNotificationDto request){
        loginNotification.send("login-notification",request);
    }

}
