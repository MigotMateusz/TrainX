package com.example.trainx.models;

import java.io.Serializable;
import java.util.ArrayList;

public class FinishedTraining implements Serializable {
    private TrainingExecution trainingExecution;
    private ArrayList<FinishedExercise> exercises;

    public FinishedTraining() {}

    public FinishedTraining(TrainingExecution n, ArrayList<FinishedExercise> e) {
        this.trainingExecution = n;
        this.exercises = e;
    }

    public ArrayList<FinishedExercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<FinishedExercise> exercises) {
        this.exercises = exercises;
    }

    public TrainingExecution getTrainingExecution() {
        return trainingExecution;
    }

    public void setTrainingExecution(TrainingExecution trainingExecution) {
        this.trainingExecution = trainingExecution;
    }
}
