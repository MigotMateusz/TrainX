package com.example.trainx;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public boolean after(Object o) {
        if(this == o)
            return false;
        if(o == null || getClass() != o.getClass())
            return false;
        TrainingExecution te = (TrainingExecution) o;
        try {
            Date thisDate = new SimpleDateFormat("yyyy-MM-dd").parse(this.getDate());
            Date oDate = new SimpleDateFormat("yyyy-MM-dd").parse(te.getDate());
            return thisDate.after(oDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

}
