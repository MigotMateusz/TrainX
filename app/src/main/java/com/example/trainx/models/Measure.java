package com.example.trainx.models;

import java.io.Serializable;

public class Measure implements Serializable {
    private String date;
    private double value;

    public Measure() {}

    public Measure(String d, double v) {
        this.date = d;
        this.value = v;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
