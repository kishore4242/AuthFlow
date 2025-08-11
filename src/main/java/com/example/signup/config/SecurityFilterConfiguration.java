package com.example.signup.config;

import com.example.signup.filter.RedisSessionCheck;
import com.example.signup.security.auth.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterConfiguration {

    private final RedisSessionCheck redisSessionCheck;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RedisSessionCheck redisSessionCheck) throws Exception{
        http
                .csrf((csrf) -> csrf.disable())
                .authenticationProvider(customAuthenticationProvider)
                .addFilterBefore(redisSessionCheck, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/","/login","/signup","/forget-password","/reset-password").permitAll()
                        .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       CustomAuthenticationProvider customProvider) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customProvider)
                .build();
    }

}
