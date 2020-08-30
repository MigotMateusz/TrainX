package com.example.trainx;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class OverviewFragment extends Fragment {

    public OverviewFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_overview, container, false);
        MaterialButton button = myView.findViewById(R.id.startTrainingButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataManager dataManager = (DataManager) getArguments().getSerializable("DataManager");
                if(dataManager.isTrainingDone())
                    Toast.makeText(getActivity(), "Training is done already", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(getContext(), TrainingExecActivity.class);
                    Bundle bundle = getArguments();
                    intent.putExtra("DataManager", bundle);
                    startActivity(intent);
                }

            }
        });
        return myView;
    }
}