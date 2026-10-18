package com.example.trombonework;

import com.example.trombonework.model.TromboneWork;
import com.example.trombonework.service.TromboneService;
import com.example.trombonework.util.WorkDialog;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.collections.transformation.SortedList;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Controller {

    @FXML private Label mainTitleLabel;
    @FXML private TableView<TromboneWork> worksTable;
    @FXML private TableColumn<TromboneWork, String> titleColumn;
    @FXML private TableColumn<TromboneWork, String> composerColumn;
    @FXML private TableColumn<TromboneWork, String> typeColumn;
    @FXML private TableColumn<TromboneWork, Integer> difficultyColumn;
    @FXML private TableColumn<TromboneWork, String> genreColumn;
    @FXML private Label statusLabel;
    @FXML private TextArea notesDetailsArea; // Нове поле для відображення нотаток

    private final TromboneService tromboneService;

    // Spring сам автоматично підставить правильний TromboneService з усіма репозиторіями
    public Controller(TromboneService tromboneService) {
        this.tromboneService = tromboneService;
    }
    private ObservableList<TromboneWork> masterWorksList;
    private FilteredList<TromboneWork> filteredData;
    private double hue = 0;

    @FXML
    public void initialize() {
        setupTableColumns();
        setupTableSelection(); // Додано слухач вибору рядка
        loadData();
        startTitleColorAnimation();
    }

    private void setupTableColumns() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        composerColumn.setCellValueFactory(new PropertyValueFactory<>("composer"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeOfTrombone"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficultyLevel"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
    }

    private void setupTableSelection() {
        // Відстежуємо вибір твору в таблиці та оновлюємо праву панель
        worksTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedWork) -> {
            if (selectedWork != null && selectedWork.getNotes() != null && !selectedWork.getNotes().isBlank()) {
                notesDetailsArea.setText(selectedWork.getNotes());
            } else if (selectedWork != null) {
                notesDetailsArea.setText("Немає додаткових нотаток для цього твору.");
            } else {
                notesDetailsArea.clear();
            }
        });
    }

    private void loadData() {
        masterWorksList = FXCollections.observableArrayList(tromboneService.getAllWorks());
        filteredData = new FilteredList<>(masterWorksList, p -> true);

        // 1. Обгортаємо FilteredList у SortedList для підтримки сортування
        SortedList<TromboneWork> sortedData = new SortedList<>(filteredData);

        // 2. Пов'язуємо компаратор сортування таблиці з SortedList
        sortedData.comparatorProperty().bind(worksTable.comparatorProperty());

        // 3. Передаємо в таблицю саме sortedData замість filteredData
        worksTable.setItems(sortedData);

        updateStatus("Завантажено творів: " + masterWorksList.size());
    }

    @FXML
    private void onFilterButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String filterType = clickedButton.getText();

        if ("Всі".equalsIgnoreCase(filterType)) {
            filteredData.setPredicate(work -> true);
        } else {
            filteredData.setPredicate(work -> filterType.equalsIgnoreCase(work.getTypeOfTrombone()));
        }

        updateStatus("Категорія: " + filterType + " | Знайдено: " + filteredData.size());
    }

    @FXML
    private void onAddWorkClick() {
        Optional<TromboneWork> result = WorkDialog.show(null, tromboneService.getAllComposers());
        result.ifPresent(work -> {
            tromboneService.addWork(work);
            loadData();
            updateStatus("Твір «" + work.getTitle() + "» успішно додано!");
        });
    }

    @FXML
    private void onEditWorkClick() {
        TromboneWork selectedWork = worksTable.getSelectionModel().getSelectedItem();
        if (selectedWork == null) {
            showAlert("Помилка", "Будь ласка, виберіть твір для редагування.");
            return;
        }

        Optional<TromboneWork> result = WorkDialog.show(selectedWork, tromboneService.getAllComposers());
        result.ifPresent(updatedWork -> {
            tromboneService.updateWork(updatedWork);
            loadData();
            updateStatus("Твір «" + updatedWork.getTitle() + "» оновлено!");
        });
    }

    @FXML
    private void onDeleteWorkClick() {
        TromboneWork selectedWork = worksTable.getSelectionModel().getSelectedItem();
        if (selectedWork == null) {
            showAlert("Помилка", "Будь ласка, виберіть твір для видалення.");
            return;
        }

        Alert confirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Ви впевнені, що хочете видалити твір «" + selectedWork.getTitle() + "»?",
                ButtonType.YES, ButtonType.NO
        );
        confirmation.setTitle("Підтвердження видалення");
        confirmation.setHeaderText(null);

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                tromboneService.deleteWork(selectedWork.getId());
                loadData();
                notesDetailsArea.clear();
                updateStatus("Твір «" + selectedWork.getTitle() + "» видалено.");
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void startTitleColorAnimation() {
        if (mainTitleLabel == null) return;

        Timeline colorTimeline = new Timeline(
                new KeyFrame(Duration.millis(40), event -> {
                    hue = (hue + 2) % 360;
                    Color color = Color.hsb(hue, 0.7, 0.95);

                    String hexColor = String.format("#%02X%02X%02X",
                            (int) (color.getRed() * 255),
                            (int) (color.getGreen() * 255),
                            (int) (color.getBlue() * 255));

                    mainTitleLabel.setStyle(
                            "-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + hexColor + ";"
                    );
                })
        );

        colorTimeline.setCycleCount(Timeline.INDEFINITE);
        colorTimeline.play();
    }

    private void updateStatus(String message) {
        if (statusLabel != null) {
            statusLabel.setText(message);
        }
    }
}