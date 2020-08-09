package com.example.trainx;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlansFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlansFragment extends Fragment implements NewPlanActivity.DataFromActivityToFragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlansFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlansFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlansFragment newInstance(String param1, String param2) {
        PlansFragment fragment = new PlansFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myView =  inflater.inflate(R.layout.fragment_plans, container, false);


        MaterialTextView materialTextView = (MaterialTextView)myView.findViewById(R.id.NoTrainingText);
        materialTextView.setVisibility(View.GONE);

        ExtendedFloatingActionButton newPlanButton = (ExtendedFloatingActionButton) myView.findViewById(R.id.PlanFloatingButton) ;
        newPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewPlanActivity newPlanActivity = new NewPlanActivity();
                Intent intent = new Intent(getContext(), newPlanActivity.getClass());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
            }
        });

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        fm.beginTransaction();

        Fragment newModule = new PlanModule("Split 1", "Split", true);
        Fragment newModule1 = new PlanModule("FBW 1", "Full Body Workout", false);
        Fragment newModule2 = new PlanModule("Crossfit elements","Full Body Workout",false);
        Fragment newModule3 = new PlanModule("After recovery workout", "Push-Pull-Legs", false);

        ft.add(R.id.LlPlans, newModule);
        ft.add(R.id.LlPlans, newModule1);
        ft.add(R.id.LlPlans, newModule2);
        ft.add(R.id.LlPlans, newModule3);

        ft.commit();

        return myView;
    }

    @Override
    public Fragment sentData(String name, String type, boolean active, ArrayList<String> arrayOfUnits) {
        return new PlanModule(name, type, active);
    }
    public void addToView(Fragment newFragment, FragmentTransaction ft) {
        ft.add(R.id.LlPlans, newFragment);
    }
}