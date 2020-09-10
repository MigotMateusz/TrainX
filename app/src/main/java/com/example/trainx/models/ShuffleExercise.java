package com.example.trainx.models;

import java.io.Serializable;

public class ShuffleExercise implements Serializable {
    private String name;
    private int Beginner;
    private int Intermediate;
    private int Athlete;

    public ShuffleExercise() {}

    public ShuffleExercise(String n, int beginner, int intermediate, int athlete) {
        this.name = n;
        this.Beginner = beginner;
        this.Athlete = athlete;
        this.Intermediate = intermediate;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public int getBeginner() {
        return Beginner;
    }

    public void setBeginner(int Beginner) {
        this.Beginner = Beginner;
    }

    public int getIntermediate() {
        return Intermediate;
    }

    public void setIntermediate(int Intermediate) {
        this.Intermediate = Intermediate;
    }

    public int getAthlete() {
        return Athlete;
    }

    public void setAthlete(int Athlete) {
        this.Athlete = Athlete;
    }
}
