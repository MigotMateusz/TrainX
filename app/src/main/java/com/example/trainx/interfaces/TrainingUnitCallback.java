package com.example.trainx.interfaces;

import com.example.trainx.models.Exercise;

import java.util.ArrayList;

public interface TrainingUnitCallback {
    void onCallback(String namePlan, ArrayList<Exercise> exerciseArray);
}
