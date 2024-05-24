package com.example.demo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class ResultTabController {
    @FXML
    private TableView<Result> resultTableView;
    @FXML
    private TableColumn<Result, String> athleteColumn;
    @FXML
    private TableColumn<Result, String> eventColumn;
    @FXML
    private TableColumn<Result, Integer> positionColumn;
    @FXML
    private TableColumn<Result, Double> scoreColumn;
    @FXML
    private TableColumn<Result, String> medalColumn;
    @FXML
    private ComboBox<Athlete> athleteComboBox;
    @FXML
    private ComboBox<Event> eventComboBox;
    @FXML
    private TextField positionField;
    @FXML
    private TextField scoreField;
    @FXML
    private ComboBox<MedalType> medalComboBox;
    @FXML
    private Button addResultButton;

    private OlympicGames olympicGames;
    private ObservableList<Result> resultList;

    public void initialize() {
        // Configurez la TableView pour permettre l'édition en ligne
        resultTableView.setEditable(true);
        positionColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        scoreColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        // Ajoutez les gestionnaires d'événements de double-clic
        positionColumn.setOnEditCommit(event -> {
            Result editedResult = event.getRowValue();
            editedResult.setPosition(event.getNewValue());
        });
        scoreColumn.setOnEditCommit(event -> {
            Result editedResult = event.getRowValue();
            editedResult.setScore(event.getNewValue());
        });
        


        athleteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().athlete.getName()));
        eventColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().event.name));
        positionColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().position).asObject());
        scoreColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().score).asObject());
        medalColumn.setCellValueFactory(cellData -> {
            Medal medal = cellData.getValue().medal;
            return new SimpleStringProperty(medal != null ? medal.type.toString() : "");
        });
        medalComboBox.setItems(FXCollections.observableArrayList(MedalType.values()));
    }
    private void updateAthleteComboBox() {
        athleteComboBox.setItems(olympicGames.getAthletes());
    }

    private void updateEventComboBox() {
        eventComboBox.setItems(olympicGames.getEvents());
    }
    public void setOlympicGames(OlympicGames olympicGames) {
        this.olympicGames = olympicGames;
        resultList = FXCollections.observableArrayList(olympicGames.getResults());
        resultTableView.setItems(resultList);
        athleteComboBox.setConverter(new StringConverter<Athlete>() {
            @Override
            public String toString(Athlete Athlete) {
                return Athlete != null ? Athlete.getName() : "";
            }

            @Override
            public Athlete fromString(String string) {
                return null;
            }
        });
        athleteComboBox.setItems(FXCollections.observableArrayList(olympicGames.getAthletes()));
        eventComboBox.setConverter(new StringConverter<Event>() {
            @Override
            public String toString(Event Event) {
                return Event != null ? Event.getName() : "";
            }

            @Override
            public Event fromString(String string) {
                return null;
            }
        });
        eventComboBox.setItems(FXCollections.observableArrayList(olympicGames.getEvents()));
        updateAthleteComboBox();
        updateEventComboBox();
    }

    @FXML
    private void addResult() {
        try {
            Athlete selectedAthlete = athleteComboBox.getValue();
            Event selectedEvent = eventComboBox.getValue();
            int position = Integer.parseInt(positionField.getText());
            double score = Double.parseDouble(scoreField.getText());
            MedalType medalType = medalComboBox.getValue();
            Medal medal = medalType != null ? new Medal(medalType, 1) : null;
            if (selectedAthlete != null && selectedEvent != null) {
                Result result = new Result(selectedAthlete, selectedEvent, position, score, medal);
                olympicGames.recordResult(result);
                resultList.add(result);
                clearFields();
                updateAthleteComboBox();
                updateEventComboBox();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid position or score format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error adding result: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void removeResult() {
        try {
            Result selectedResult = resultTableView.getSelectionModel().getSelectedItem();
            if (selectedResult != null) {
                olympicGames.getResults().remove(selectedResult);
                resultTableView.getItems().remove(selectedResult);
            }
        } catch (Exception e) {
            System.out.println("Error removing result: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearFields() {
        athleteComboBox.getSelectionModel().clearSelection();
        eventComboBox.getSelectionModel().clearSelection();
        positionField.clear();
        scoreField.clear();
        medalComboBox.getSelectionModel().clearSelection();
    }
}
