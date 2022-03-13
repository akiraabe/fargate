package com.example.fargate.exception;

public class CannotReturnBookException extends BaseException {
    public CannotReturnBookException(String bookName) {
        super("The book '" + bookName + "' could not be returned.");
    }
}
