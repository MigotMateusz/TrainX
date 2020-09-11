package com.example.trainx.models;

import java.io.Serializable;

public class TrainingExecution implements Serializable{
    private String date;
    private String unit;
    private String plan;

    public TrainingExecution() {}

    public TrainingExecution(String date, String trainingUnit, String plan) {
        this.date = date;
        this.unit = trainingUnit;
        this.plan = plan;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

}
