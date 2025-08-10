package com.example.signup.exception;

public class UserAlreadyAvailableException extends Exception{
    public UserAlreadyAvailableException(String message){
        super(message);
    }
}
