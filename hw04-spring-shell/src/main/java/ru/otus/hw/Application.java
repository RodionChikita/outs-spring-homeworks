package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@CommandScan
@ConfigurationPropertiesScan("ru.otus.hw")
public class Application {
    public static void main(String[] args) {

        //Создать контекст Spring Boot приложения
        SpringApplication.run(Application.class, args);
    }
}