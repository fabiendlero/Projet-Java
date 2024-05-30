package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class AthleteTabController {

    @FXML
    private ListView<Athlete> athleteListView;
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
    private ObservableList<String> countries = FXCollections.observableArrayList("France", "United States", "United Kingdom", "Canada", "Germany");
    private ObservableList<String> genders = FXCollections.observableArrayList("Homme", "Femme", "Autre");

    @FXML
    public void initialize() {
        // Set up the ComboBoxes
        countryField.setItems(countries);
        genderField.setItems(genders);

        athleteListView.setItems(athleteList);
        athleteListView.setCellFactory(e -> new AthleteCellFactory());
    }

    public void setOlympicGames(OlympicGames olympicGames) {
        this.olympicGames = olympicGames;
        athleteList = olympicGames.getAthletes();
        athleteListView.setItems(athleteList);
        
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
        Athlete selectedAthlete = athleteListView.getSelectionModel().getSelectedItem();
        if (selectedAthlete != null) {
            olympicGames.getAthletes().remove(selectedAthlete);
            athleteListView.getItems().remove(selectedAthlete);
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
