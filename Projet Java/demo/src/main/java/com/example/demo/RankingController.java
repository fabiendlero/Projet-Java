package com.example.demo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class RankingController {
    private MedalTable medalTable;

    public RankingController(MedalTable medalTable) {
        this.medalTable = medalTable;
    }

    public void showRanking(Stage stage) {
        TableView<CountryMedals> rankingTable = new TableView<>();
        TableColumn<CountryMedals, String> countryColumn = new TableColumn<>("Pays");
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
    
        TableColumn<CountryMedals, Integer> goldColumn = new TableColumn<>("Or");
        goldColumn.setCellValueFactory(data -> {
            ObservableList<Medal> medals = data.getValue().getMedals();
            return new SimpleIntegerProperty(countMedals(medals, MedalType.GOLD)).asObject();
        });
    
        TableColumn<CountryMedals, Integer> silverColumn = new TableColumn<>("Argent");
        silverColumn.setCellValueFactory(data -> {
            ObservableList<Medal> medals = data.getValue().getMedals();
            return new SimpleIntegerProperty(countMedals(medals, MedalType.SILVER)).asObject();
        });
    
        TableColumn<CountryMedals, Integer> bronzeColumn = new TableColumn<>("Bronze");
        bronzeColumn.setCellValueFactory(data -> {
            ObservableList<Medal> medals = data.getValue().getMedals();
            return new SimpleIntegerProperty(countMedals(medals, MedalType.BRONZE)).asObject();
        });
    
        rankingTable.getColumns().addAll(countryColumn, goldColumn, silverColumn, bronzeColumn);
    
        ObservableList<CountryMedals> countryMedalsList = FXCollections.observableArrayList();
        countryMedalsList.addAll(medalTable.countryMedals.entrySet().stream().map(CountryMedals::new).toList());
        rankingTable.getItems().addAll(countryMedalsList);
    
        VBox root = new VBox(rankingTable);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private int countMedals(ObservableList<Medal> medals, MedalType type) {
        return (int) medals.stream()
                .filter(medal -> medal.getType() == type)
                .count();
    }
}