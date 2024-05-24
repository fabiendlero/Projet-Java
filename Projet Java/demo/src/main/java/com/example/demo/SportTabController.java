package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SportTabController {
    @FXML
    private TableView<Sport> sportTableView;
    @FXML
    private TableColumn<Sport, String> nameColumn;
    @FXML
    private TextField nameField;
    @FXML
    private Button addSportButton;

    private OlympicGames olympicGames;
    private ObservableList<Sport> sportList;

    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name));
    }

    public void setOlympicGames(OlympicGames olympicGames) {
        this.olympicGames = olympicGames;
        sportList = olympicGames.getSportList();
        sportTableView.setItems(sportList);
    }

    @FXML
    private void addSport() {
        try {
            String name = nameField.getText();
            Sport sport = new Sport(name);
            olympicGames.addSport(sport);
            clearFields();
        } catch (Exception e) {
            System.out.println("Error adding sport: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void clearFields() {
        nameField.clear();
    }
}
