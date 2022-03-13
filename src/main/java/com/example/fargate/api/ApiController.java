package com.example.fargate.api;

import com.example.fargate.enums.PositionName;
import com.example.fargate.factory.BorrowerStrategyFactory;
import com.example.fargate.model.Book;
import com.example.fargate.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    Logger logger = LoggerFactory.getLogger(getClass().getPackage().getName());

    @Autowired
    BorrowerStrategyFactory borrowerStrategyFactory;

    @GetMapping("/custom-health-path")
    public String healthCheck() {
        logger.info("healthCheck is called");
        return "I'm healthy";
    }


    @GetMapping("/say")
    public String say() {
        logger.info("say is called");
        return "Hello, Fargate app by Spring-Boot(Buildpacks)";
    }

    @GetMapping("/test1")
    public String test1() {

        logger.info("test1 is called");

        User user = new User("TestProfessor", PositionName.PROFESSOR);
        var borrowerStrategy = borrowerStrategyFactory.findStrategy(user.getPositionName());
        user = borrowerStrategy.borrowBook(user, new Book("Book1"));

        logger.info(user.toString());

        return user.toString();
    }
}
