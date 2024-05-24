package com.example.demo;

import java.io.Serializable;

public class Medal implements Serializable {
    MedalType type;
    int count;

    Medal(MedalType type, int count) {
        this.type = type;
        this.count = count;
    }
}
