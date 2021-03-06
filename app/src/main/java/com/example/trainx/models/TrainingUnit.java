package com.example.trainx.models;

import java.io.Serializable;
import java.util.ArrayList;

public class TrainingUnit implements Serializable {
    private String name;
    private ArrayList<Exercise> exerciseArrayList;

    public TrainingUnit() {}

    public TrainingUnit(String name, ArrayList<Exercise> exerciseArrayList) {
        this.name = name;
        this.exerciseArrayList = exerciseArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Exercise> getExerciseArrayList() {
        return exerciseArrayList;
    }

    public void setExerciseArrayList(ArrayList<Exercise> exerciseArrayList) {
        this.exerciseArrayList = exerciseArrayList;
    }
}
