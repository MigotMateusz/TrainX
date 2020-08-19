package com.example.trainx;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.MyViewHolder> {
    public ArrayList<Exercise> exercises;
    public ExerciseAdapter(ArrayList<Exercise> myDataset) {
        exercises = myDataset;
    }

    @NonNull
    @Override
    public ExerciseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialTextView v = (MaterialTextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textviewexercise_layout, parent, false);
        return new ExerciseAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        String text = exercise.getName() + " " + exercise.getSets() + "x" + exercise.getReps();
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView textView;
        public MyViewHolder(MaterialTextView v) {
            super(v);
            textView = v;
        }
    }
}
