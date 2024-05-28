package com.example.demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MedalTable implements Serializable {
    Map<String, List<Medal>> countryMedals = new HashMap<>();

    void updateMedals(String country, Medal medal) {
        countryMedals.putIfAbsent(country, new ArrayList<>());
        countryMedals.get(country).add(medal);
    }

    List<Medal> getCountryMedals(String country) {
        return countryMedals.getOrDefault(country, new ArrayList<>());
    }
}

