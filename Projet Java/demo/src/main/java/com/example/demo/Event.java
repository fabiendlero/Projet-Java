package com.example.demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Event implements Serializable {
    String name;
    Sport sport;
    List<Athlete> athletes = new ArrayList<>();
    List<Result> results = new ArrayList<>();

    Event(String name, Sport sport) {
        this.name = name;
        this.sport = sport;
    }

    void addAthlete(Athlete athlete) {
        athletes.add(athlete);
    }

    void recordResult(Result result) {
        results.add(result);
    }

    public String getName() {
        return name;
    }
}
