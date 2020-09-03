package com.example.trainx;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDataBase extends Application {
    @Override
    public void onCreate() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        super.onCreate();
    }
}
