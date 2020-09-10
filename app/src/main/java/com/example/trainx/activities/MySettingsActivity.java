package com.example.trainx.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.trainx.R;
import com.example.trainx.fragments.MySettingsFragment;
import com.google.android.material.appbar.MaterialToolbar;

public class MySettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        MaterialToolbar toolbar = findViewById(R.id.settingToolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settingsFrame, new MySettingsFragment())
                .commit();
    }
}