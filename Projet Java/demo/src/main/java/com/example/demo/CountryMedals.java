package com.example.demo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

public class CountryMedals {
    private final SimpleStringProperty country;
    private final SimpleListProperty<Medal> medals;

    public CountryMedals(Map.Entry<String, List<Medal>> entry) {
        this.country = new SimpleStringProperty(entry.getKey());
        this.medals = new SimpleListProperty<>(FXCollections.observableList(entry.getValue()));
    }

    public String getCountry() {
        return country.get();
    }

    public SimpleStringProperty countryProperty() {
        return country;
    }

    public ObservableList<Medal> getMedals() {
        return medals.get();
    }

    public SimpleListProperty<Medal> medalsProperty() {
        return medals;
    }
}