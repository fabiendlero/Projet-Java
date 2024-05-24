package com.example.demo;

import java.io.Serializable;

class Result implements Serializable {
    Athlete athlete;
    Event event;
    int position;
    double score;
    Medal medal;

    Result(Athlete athlete, Event event, int position, double score, Medal medal) {
        this.athlete = athlete;
        this.event = event;
        this.position = position;
        this.score = score;
        this.medal = medal;
    }
}
