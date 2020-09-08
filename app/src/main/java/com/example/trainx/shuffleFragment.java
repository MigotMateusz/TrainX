package com.example.trainx;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textview.MaterialTextView;

public class shuffleFragment extends Fragment {
    private String exerciseName;
    private String nextExerciseName;
    public shuffleFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_shuffle, container, false);
        ShuffleExercise exercise = (ShuffleExercise) getArguments().getSerializable("exercise");
        ShuffleExercise nextExercise = (ShuffleExercise) getArguments().getSerializable("nextExercise");
        ShuffleActivity.LEVEL level = (ShuffleActivity.LEVEL) getArguments().getSerializable("Level");
        MaterialTextView nameText = (MaterialTextView)myView.findViewById(R.id.nameText);
        MaterialTextView nextExerciseText = (MaterialTextView)myView.findViewById(R.id.nextExerciseText);
        MaterialTextView counterText = (MaterialTextView)myView.findViewById(R.id.counterText);
        exerciseName = exercise.getName();
        nameText.setText(exerciseName);
        if(nextExercise != null)
            nextExerciseName = nextExercise.getName();

        if(level == ShuffleActivity.LEVEL.BEGINNER)
            getReadyCounter(nameText, nextExerciseText, counterText, exercise.getBeginner());
            //counter(counterText, exercise.getBeginner());
            //counterText.setText(String.valueOf(exercise.getBeginner()));
        else if(level == ShuffleActivity.LEVEL.INTERMEDIATE)
            getReadyCounter(nameText, nextExerciseText, counterText, exercise.getIntermediate());
            //counter(counterText, exercise.getIntermediate());
            //counterText.setText(String.valueOf(exercise.getIntermediate()));
        else
            getReadyCounter(nameText, nextExerciseText, counterText, exercise.getAthlete());
            //counter(counterText, exercise.getAthlete());
            //counterText.setText(String.valueOf(exercise.getAthlete()));

        return myView;
    }
    private void getReadyCounter(MaterialTextView nameText, MaterialTextView nextExerciseText, MaterialTextView counterText, int sec) {
        nameText.setText("Get ready for next exercise");
        nextExerciseText.setText(exerciseName);
        new CountDownTimer(8000, 1000) {
            public void onTick(long millisUntilFinished) {
                counterText.setText(String.valueOf( (millisUntilFinished / 1000)));
            }
            public void onFinish(){
                nameText.setText(exerciseName);
                nextExerciseText.setText(nextExerciseName);
                counter(counterText, sec);
            }
        }.start();

    }
    private void counter(MaterialTextView counterText, int sec) {
        new CountDownTimer((sec+1)*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                counterText.setText(String.valueOf( (millisUntilFinished / 1000)));
            }
            public void onFinish(){
                counterText.setText("Finished");
                synchronized (ShuffleActivity.isComplete) {
                    ShuffleActivity.isComplete.notify();
                }
            }
        }.start();
    }

}