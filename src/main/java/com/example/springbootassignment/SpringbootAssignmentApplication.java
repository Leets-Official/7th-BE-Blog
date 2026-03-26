package com.example.springbootassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringbootAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAssignmentApplication.class, args);
    }

}
