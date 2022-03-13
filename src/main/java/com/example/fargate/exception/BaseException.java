package com.example.fargate.exception;

public class BaseException extends RuntimeException {
    protected BaseException(String message) {
        super(message);
        System.out.println(message);
    }
}
