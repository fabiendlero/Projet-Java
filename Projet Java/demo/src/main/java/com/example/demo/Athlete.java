package com.example.demo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Athlete {
    private SimpleStringProperty name;
    private SimpleStringProperty country;
    private SimpleIntegerProperty age;
    private SimpleStringProperty gender;

    public Athlete(String name, String country, int age, String gender) {
        this.name = new SimpleStringProperty(name);
        this.country = new SimpleStringProperty(country);
        this.age = new SimpleIntegerProperty(age);
        this.gender = new SimpleStringProperty(gender);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public SimpleStringProperty countryProperty() {
        return country;
    }

    public int getAge() {
        return age.get();
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public SimpleStringProperty genderProperty() {
        return gender;
    }
}
//fabien1