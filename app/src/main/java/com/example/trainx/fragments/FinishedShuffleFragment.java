package com.example.trainx.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainx.R;
import com.example.trainx.activities.MainActivity;
import com.google.android.material.button.MaterialButton;

public class FinishedShuffleFragment extends Fragment {
    public FinishedShuffleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView =  inflater.inflate(R.layout.fragment_finished_shuffle, container, false);
        prepareButton(myView);
        return myView;
    }
    private void prepareButton(View view) {
        MaterialButton goBackButton = (MaterialButton) view.findViewById(R.id.goBackMainScreenButton);
        goBackButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });
    }
}