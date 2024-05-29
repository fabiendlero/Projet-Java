package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class AthleteCellFactory extends ListCell<Athlete> {
    @Override
    protected void updateItem(Athlete athlete, boolean empty) {
        super.updateItem(athlete, empty);
        if(empty){
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/AthleteCell.fxml"));
            AthleteCellController controller = new AthleteCellController(athlete);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
