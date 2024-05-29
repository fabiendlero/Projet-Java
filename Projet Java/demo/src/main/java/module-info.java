module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires json.simple;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}