package com.example.signup.repository;

import com.example.signup.model.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SignupRepo extends JpaRepository<User, Long> {

    boolean existsByUserName(String userName);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByUserName(String username);

    @Query("select u.userName from User u where u.email = :email")
    String getUserNameByEmail(String email);

    @Query("update User u set u.password = :password where u.email = :email")
    @Modifying
    @Transactional
    int updatePasswordByEmail(String password, String email);
}
