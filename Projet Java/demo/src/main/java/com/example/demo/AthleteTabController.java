package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AthleteTabController {
    @FXML
    private TableView<Athlete> athleteTableView;
    @FXML
    private TableColumn<Athlete, String> nameColumn;
    @FXML
    private TableColumn<Athlete, String> countryColumn;
    @FXML
    private TableColumn<Athlete, Integer> ageColumn;
    @FXML
    private TableColumn<Athlete, String> genderColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField genderField;
    @FXML
    private Button addAthleteButton;
    @FXML
    private Button deleteAthleteButton;

    private OlympicGames olympicGames;
    private ObservableList<Athlete> athleteList;

    public void initialize() {
        // Initialize table columns
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        countryColumn.setCellValueFactory(cellData -> cellData.getValue().countryProperty());
        ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
        genderColumn.setCellValueFactory(cellData -> cellData.getValue().genderProperty());
    }

    public void setOlympicGames(OlympicGames olympicGames) {
        this.olympicGames = olympicGames;
        athleteList = olympicGames.getAthletes();
        athleteTableView.setItems(athleteList);
    }

    @FXML
    private void addAthlete() {
        try {
            String name = nameField.getText();
            String country = countryField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderField.getText();
            Athlete athlete = new Athlete(name, country, age, gender);
            olympicGames.addAthlete(athlete);
            clearFields();
        } catch (NumberFormatException e) {
            System.out.println("Invalid age format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error adding athlete: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteAthlete() {
        Athlete selectedAthlete = athleteTableView.getSelectionModel().getSelectedItem();
        if (selectedAthlete != null) {
            olympicGames.removeAthlete(selectedAthlete);
            athleteList.remove(selectedAthlete);
        } else {
            System.out.println("No athlete selected for deletion.");
        }
    }

    private void clearFields() {
        nameField.clear();
        countryField.clear();
        ageField.clear();
        genderField.clear();
    }
}
