package com.example.trainx.interfaces;

import java.text.ParseException;

public interface OnOverviewDataReceive {
    void onDataReceived(String date, double weight, int strike, String lastDate) throws ParseException;
}
