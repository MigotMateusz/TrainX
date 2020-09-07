package com.example.trainx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class ShuffleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuffle);
        prepareBeginnerLevel();
    }

    private void prepareBeginnerLevel() {
        MaterialButton beginnerButton = (MaterialButton) findViewById(R.id.beginnerButton);
        beginnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShuffleActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                refreshLayout();
            }
        });
    }

    private void prepareIntermediateLevel() {
        MaterialButton intermediateButton = (MaterialButton) findViewById(R.id.intermediateButton);
        intermediateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void prepareAthleteLevel() {
        MaterialButton athleteButton = (MaterialButton) findViewById(R.id.athleteButton);
        athleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void refreshLayout() {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameShuffle);
        frameLayout.removeAllViews();
        Fragment newShuffle = new shuffleFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameShuffle, newShuffle).commit();
    }

}