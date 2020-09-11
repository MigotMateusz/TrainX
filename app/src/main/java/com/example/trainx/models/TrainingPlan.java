package com.example.trainx.models;

import java.io.Serializable;
import java.util.ArrayList;

public class TrainingPlan implements Serializable {
    private String name;
    private String type;
    private boolean isActive;
    private ArrayList<TrainingUnit> unitArrayList;

    public TrainingPlan() {}

    public TrainingPlan(String name, String type, boolean isActive, ArrayList<TrainingUnit> units) {
        this.name = name;
        this.type = type;
        this.isActive = isActive;
        this.unitArrayList = units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TrainingUnit> getUnitArrayList() {
        return unitArrayList;
    }

    public void setUnitArrayList(ArrayList<TrainingUnit> unitArrayList) {
        this.unitArrayList = unitArrayList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getisActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
