package com.example.demo;

import java.io.*;
import java.util.*;

class Sport implements Serializable {
    String name;
    List<Event> events = new ArrayList<>();

    Sport(String name) {
        this.name = name;
    }

    void addEvent(Event event) {
        events.add(event);
    }

    public String getName() {
        return name;
    }
}

