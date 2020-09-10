package com.example.trainx.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainx.models.Exercise;
import com.example.trainx.R;
import com.example.trainx.models.TrainingUnit;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    private ArrayList<TrainingUnit> trainingUnits;

    public CardAdapter(ArrayList<TrainingUnit> tu) { trainingUnits = tu; }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardlist_view, parent, false);
        return new MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TrainingUnit currentTrainingUnit = trainingUnits.get(position);
        String trainingName = currentTrainingUnit.getName();

        holder.titleText.setText(trainingName);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.titleText.getContext());
        holder.recyclerViewExercises.setLayoutManager(layoutManager);

        ArrayList<Exercise> exercises = currentTrainingUnit.getExerciseArrayList();
        ExerciseAdapter mAdapter = new ExerciseAdapter(exercises);
        holder.recyclerViewExercises.setAdapter(mAdapter);
    }

    @Override
    public int getItemCount() {
        return trainingUnits.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView titleText;
        public RecyclerView recyclerViewExercises;

        public MyViewHolder(@NonNull View myView){
            super(myView);
            titleText = myView.findViewById(R.id.cardRecyclerTitle);
            recyclerViewExercises = myView.findViewById(R.id.cardsInnerRecyclerView);
        }
    }
}
