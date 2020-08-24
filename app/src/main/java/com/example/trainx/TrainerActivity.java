package com.example.trainx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

public class TrainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        setToolbar();
        getBundle();
    }

    private void setToolbar() {
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.trainerToolbar);
        setSupportActionBar(toolbar);
    }

    private void getBundle() {
        Bundle bundle = getIntent().getBundleExtra("Bundle");
        Toast.makeText(this, bundle.getString("Date"), Toast.LENGTH_SHORT).show();
    }

}