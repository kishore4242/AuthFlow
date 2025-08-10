package com.example.signup.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseModel {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
