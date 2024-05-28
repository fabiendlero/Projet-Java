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

    public void setPosition(int position) {
        this.position = position;
    }

    public void setScore(double score) {
        this.score = score;
    }
    
    public void setMedal(Medal medal) {
        this.medal = medal;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public Medal getMedal() {
        return medal;
    }
}