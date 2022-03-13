package com.example.fargate.strategy;

import com.example.fargate.enums.PositionName;
import com.example.fargate.model.Book;
import com.example.fargate.model.User;

public interface BorrowerStrategy {

    int getMaxBorrowNum();

    User borrowBook(User user, Book book);

    User returnBook(User user, Book book);

    PositionName getPositionName();
}
