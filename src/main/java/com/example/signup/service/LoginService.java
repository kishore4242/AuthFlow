package com.example.signup.service;

import com.example.signup.dto.EmailDto;
import com.example.signup.dto.PasswordResetRequest;
import com.example.signup.repository.SignupRepo;
import com.example.signup.security.auth.JwtService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final SignupRepo signupRepo;
    private final KafkaTemplate<String, PasswordResetRequest> kafkaTemplate;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtService jwtService;

    public ResponseEntity<?> login(String email, String password) {
        log.debug("Attempt login for user: {}", email);
        String jwtToken = null;
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String userDetails = email + ":" + auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
            if(auth.isAuthenticated()){
                UserDetails userDetails1 = myUserDetailsService.loadUserByUsername(email);

                jwtToken = jwtService.generateToken(userDetails1);
                return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,"Bearer "+jwtToken).body("Jwt created Done");

            }

            return ResponseEntity.ok("user not authenticated");

        } catch (AuthenticationException e) {
            log.warn("Authentication failed for user: {} {}", email,e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    public ResponseEntity<?> forgetPassword(EmailDto email) {
        String mail = email.getEmail();
        if(signupRepo.existsByEmail(mail)){
            String username = "simple";
            PasswordResetRequest request = new PasswordResetRequest(mail,username);
            kafkaTemplate.send("forget-password",request);
            return new ResponseEntity<>("reset password send to mail",HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Email id");
    }
}

