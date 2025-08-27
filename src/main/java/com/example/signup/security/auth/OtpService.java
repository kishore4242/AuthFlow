package com.example.signup.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpService {
    private static final SecureRandom random = new SecureRandom();
    private static final int TIME_OUT = 600;

    private final StringRedisTemplate redisTemplate;

    public static String generateOtp() {
        int otp = random.nextInt((int)Math.pow(10,6));
        return String.format("%06d",otp);
    }

    public void saveOtp(String email, String otp){
        redisTemplate.opsForValue().set(email, otp, Duration.ofSeconds(TIME_OUT));
//        redisTemplate.opsForValue().set(email, otp, Duration.ofSeconds(EmailUtils.OTP_EXPIRY));
    }

    public String getOtp(String email){
        return redisTemplate.opsForValue().get(email);
    }

    public void deleteOtp(String email) {
        redisTemplate.delete(email);
    }
}
