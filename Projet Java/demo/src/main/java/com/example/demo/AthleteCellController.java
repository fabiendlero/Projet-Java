package com.example.demo;

import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AthleteCellController implements Initializable {
    public ImageView flag_img;
    public Label name_lbl;
    public Label age_lbl;
    public Label gender_lbl;
    public Label height_lbl;
    public Label weight_lbl;

    private final Athlete athlete;

    public AthleteCellController(Athlete athlete) {
        this.athlete = athlete;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setFlag_img();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        StringProperty nameProperty = new SimpleStringProperty();
        nameProperty.setValue(athlete.getName());

        IntegerProperty ageProperty = new SimpleIntegerProperty();
        ageProperty.setValue(athlete.getAge());

        name_lbl.textProperty().bind(nameProperty);
        age_lbl.textProperty().bind(ageProperty.asString());
        gender_lbl.textProperty().bind(athlete.genderProperty());
    }

    private void setFlag_img() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader("src/main/resources/com/example/demo/Images/codes.json"));
        JSONObject jo = (JSONObject) obj;
        jo.keySet().forEach(keyStr ->
        {
            Object keyvalue = jo.get(keyStr);
            if (keyvalue.equals(athlete.countryProperty().getValue())) {
                try {
                    URL url = new URL("https://flagcdn.com/16x12/"+keyStr+".png");
                    BufferedImage bufferedImage = ImageIO.read(url);
                    flag_img.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

    }
}
