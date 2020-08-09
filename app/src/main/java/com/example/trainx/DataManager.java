package com.example.trainx;

import java.util.ArrayList;

public class DataManager {
    private ArrayList<TrainingPlan> trainingPlans;

    public DataManager() {
        trainingPlans = new ArrayList<TrainingPlan>();
    }

    public static DataManager INSTANCE;

    public static DataManager getInstance() {
        if(INSTANCE == null)
            INSTANCE = new DataManager();
        return INSTANCE;
    }

    public ArrayList<TrainingPlan> getTrainingPlans() {
        return trainingPlans;
    }

    public void setTrainingPlans(ArrayList<TrainingPlan> trainingPlans) {
        this.trainingPlans = trainingPlans;
    }

    public void addToTrainingList(TrainingPlan tp) {
        trainingPlans.add(tp);
    }

    public void deleteFromTrainingList(TrainingPlan tp){
        trainingPlans.remove(tp);
    }
}
