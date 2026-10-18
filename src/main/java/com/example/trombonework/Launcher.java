package com.example.trombonework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Launcher {

    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        // 1. Запускаємо контекст Spring Boot
        springContext = SpringApplication.run(Launcher.class, args);

        // 2. Запускаємо ваш звичайний JavaFX клас Application
        javafx.application.Application.launch(TromboneWorksApplication.class, args);
    }

    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }
}
