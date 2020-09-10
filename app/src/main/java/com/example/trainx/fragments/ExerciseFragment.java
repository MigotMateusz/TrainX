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
        final int nextPosition = getArguments().getInt("Position") + 1;
        ArrayList<Exercise> arrayExercises = (ArrayList<Exercise>) getArguments().getSerializable("ExerciseArray");
        MaterialTextView exerciseNameTextView = myView.findViewById(R.id.titleNameTextView);

        exerciseNameTextView.setText(exercise.getName());

        RecyclerView recyclerView = myView.findViewById(R.id.recyclerSets);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        ExerciseExecAdapter mAdapter = new ExerciseExecAdapter(exercise.getSets());
        recyclerView.setAdapter(mAdapter);


        MaterialButton nextButton = myView.findViewById(R.id.nextExerciseButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FinishedExercise newExercise = new FinishedExercise(exercise.getName(), (ArrayList<FinishedSet>) mAdapter.retrieveData());
                FinishedTraining currentFinishedTraining = (FinishedTraining) getArguments().getSerializable("FinishedTraining");
                currentFinishedTraining.getExercises().add(newExercise);
                if(nextPosition < arrayExercises.size()){
                Bundle bundle = getArguments();
                Exercise nextExercise = arrayExercises.get(nextPosition);
                bundle.putSerializable("Exercise", nextExercise);
                bundle.putSerializable("DataManager", getArguments().getSerializable("DataManager"));
                bundle.putSerializable("FinishedTraining", currentFinishedTraining);
                bundle.putSerializable("ExerciseArray", arrayExercises);
                bundle.putInt("Position", nextPosition);
                ExerciseFragment exerciseFragment = new ExerciseFragment();
                exerciseFragment.setArguments(bundle);
                FrameLayout frameLayout = getActivity().findViewById(R.id.frameLayoutExec);
                frameLayout.removeAllViews();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.pull_in_left, R.anim.pull_out_right);
                ft.add(R.id.frameLayoutExec, exerciseFragment).commit();
                }
                else {
                    FrameLayout frameLayout = getActivity().findViewById(R.id.frameLayoutExec);
                    frameLayout.removeAllViews();
                    FinishedTrainingFragment finishedTrainingFragment = new FinishedTrainingFragment();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.pull_in_left, R.anim.pull_out_right);
                    ft.add(R.id.frameLayoutExec, finishedTrainingFragment).commit();


                    /*Log.i("FinishedTraining", currentFinishedTraining.getName());
                    for(FinishedExercise ex : currentFinishedTraining.getExercises()){
                        Log.i("FinishedTraining", ex.getName());
                        for(FinishedSet set : ex.getSets()){
                            Log.i("FinishedTraining", set.getReps() + " " +set.getWeight());
                        }
                    }*/
                    DataManager dataManager = (DataManager) getArguments().getSerializable("DataManager");
                    dataManager.addSingleFinishedTraining(currentFinishedTraining);
                    //dataManager

                }
            }
        });

        return myView;
    }
}
/*
int position = 0;
        Exercise e = currentTraining.getExerciseArrayList().get(position);
        ExerciseFragment exerciseFragment = new ExerciseFragment();
        Bundle bundle = getIntent().getExtras();
        bundle.putSerializable("Exercise", e);
        bundle.putSerializable("ExerciseArray", currentTraining.getExerciseArrayList());
        bundle.putInt("Position", position);
        exerciseFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayoutExec, exerciseFragment).commit();
 */