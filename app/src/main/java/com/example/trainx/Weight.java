package com.example.trainx;

import java.io.Serializable;

public class Weight implements Serializable {
    private double weight;
    private String date;

    public Weight() {}

    public Weight(double w, String d) {
        this.weight = w;
        this.date = d;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
