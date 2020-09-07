package com.example.trainx;

import java.io.Serializable;
import java.util.ArrayList;

public class ShuffleTraining implements Serializable {
    private ArrayList<ShuffleTraining> shuffleTrainings;

    public ShuffleTraining() {}

    public ShuffleTraining(ArrayList<ShuffleTraining> shuffleTrainings) {
        this.shuffleTrainings = shuffleTrainings;
    }

    public ArrayList<ShuffleTraining> getShuffleTrainings() {
        return shuffleTrainings;
    }

    public void setShuffleTrainings(ArrayList<ShuffleTraining> shuffleTrainings) {
        this.shuffleTrainings = shuffleTrainings;
    }
}
