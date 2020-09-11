package com.example.trainx.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.trainx.data.DataManager;
import com.example.trainx.models.Exercise;
import com.example.trainx.models.FinishedExercise;
import com.example.trainx.models.FinishedSet;
import com.example.trainx.models.FinishedTraining;
import com.example.trainx.R;
import com.example.trainx.adapters.ExerciseExecAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ExerciseFragment extends Fragment {
    private Exercise exercise;
    private int nextPosition;
    ArrayList<Exercise> arrayExercises;
    private ExerciseExecAdapter mAdapter;
    public ExerciseFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_exercise, container, false);

        initData();
        setExerciseNameText(myView);
        prepareRecyclerView(myView);
        prepareNextButton(myView);

        return myView;
    }

    private void prepareNextButton(View view) {
        MaterialButton nextButton = view.findViewById(R.id.nextExerciseButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FinishedExercise newFinishedExercise = new FinishedExercise(exercise.getName(), (ArrayList<FinishedSet>) mAdapter.retrieveData());
                FinishedTraining currentFinishedTraining = (FinishedTraining) getArguments().getSerializable("FinishedTraining");
                currentFinishedTraining.getExercises().add(newFinishedExercise);
                if(nextPosition < arrayExercises.size()){
                    Bundle arguments = setArguments(currentFinishedTraining);
                    ExerciseFragment exerciseFragment = new ExerciseFragment();
                    exerciseFragment.setArguments(arguments);
                    transitionToNextFragment(exerciseFragment);
                }
                else
                   transitionToFinishedTrainingFragment(currentFinishedTraining);

            }
        });
    }

    private Bundle setArguments(FinishedTraining currentFinishedTraining) {
        Bundle bundle = getArguments();
        Exercise nextExercise = arrayExercises.get(nextPosition);
        bundle.putSerializable("Exercise", nextExercise);
        bundle.putSerializable("DataManager", getArguments().getSerializable("DataManager"));
        bundle.putSerializable("FinishedTraining", currentFinishedTraining);
        bundle.putSerializable("ExerciseArray", arrayExercises);
        bundle.putInt("Position", nextPosition);
        return bundle;
    }

    private void transitionToFinishedTrainingFragment(FinishedTraining currentFinishedTraining) {
        FrameLayout frameLayout = getActivity().findViewById(R.id.frameLayoutExec);
        frameLayout.removeAllViews();
        FinishedTrainingFragment finishedTrainingFragment = new FinishedTrainingFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.pull_in_left, R.anim.pull_out_right);
        ft.add(R.id.frameLayoutExec, finishedTrainingFragment).commit();
        DataManager dataManager = (DataManager) getArguments().getSerializable("DataManager");
        dataManager.addSingleFinishedTraining(currentFinishedTraining);
    }

    private void transitionToNextFragment(ExerciseFragment exerciseFragment) {
        FrameLayout frameLayout = getActivity().findViewById(R.id.frameLayoutExec);
        frameLayout.removeAllViews();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.pull_in_left, R.anim.pull_out_right);
        ft.add(R.id.frameLayoutExec, exerciseFragment).commit();
    }

    private void setExerciseNameText(View view) {
        MaterialTextView exerciseNameTextView = view.findViewById(R.id.titleNameTextView);
        exerciseNameTextView.setText(exercise.getName());
    }

    private void initData() {
        exercise = (Exercise) getArguments().getSerializable("Exercise");
        nextPosition = getArguments().getInt("Position") + 1;
        arrayExercises = (ArrayList<Exercise>) getArguments().getSerializable("ExerciseArray");
    }

    private void prepareRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerSets);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ExerciseExecAdapter(exercise.getSets());
        recyclerView.setAdapter(mAdapter);
    }
}