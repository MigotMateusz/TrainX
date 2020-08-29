package com.example.trainx;

import java.io.Serializable;
import java.util.ArrayList;

public class FinishedTraining implements Serializable {
    private String name;
    private ArrayList<FinishedExercise> exercises;

    public FinishedTraining() {}

    public FinishedTraining(String n, ArrayList<FinishedExercise> e) {
        this.name = n;
        this.exercises = e;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FinishedExercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<FinishedExercise> exercises) {
        this.exercises = exercises;
    }
}
