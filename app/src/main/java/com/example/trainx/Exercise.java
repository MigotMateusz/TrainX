package com.example.trainx;

import android.util.Log;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String name;
    private int sets;
    private int reps;

    public Exercise() {}

    public Exercise(String name, int sets, int reps) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }


    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void print() {
        String msg = this.name + " " + this.sets + "x" + this.reps;
        Log.i("ExerciseClass", msg);
    }

}
