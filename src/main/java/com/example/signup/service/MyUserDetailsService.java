package com.example.signup.service;

import com.example.signup.model.User;
import com.example.signup.repository.SignupRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private final SignupRepo signupRepo;

    public MyUserDetailsService(SignupRepo signupRepo){
        this.signupRepo = signupRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = signupRepo.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Set<SimpleGrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("ROLE_USER"));

        log.info("{} is checking inside UserDetailsService",username);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .authorities(authorities)
                .build();

    }
}
