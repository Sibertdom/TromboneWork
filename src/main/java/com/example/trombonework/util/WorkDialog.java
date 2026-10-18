package com.example.trombonework.util;

import com.example.trombonework.model.Composer;
import com.example.trombonework.model.TromboneWork;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Optional;

public class WorkDialog {

    // Змінюємо сигнатуру: список композиторів більше не потрібен у діалозі,
    // оскільки ми вводимо ім'я текстом. Але залишаємо параметр для сумісності або прибираємо за бажанням.
    public static Optional<TromboneWork> show(TromboneWork initialWork, List<Composer> availableComposers) {
        Dialog<TromboneWork> dialog = new Dialog<>();
        dialog.setTitle(initialWork == null ? "Додати новий твір" : "Редагувати твір");
        dialog.setHeaderText(initialWork == null ? "Введіть дані нового твору" : "Змініть потрібні поля");

        ButtonType saveButtonType = new ButtonType("Зберегти", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField titleField = new TextField();

        // Замість ComboBox використовуємо TextField для ручного введення імені композитора
        TextField composerNameField = new TextField();
        composerNameField.setPromptText("Наприклад: Борис Лятошинський");

        TextField typeField = new TextField();
        TextField accompanimentField = new TextField();
        TextField difficultyField = new TextField();
        TextField genreField = new TextField();
        TextField yearField = new TextField();
        CheckBox favoriteCheckBox = new CheckBox("Улюблений твір");

        TextArea notesArea = new TextArea();
        notesArea.setPrefRowCount(3);
        notesArea.setWrapText(true);

        if (initialWork != null) {
            titleField.setText(initialWork.getTitle());
            if (initialWork.getComposer() != null) {
                composerNameField.setText(initialWork.getComposer().getFullName());
            }
            typeField.setText(initialWork.getTypeOfTrombone());
            accompanimentField.setText(initialWork.getAccompanimentType());
            if (initialWork.getDifficultyLevel() != null) {
                difficultyField.setText(String.valueOf(initialWork.getDifficultyLevel()));
            }
            genreField.setText(initialWork.getGenre());
            if (initialWork.getYearComposed() != null) {
                yearField.setText(String.valueOf(initialWork.getYearComposed()));
            }
            favoriteCheckBox.setSelected(Boolean.TRUE.equals(initialWork.getIsFavorite()));
            notesArea.setText(initialWork.getNotes());
        }

        grid.add(new Label("Назва:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Композитор:"), 0, 1);
        grid.add(composerNameField, 1, 1);
        grid.add(new Label("Тип тромбона:"), 0, 2);
        grid.add(typeField, 1, 2);
        grid.add(new Label("Супровід:"), 0, 3);
        grid.add(accompanimentField, 1, 3);
        grid.add(new Label("Складність (1-6):"), 0, 4);
        grid.add(difficultyField, 1, 4);
        grid.add(new Label("Жанр:"), 0, 5);
        grid.add(genreField, 1, 5);
        grid.add(new Label("Рік написання:"), 0, 6);
        grid.add(yearField, 1, 6);
        grid.add(new Label("Нотатки:"), 0, 7);
        grid.add(notesArea, 1, 7);
        grid.add(favoriteCheckBox, 1, 8);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                Integer difficulty = null;
                Integer year = null;
                try { difficulty = Integer.parseInt(difficultyField.getText()); } catch (Exception ignored) {}
                try { year = Integer.parseInt(yearField.getText()); } catch (Exception ignored) {}

                Long id = (initialWork != null) ? initialWork.getId() : System.currentTimeMillis();

                // Зберігаємо текстове значення композитора в тимчасове поле або створюємо об'єкт
                String compName = composerNameField.getText().trim();
                Composer tempComposer = null;
                if (!compName.isBlank()) {
                    tempComposer = new Composer();
                    // Розбиваємо на ім'я та прізвище для зручності
                    String[] parts = compName.split("\\s+", 2);
                    if (parts.length > 1) {
                        tempComposer.setFirstName(parts[0]);
                        tempComposer.setLastName(parts[1]);
                    } else {
                        tempComposer.setLastName(parts[0]);
                    }
                }

                return TromboneWork.builder()
                        .id(id)
                        .title(titleField.getText())
                        .composer(tempComposer)
                        .composerId(initialWork != null ? initialWork.getComposerId() : System.currentTimeMillis())
                        .typeOfTrombone(typeField.getText())
                        .accompanimentType(accompanimentField.getText())
                        .difficultyLevel(difficulty)
                        .genre(genreField.getText())
                        .yearComposed(year)
                        .isFavorite(favoriteCheckBox.isSelected())
                        .notes(notesArea.getText())
                        .build();
            }
            return null;
        });

        return dialog.showAndWait();
    }
}