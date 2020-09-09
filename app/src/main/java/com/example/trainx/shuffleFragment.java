package com.example.trainx;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class shuffleFragment extends Fragment {
    private String exerciseName;
    private String nextExerciseName;
    private int position;
    private ShuffleActivity.LEVEL level;
    private ArrayList<ShuffleExercise> shuffleExercises;
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
        level = (ShuffleActivity.LEVEL) getArguments().getSerializable("Level");
        position = getArguments().getInt("position");
        position++;
        shuffleExercises = (ArrayList<ShuffleExercise>) getArguments().getSerializable("exerciseArray");
        Log.i("ShuffleLog", String.valueOf(position));
        MaterialTextView nameText = (MaterialTextView)myView.findViewById(R.id.nameText);
        MaterialTextView nextExerciseText = (MaterialTextView)myView.findViewById(R.id.nextExerciseText);
        MaterialTextView counterText = (MaterialTextView)myView.findViewById(R.id.counterText);
        exerciseName = exercise.getName();
        nameText.setText(exerciseName);
        if(nextExercise != null)
            nextExerciseName = nextExercise.getName();

        if(level == ShuffleActivity.LEVEL.BEGINNER)
            getReadyCounter(nameText, nextExerciseText, counterText, exercise.getBeginner());
        else if(level == ShuffleActivity.LEVEL.INTERMEDIATE)
            getReadyCounter(nameText, nextExerciseText, counterText, exercise.getIntermediate());
        else
            getReadyCounter(nameText, nextExerciseText, counterText, exercise.getAthlete());

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
                refreshLayout();
            }
        }.start();
    }

    private void refreshLayout(){
        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.frameShuffle);
        frameLayout.removeAllViews();
        int next_index = position + 1;
        ShuffleExercise currentExercise;
        if(position < shuffleExercises.size()){
            currentExercise = shuffleExercises.get(position);
            shuffleFragment newShuffle = new shuffleFragment();
            Bundle exerciseBundle = new Bundle();
            exerciseBundle.putSerializable("exercise", currentExercise);
            exerciseBundle.putSerializable("exerciseArray", shuffleExercises);
            exerciseBundle.putInt("position", position);
            exerciseBundle.putSerializable("Level", level);
            if(next_index < shuffleExercises.size())
                exerciseBundle.putSerializable("nextExercise", shuffleExercises.get(next_index));
            newShuffle.setArguments(exerciseBundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameShuffle, newShuffle).commit();
        } else {
            FinishedShuffleFragment finishedShuffleFragment = new FinishedShuffleFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameShuffle, finishedShuffleFragment).commit();
        }

    }

}