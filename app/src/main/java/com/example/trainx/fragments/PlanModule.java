package com.example.trainx.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.trainx.R;
import com.example.trainx.activities.MainActivity;
import com.example.trainx.data.DataManager;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;


public class PlanModule extends Fragment implements View.OnClickListener {
    public Button activeButton;
    public MaterialTextView titleText;
    public MaterialTextView structureText;
    private MaterialCardView materialCardView;

    private String titleString;
    private String structureString;
    private boolean isActive;

    public PlanModule() {
    }

    public PlanModule(String title, String structure, boolean isActive) {
        this.titleString = title;
        this.structureString = structure;
        this.isActive = isActive;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_plan_module, container, false);

        initUIElements(myView);

        return myView;
    }

    @Override
    public void onClick(View view) {

    }

    private void initUIElements(View myView){
        materialCardView = myView.findViewById(R.id.PlanCardView);
        activeButton = myView.findViewById(R.id.ActiveFlag);
        titleText = myView.findViewById(R.id.PlanTitleText);
        structureText = myView.findViewById(R.id.PlanStructureText);

        if(!isActive)
            activeButton.setVisibility(View.GONE);

        titleText.setText(titleString);
        structureText.setText(structureString);
        prepareMaterialCard();
    }

    private void prepareMaterialCard(){
        materialCardView.setOnClickListener(view -> ((MainActivity)getActivity()).replaceFragment(titleString));

        materialCardView.setOnLongClickListener(view -> {
            materialCardView.setChecked(true);
            openDialog();
            return true;
        });
    }

    private void openDialog(){
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Delete Training Plan")
                .setMessage("Are you sure to delete selected training plan?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        materialCardView.setChecked(false);
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        assert getArguments() != null;
                        DataManager dataManager = (DataManager) getArguments().getSerializable("DataManager");
                        assert dataManager != null;
                        dataManager.deleteFromTrainingList(titleString);
                        materialCardView.setVisibility(View.GONE);
                    }
                })
                .show();
    }
}