package com.example.trainx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MySettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settingsFrame, new MySettingsFragment())
                .commit();
    }
}