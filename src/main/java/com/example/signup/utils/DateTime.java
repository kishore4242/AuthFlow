package com.example.signup.utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
    public static String getDateAndTime(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        return dateTime.format(dateTimeFormatter);
    }
}
