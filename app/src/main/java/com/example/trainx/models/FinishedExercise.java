package com.example.trainx.models;

import java.io.Serializable;
import java.util.ArrayList;

public class FinishedExercise implements Serializable {
    private String name;
    private ArrayList<FinishedSet> sets;

    public FinishedExercise() {}

    public FinishedExercise(String n, ArrayList<FinishedSet> s){
        this.name = n;
        this.sets = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FinishedSet> getSets() {
        return sets;
    }

    public void setSets(ArrayList<FinishedSet> sets) {
        this.sets = sets;
    }

}
