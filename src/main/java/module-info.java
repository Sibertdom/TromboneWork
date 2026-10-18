module com.example.trombonework {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    opens com.example.trombonework.model to com.fasterxml.jackson.databind, javafx.base;
    opens com.example.trombonework to javafx.fxml;

    exports com.example.trombonework;
    exports com.example.trombonework.util; // Додаємо експорт нового пакета
}