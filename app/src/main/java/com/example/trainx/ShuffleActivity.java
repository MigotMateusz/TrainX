package com.example.trainx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class ShuffleActivity extends AppCompatActivity {
    private DataManager dataManager;
    public static Boolean isComplete = new Boolean(true);
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuffle);
        dataManager = (DataManager)getIntent().getBundleExtra("bundle").getSerializable("dataManager");
        MaterialToolbar toolbar = findViewById(R.id.toolbarShuffle);
        setSupportActionBar(toolbar);
        prepareBeginnerLevel();
        prepareIntermediateLevel();
        prepareAthleteLevel();
    }

    private void prepareBeginnerLevel() {
        MaterialButton beginnerButton = (MaterialButton) findViewById(R.id.beginnerButton);
        beginnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshLayout(LEVEL.BEGINNER);
            }
        });
    }

    private void prepareIntermediateLevel() {
        MaterialButton intermediateButton = (MaterialButton) findViewById(R.id.intermediateButton);
        intermediateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshLayout(LEVEL.INTERMEDIATE);
            }
        });
    }

    private void prepareAthleteLevel() {
        MaterialButton athleteButton = (MaterialButton) findViewById(R.id.athleteButton);
        athleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshLayout(LEVEL.ATHLETE);
            }
        });
    }

    public void refreshLayout(LEVEL level) {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameShuffle);
        frameLayout.removeAllViews();
        int next_index = 1;
        ShuffleExercise currentExercise = dataManager.getShuffleTrainings().get(0).getShuffleTrainings().get(0);
        shuffleFragment newShuffle = new shuffleFragment();
        Bundle exerciseBundle = new Bundle();
        exerciseBundle.putSerializable("exercise", currentExercise);
        exerciseBundle.putSerializable("exerciseArray", dataManager.getShuffleTrainings().get(0).getShuffleTrainings());
        exerciseBundle.putInt("position", 0);
        exerciseBundle.putSerializable("Level", level);
        if(next_index < dataManager.getShuffleTrainings().get(0).getShuffleTrainings().size())
            exerciseBundle.putSerializable("nextExercise", dataManager.getShuffleTrainings().get(0).getShuffleTrainings().get(next_index));
        newShuffle.setArguments(exerciseBundle);
        getSupportFragmentManager().beginTransaction()
               .replace(R.id.frameShuffle, newShuffle).commit();


    }

    public enum LEVEL {
        BEGINNER, INTERMEDIATE, ATHLETE;
    }
}