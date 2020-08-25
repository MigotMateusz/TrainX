package com.example.trainx;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ExerciseFragment extends Fragment {
    public ExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_exercise, container, false);

        Exercise exercise = (Exercise) getArguments().getSerializable("Exercise");
        MaterialTextView exerciseNameTextView = myView.findViewById(R.id.titleNameTextView);

        exerciseNameTextView.setText(exercise.getName());

        RecyclerView recyclerView = myView.findViewById(R.id.recyclerSets);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        //for(int i = 0;  i < exercise.getSets(); i++)
        //    tabExercise.add(e);

        ExerciseExecAdapter mAdapter = new ExerciseExecAdapter(exercise.getSets());
        recyclerView.setAdapter(mAdapter);

        return myView;
    }
}