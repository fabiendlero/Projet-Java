package com.example.demo;

import java.io.Serializable;

public class Medal implements Serializable {
    private MedalType type;
    
    public Medal(MedalType type) {
        this.type = type;
    }

    public MedalType getType() {
        return type;
    }
    
}

