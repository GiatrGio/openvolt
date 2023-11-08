package com.example.energyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = "com.example")
public class EnergyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergyApiApplication.class, args);
    }

}
