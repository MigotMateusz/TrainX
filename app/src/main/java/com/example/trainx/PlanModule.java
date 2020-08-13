package com.example.trainx;


import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanModule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanModule extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Button activeButton;
    public MaterialTextView titleText;
    public MaterialTextView structureText;

    private String titleString;
    private String structureString;
    private boolean isActive;

    public PlanModule() {
        // Required empty public constructor
    }
    public PlanModule(String title, String structure, boolean isActive) {
        this.titleString = title;
        this.structureString = structure;
        this.isActive = isActive;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanModule.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanModule newInstance(String param1, String param2) {
        PlanModule fragment = new PlanModule();
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
        View myView = inflater.inflate(R.layout.fragment_plan_module, container, false);

        MaterialCardView materialCardView = myView.findViewById(R.id.PlanCardView);
        activeButton = myView.findViewById(R.id.ActiveFlag);
        titleText = myView.findViewById(R.id.PlanTitleText);
        structureText = myView.findViewById(R.id.PlanStructureText);

        if(!isActive)
            activeButton.setVisibility(View.GONE);

        titleText.setText(titleString);
        structureText.setText(structureString);

        materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(titleString);
            }
        });
        return myView;
    }

    @Override
    public void onClick(View view) {

    }
}