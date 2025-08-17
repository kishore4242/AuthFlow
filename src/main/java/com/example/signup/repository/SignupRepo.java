package com.example.signup.repository;

import com.example.signup.model.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignupRepo extends JpaRepository<User, Long> {

    boolean existsByUserName(String userName);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByUserName(String username);

    String getUserNameByEmail(String email);
}
