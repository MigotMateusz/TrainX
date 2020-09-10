package com.example.trainx.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.trainx.R;
import com.example.trainx.adapters.CardAdapter;
import com.example.trainx.data.DataManager;
import com.example.trainx.models.TrainingPlan;
import com.example.trainx.models.TrainingUnit;

import java.util.ArrayList;

public class PlanStructureFragment extends Fragment {

    ArrayList<TrainingUnit> trainingUnits;

    public PlanStructureFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView =  inflater.inflate(R.layout.fragment_plan_structure, container, false);

        RecyclerView recyclerView = (RecyclerView) myView.findViewById(R.id.cardsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        String title = getArguments().getString("title");
        DataManager dataManager = (DataManager) getArguments().getSerializable("DataManager");
        for(TrainingPlan tp : dataManager.getTrainingPlans()) {
            if(tp.getName().equals(title)){
                trainingUnits = tp.getUnitArrayList();
                break;
            }
        }
        CardAdapter mAdapter = new CardAdapter(trainingUnits);
        recyclerView.setAdapter(mAdapter);

        return myView;
    }
}