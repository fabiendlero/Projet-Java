package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class EventTabController {
    @FXML
    private TableView<Event> eventTableView;
    @FXML
    private TableColumn<Event, String> nameColumn;
    @FXML
    private TableColumn<Event, String> sportColumn;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<Sport> sportComboBox;
    @FXML
    private Button addEventButton;
    @FXML
    private Button removeEventButton;

    private OlympicGames olympicGames;
    private ObservableList<Event> eventList;

    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name));
        sportColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().sport.name));
    }

    private void updateSportComboBox() {
        sportComboBox.setItems(olympicGames.getSportList());
    }

    public void setOlympicGames(OlympicGames olympicGames) {
        this.olympicGames = olympicGames;
        eventList = olympicGames.getEvents();
        eventTableView.setItems(eventList);
        sportComboBox.setConverter(new StringConverter<Sport>() {
            @Override
            public String toString(Sport sport) {
                return sport != null ? sport.getName() : "";
            }

            @Override
            public Sport fromString(String string) {
                return null;
            }
        });
        updateSportComboBox();
    }

    @FXML
    private void addEvent() {
        try {
            String name = nameField.getText();
            Sport selectedSport = sportComboBox.getValue();
            if (selectedSport != null) {
                Event event = new Event(name, selectedSport);
                olympicGames.addEvent(event);
                clearFields();
                updateSportComboBox(); // Update the sportComboBox after adding a new event
            }
        } catch (Exception e) {
            System.out.println("Error adding event: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void removeEvent() {
        try {
            Event selectedEvent = eventTableView.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                olympicGames.getEvents().remove(selectedEvent);
                eventTableView.getItems().remove(selectedEvent);
            }
        } catch (Exception e) {
            System.out.println("Error removing event: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearFields() {
        nameField.clear();
        sportComboBox.getSelectionModel().clearSelection();
    }
}
