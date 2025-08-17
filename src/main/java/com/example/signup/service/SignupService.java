package com.example.signup.service;


import com.example.signup.dto.RegisterRequest;
import com.example.signup.exception.UserAlreadyAvailableException;
import com.example.signup.model.User;
import com.example.signup.repository.SignupRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SignupService {

    private final PasswordEncoder passwordEncoder;
    private final SignupRepo signUpRepo;

    SignupService(PasswordEncoder passwordEncoder,SignupRepo signUpRepo){
        this.signUpRepo = signUpRepo;
        this.passwordEncoder = passwordEncoder;
    }
    public ResponseEntity<String> register(RegisterRequest request){
        /**
         * save new user inside the database
         * @param email the user entity containing registration details
         * @return the saved user entity with an assigned ID
         */
        try{
            log.info("New user trying to register");
            if(signUpRepo.existsByEmail(request.getEmail().toString())){
                log.info("Email Already available");
                throw new UserAlreadyAvailableException("Email already found");
            }
            User user = new User();
            user.setEmail(request.getEmail());
            user.setUserName(request.getUserName());
            log.info("Saving user with password: {} length: {}", request.getPassword(),request.getPassword() != null ? request.getPassword().length() : "null");
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            signUpRepo.save(user);
            return new ResponseEntity<>("User created Successfully",HttpStatus.CREATED);
        }
        catch(UserAlreadyAvailableException e){
            log.error("User name already available {}",request.getEmail().toString());
            return new ResponseEntity<>("User Already available",HttpStatus.CONFLICT);
        }
        catch (Exception e){
            log.error("Some error occur during the sign up {}", e.getMessage());
            return new ResponseEntity<>("Unexpected error occur during signup",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
