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

    private void refreshLayout(LEVEL level) {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameShuffle);
        //int i = 0;
        /*for(ShuffleExercise exercise : dataManager.getShuffleTrainings().get(0).getShuffleTrainings()) {
            while (previousTimerActive);
            Fragment newShuffle = new shuffleFragment();
            Bundle exerciseBundle = new Bundle();
            exerciseBundle.putSerializable("exercise", exercise);
            Log.i("refreshLog", exercise.getName());
            exerciseBundle.putSerializable("Level", level);
            if(i + 1 < dataManager.getShuffleTrainings().get(0).getShuffleTrainings().size())
                exerciseBundle.putSerializable("nextExercise", dataManager.getShuffleTrainings().get(0).getShuffleTrainings().get(i + 1));
            newShuffle.setArguments(exerciseBundle);
            Log.i("refreshLog", "removeAllViews");
            frameLayout.removeAllViews();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameShuffle, newShuffle).commit();
            previousTimerActive = true;
        }*/
        for(int i = 0; i < dataManager.getShuffleTrainings().get(0).getShuffleTrainings().size(); i++) {
            frameLayout.removeAllViews();
            int next_index = i + 1;
            ShuffleExercise currentExercise = dataManager.getShuffleTrainings().get(0).getShuffleTrainings().get(i);
            mHandler = new Handler();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    shuffleFragment newShuffle = new shuffleFragment();
                    Bundle exerciseBundle = new Bundle();
                    exerciseBundle.putSerializable("exercise", currentExercise);
                    Log.i("refreshLog", currentExercise.getName());
                    exerciseBundle.putSerializable("Level", level);
                    if(next_index < dataManager.getShuffleTrainings().get(0).getShuffleTrainings().size())
                        exerciseBundle.putSerializable("nextExercise", dataManager.getShuffleTrainings().get(0).getShuffleTrainings().get(next_index));
                    newShuffle.setArguments(exerciseBundle);
                    //frameLayout.removeAllViews();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.popBackStack();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameShuffle, newShuffle);
                    fragmentTransaction.addToBackStack("StartShuffleFragment");
                    fragmentTransaction.commit();
                    //getSupportFragmentManager().beginTransaction()
                    //       .replace(R.id.frameShuffle, newShuffle).commit();
                }
            });
            synchronized (isComplete) {
                try {
                    Log.i("refreshLog", "Waiting for fragment");
                    isComplete.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.i("refreshLog", "Got signal from fragment");
        }
    }


    public enum LEVEL {
        BEGINNER, INTERMEDIATE, ATHLETE;
    }
}