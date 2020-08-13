package com.example.trainx;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class PlanStructureFragment extends Fragment {

    private RecyclerView recyclerView;
    private CardAdapter mAdapter;
    ArrayList<TrainingUnit> trainingUnits;

    public PlanStructureFragment() {}

    public static PlanStructureFragment newInstance(String param1, String param2) {
        PlanStructureFragment fragment = new PlanStructureFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View myView =  inflater.inflate(R.layout.fragment_plan_structure, container, false);

        recyclerView = (RecyclerView) myView.findViewById(R.id.cardsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        String title = getArguments().getString("title");
        DataManager dataManager = (DataManager) getArguments().getSerializable("DataManager");
        Log.i("Card", title);
        for(TrainingPlan tp : dataManager.getTrainingPlans()) {
            if(tp.getName().equals(title)){
                trainingUnits = tp.getUnitArrayList();
                break;
            }
        }
        mAdapter = new CardAdapter(trainingUnits);
        recyclerView.setAdapter(mAdapter);
        for(TrainingUnit tu : trainingUnits) {
            Log.i("Card-UnitTitle", tu.getName());
            for(Exercise ex : tu.getExerciseArrayList()){
                Log.i("Card-Exercise", ex.getName() + " " + ex.getSets() + "x" + ex.getReps());
            }
        }

        return myView;
    }
}