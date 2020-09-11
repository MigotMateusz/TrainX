package com.example.trainx.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.trainx.R;
import com.example.trainx.data.DataManager;
import com.example.trainx.fragments.shuffleFragment;
import com.example.trainx.models.ShuffleExercise;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class ShuffleActivity extends AppCompatActivity {
    private DataManager dataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuffle);
        dataManager = (DataManager) Objects.requireNonNull(getIntent().getBundleExtra("bundle")).getSerializable("dataManager");
        prepareUI();
    }

    private void prepareBeginnerLevel() {
        MaterialButton beginnerButton = findViewById(R.id.beginnerButton);
        beginnerButton.setOnClickListener(view -> refreshLayout(LEVEL.BEGINNER));
    }

    private void prepareIntermediateLevel() {
        MaterialButton intermediateButton = findViewById(R.id.intermediateButton);
        intermediateButton.setOnClickListener(view -> refreshLayout(LEVEL.INTERMEDIATE));
    }

    private void prepareAthleteLevel() {
        MaterialButton athleteButton = findViewById(R.id.athleteButton);
        athleteButton.setOnClickListener(view -> refreshLayout(LEVEL.ATHLETE));
    }

    public void refreshLayout(LEVEL level) {
        FrameLayout frameLayout = findViewById(R.id.frameShuffle);
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

    private void prepareUI() {
        MaterialToolbar toolbar = findViewById(R.id.toolbarShuffle);
        setSupportActionBar(toolbar);
        prepareBeginnerLevel();
        prepareIntermediateLevel();
        prepareAthleteLevel();
    }

    public enum LEVEL {
        BEGINNER, INTERMEDIATE, ATHLETE
    }
}