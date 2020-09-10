package com.example.trainx.models;

import java.io.Serializable;

public class FinishedSet implements Serializable {
    private int reps;
    private int weight;
    public FinishedSet() {}

    public FinishedSet(int r, int w) {
        this.reps = r;
        this.weight = w;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
