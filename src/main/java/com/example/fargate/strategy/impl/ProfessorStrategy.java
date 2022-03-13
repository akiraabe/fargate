package com.example.fargate.strategy.impl;

import com.example.fargate.enums.PositionName;
import com.example.fargate.exception.CannotBorrowBookException;
import com.example.fargate.exception.CannotReturnBookException;
import com.example.fargate.model.Book;
import com.example.fargate.model.User;
import com.example.fargate.strategy.BorrowerStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ProfessorStrategy implements BorrowerStrategy {
    @Override
    public int getMaxBorrowNum() {
        return 50;
    }

    @Override
    public User borrowBook(User user, Book book) {

        if (user.getCurrentNum() >= getMaxBorrowNum()) {
            throw new CannotBorrowBookException();
        }
        final var books = new ArrayList<>(user.getBooks());
        books.add(book);
        return new User(user.getName(), user.getPositionName(), books);
    }

    @Override
    public User returnBook(User user, Book book) {
        if (user.getCurrentNum() == 0 || !user.getBooks().contains(book)) {
            throw new CannotReturnBookException(book.getName());
        }
        final var books = new ArrayList<>(user.getBooks());
        books.remove(book);
        return new User(user.getName(), user.getPositionName(), books);
    }

    @Override
    public PositionName getPositionName() {
        return PositionName.PROFESSOR;
    }
}
