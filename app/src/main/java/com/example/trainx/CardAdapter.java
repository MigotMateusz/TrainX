package com.example.trainx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    private ArrayList<TrainingUnit> trainingUnits;

    CardAdapter(ArrayList<TrainingUnit> tu) { trainingUnits = tu; }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardlist_view, parent, false);
        return new MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titleText.setText(trainingUnits.get(position).getName());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.titleText.getContext());
        holder.recyclerViewExercises.setLayoutManager(layoutManager);

        ExerciseAdapter mAdapter = new ExerciseAdapter(trainingUnits.get(position).getExerciseArrayList());
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
