package com.eventico;

import com.eventico.config.MultipartConfigElement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters =
        {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MultipartConfigElement.class)})
public class EventicoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventicoApplication.class, args);
    }
}
