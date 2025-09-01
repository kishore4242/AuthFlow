package com.example.signup.service;

import com.example.signup.dto.EmailDto;
import com.example.signup.dto.LoginNotificationDto;
import com.example.signup.dto.NewPassword;
import com.example.signup.dto.PasswordResetRequest;
import com.example.signup.kafka.MessageSerializer;
import com.example.signup.kafka.MessagingServices;
import com.example.signup.repository.SignupRepo;
import com.example.signup.security.auth.JwtService;
import com.example.signup.security.auth.OtpService;
import com.example.signup.utils.DateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LoginService {

    private final SignupRepo signupRepo;
    private final MessagingServices messagingServices;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtService jwtService;
    private final OtpService otpService;

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
                LoginNotificationDto dto = new LoginNotificationDto(email, DateTime.getDateAndTime());
                messagingServices.loginNotification(dto);
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
            String username = signupRepo.getUserNameByEmail(mail);
            String otp = OtpService.generateOtp();
            PasswordResetRequest request = new PasswordResetRequest(mail,username,otp);
            otpService.saveOtp(email.getEmail(),otp);
            messagingServices.resetPasswordNotification(request);
            return new ResponseEntity<>("reset password send to mail",HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Email id");
    }


    public ResponseEntity<?> validateOtpAndSaveNewPassword(String otp, NewPassword newPassword) {
        String savedOtp = otpService.getOtp(newPassword.getEmail());
        if(otp.equals(savedOtp)){
            int status = signupRepo.updatePasswordByEmail(newPassword.getNewPassword(), newPassword.getEmail());
            Predicate<Integer> updateStatus = integer -> integer == 1;
            if(updateStatus.test(status)){
                otpService.deleteOtp(newPassword.getEmail());
                return ResponseEntity.ok().body(Map.of(
                        "message", "Password reset successfully",
                        "redirect","/login"
                ));
            }
            return ResponseEntity.status(500).body("Internal Server error");
        }
        return ResponseEntity.status(400).body("Invalid otp");
    }
}

