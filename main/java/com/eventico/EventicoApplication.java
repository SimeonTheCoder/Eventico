package com.eventico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.eventico.repo")
public class EventicoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventicoApplication.class, args);
    }
}
