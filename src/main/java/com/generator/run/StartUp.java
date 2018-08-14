package com.generator.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.generator"})
public class StartUp
{
    public static void main(String[] args)
    {
        SpringApplication.run(StartUp.class, args);
    }
}
