package com.example.trombonework;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 120, 120);
        stage.setTitle("TromboneWork");
        stage.setScene(scene);
        stage.setMinWidth(400.0); // Замініть 650 на потрібне значення, щоб повністю вміщався текст "Твори для тромбону" та кнопки
        stage.setMinHeight(300.0); // За потреби можна зафіксувати й мінімальну висоту
        stage.show();
    }
}
