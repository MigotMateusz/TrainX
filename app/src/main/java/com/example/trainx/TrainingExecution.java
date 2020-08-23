package com.example.trainx;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class TrainingExecution implements Serializable {
    private String date;
    private String unit;

    public TrainingExecution() {}
    public TrainingExecution(String date, String trainingUnit) {
        this.date = date;
        this.unit = trainingUnit;
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
}
