package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

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
    private ComboBox<String> countryField;
    @FXML
    private TextField ageField;
    @FXML
    private ComboBox<String> genderField;

    private OlympicGames olympicGames;
    private ObservableList<Athlete> athleteList;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        countryColumn.setCellValueFactory(cellData -> cellData.getValue().countryProperty());
        ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
        genderColumn.setCellValueFactory(cellData -> cellData.getValue().genderProperty());

        // Set up the ComboBoxes
        countryField.setItems(FXCollections.observableArrayList("FRA", "USA", "GBR", "CAN", "GER"));
        genderField.setItems(FXCollections.observableArrayList("Homme", "Femme", "Autre"));

        // Add editing capabilities to the TableView
        nameColumn.setOnEditCommit(event -> {
            Athlete athlete = event.getRowValue();
            athlete.setName(event.getNewValue());
        });

        ageColumn.setOnEditCommit(event -> {
            Athlete athlete = event.getRowValue();
            athlete.setAge(event.getNewValue());
        });

        // Configure the TableView to allow inline editing
        athleteTableView.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ageColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
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
            String country = countryField.getValue();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderField.getValue();
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
            olympicGames.getAthletes().remove(selectedAthlete);
            athleteTableView.getItems().remove(selectedAthlete);
        } else {
            System.out.println("No athlete selected for deletion.");
        }
    }

    private void clearFields() {
        nameField.clear();
        countryField.setValue(null);
        ageField.clear();
        genderField.setValue(null);
    }
}
