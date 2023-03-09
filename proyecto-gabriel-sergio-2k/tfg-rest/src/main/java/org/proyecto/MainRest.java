package org.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@EnableAutoConfiguration
@AutoConfiguration
@ComponentScan
public class MainRest {

    public static void main(String[] args) {
        SpringApplication.run(MainRest.class, args);
    }
}