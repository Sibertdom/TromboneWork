package com.example.trombonework;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class TromboneWorksApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TromboneWorksApplication.class.getResource("hello-view.fxml"));

        // Зв'язуємо JavaFX з контекстом Spring Boot
        if (Launcher.getSpringContext() != null) {
            fxmlLoader.setControllerFactory(Launcher.getSpringContext()::getBean);
        }

        // Завантажуємо сцену з нормальними початковими розмірами (не 120x120)
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TromboneWork");

        // Встановлюємо іконку вікна
        try {
            stage.getIcons().add(new Image(TromboneWorksApplication.class.getResourceAsStream("Trombone_icon.png"), 32, 32, true, true));
        } catch (Exception e) {
            System.out.println("Іконку не знайдено: " + e.getMessage());
        }

        stage.setScene(scene);
        stage.setMinWidth(650.0); // Захист від надмірного звуження вікна
        stage.setMinHeight(500.0);
        stage.show();
    }

    @Override
    public void stop() {
        // Коректно закриваємо Spring при виході з додатку
        if (Launcher.getSpringContext() != null) {
            Launcher.getSpringContext().close();
        }
    }
}