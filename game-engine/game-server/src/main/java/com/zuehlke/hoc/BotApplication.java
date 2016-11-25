package com.zuehlke.hoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BotApplication {
    
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BotApplication.class, args);
    }
}
