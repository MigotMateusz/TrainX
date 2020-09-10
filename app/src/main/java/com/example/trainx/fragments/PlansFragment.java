package com.example.trainx.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainx.activities.NewPlanActivity;
import com.example.trainx.R;
import com.example.trainx.data.DataManager;
import com.example.trainx.models.TrainingPlan;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;


public class PlansFragment extends Fragment implements NewPlanActivity.DataFromActivityToFragment {
    DataManager dataManager;
    ArrayList<TrainingPlan> trainingPlans;

    public PlansFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataManager = (DataManager) getArguments().getSerializable("DataManager");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View myView =  inflater.inflate(R.layout.fragment_plans, container, false);

        MaterialTextView materialTextView = (MaterialTextView)myView.findViewById(R.id.NoTrainingText);
        materialTextView.setVisibility(View.GONE);

        ExtendedFloatingActionButton newPlanButton = (ExtendedFloatingActionButton) myView.findViewById(R.id.PlanFloatingButton) ;
        newPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewPlanActivity newPlanActivity = new NewPlanActivity();
                Intent intent = new Intent(getContext(), newPlanActivity.getClass());
                Bundle bundleData = new Bundle();
                bundleData.putSerializable("DataManager1", dataManager);
                intent.putExtra("DataManager", bundleData);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
            }
        });

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        fm.beginTransaction();

        if(dataManager!=null){
            loadPlans(dataManager, ft);
        }
        ft.commit();

        return myView;
    }

    @Override
    public Fragment sentData(TrainingPlan trainingPlan) {
        String name = trainingPlan.getName();
        String type = trainingPlan.getType();
        boolean active = trainingPlan.getisActive();
        Fragment newModule = new PlanModule(name, type, active);
        Bundle arguments = new Bundle();
        arguments.putSerializable("DataManager", dataManager);
        newModule.setArguments(arguments);
        return newModule;
    }
    public void addToView(Fragment newFragment, FragmentTransaction ft) {
        ft.add(R.id.LlPlans, newFragment);
    }
    public void loadPlans(DataManager dataManager, FragmentTransaction ft) {

        for(TrainingPlan tp : dataManager.getTrainingPlans()) {
            String title = tp.getName();
            String type = tp.getType();
            boolean isActive = tp.getisActive();
            Fragment newModule = new PlanModule(title, type, isActive);
            Bundle arguments = new Bundle();
            arguments.putSerializable("DataManager", dataManager);
            newModule.setArguments(arguments);
            ft.add(R.id.LlPlans, newModule);
        }
    }

}