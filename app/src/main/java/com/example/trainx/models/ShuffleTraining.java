package com.example.trainx.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ShuffleTraining implements Serializable {
    private ArrayList<ShuffleExercise> shuffleExercises;

    public ShuffleTraining() {
        this.shuffleExercises = new ArrayList<>();
    }

    public ShuffleTraining(ArrayList<ShuffleExercise> shuffleExercises) {
        this.shuffleExercises = shuffleExercises;
    }

    public ArrayList<ShuffleExercise> getShuffleTrainings() {
        return shuffleExercises;
    }

    public void setShuffleTrainings(ArrayList<ShuffleExercise> shuffleTrainings) {
        this.shuffleExercises = shuffleTrainings;
    }

    public void add(ShuffleExercise exercise) {
        this.shuffleExercises.add(exercise);
    }
}
