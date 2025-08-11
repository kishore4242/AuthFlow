package com.example.signup.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final StringRedisTemplate redisTemplate;
    private static final String BLACKLIST_PREFIX = "blacklist:";

    public void blacklistToken(String token, long expirationTime) {
        // Store the token in Redis with a key and set its expiration time.
        // The key is a unique identifier (e.g., "blacklist:token_hash").
        redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "true", expirationTime, TimeUnit.MILLISECONDS);
    }

    public boolean isBlacklisted(String token) {
        // Check if the token exists in the Redis blacklist.
        return redisTemplate.hasKey(BLACKLIST_PREFIX + token);
    }
}