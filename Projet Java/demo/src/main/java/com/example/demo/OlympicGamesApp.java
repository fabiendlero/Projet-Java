package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class OlympicGamesApp extends Application {
    private OlympicGames olympicGames;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        olympicGames = OlympicGames.loadData();

        // Create UI components using FXML files
        Tab athleteTab = loadTab("athlete_tab.fxml");
        Tab sportTab = loadTab("sport_tab.fxml");
        Tab eventTab = loadTab("event_tab.fxml");
        Tab resultTab = loadTab("result_tab.fxml");

        TabPane tabPane = new TabPane(athleteTab, sportTab, eventTab, resultTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // Disable tab closing

        VBox root = new VBox(tabPane);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Olympic Games Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Tab loadTab(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            VBox tabContent = loader.load();
            Tab tab = new Tab();
            tab.setText(fxmlFile.replace("_tab.fxml", "").toUpperCase());
            tab.setContent(tabContent);

            // Set the OlympicGames object in the controllers
            if (fxmlFile.equals("athlete_tab.fxml")) {
                AthleteTabController athleteTabController = loader.getController();
                athleteTabController.setOlympicGames(olympicGames);
            } else if (fxmlFile.equals("sport_tab.fxml")) {
                SportTabController sportTabController = loader.getController();
                sportTabController.setOlympicGames(olympicGames);
            } else if (fxmlFile.equals("event_tab.fxml")) {
                EventTabController eventTabController = loader.getController();
                eventTabController.setOlympicGames(olympicGames);
            } else if (fxmlFile.equals("result_tab.fxml")) {
                ResultTabController resultTabController = loader.getController();
                resultTabController.setOlympicGames(olympicGames);
            }
            return tab;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void stop() throws Exception {
        olympicGames.saveData();
        super.stop();
    }

}