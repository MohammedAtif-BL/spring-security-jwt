package com.example.spring_jwt.exception;

public class CustomBadCredentialsException extends RuntimeException {
    public CustomBadCredentialsException(String message) {
        super(message);
    }
}
