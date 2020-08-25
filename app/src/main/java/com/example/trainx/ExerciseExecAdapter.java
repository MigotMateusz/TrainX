package com.example.trainx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

public class ExerciseExecAdapter extends RecyclerView.Adapter<ExerciseExecAdapter.MyViewHolder>  {
    public int many;
    public ExerciseExecAdapter(int myDataset) {
        many = myDataset;
    }

    @NonNull
    @Override
    public ExerciseExecAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.setcardview_layout, parent, false);
        return new ExerciseExecAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseExecAdapter.MyViewHolder holder, int position) {
        MyViewHolder holder1 = holder;
        holder1.titleTextView.setText((position+1) + " Sets");
    }

    @Override
    public int getItemCount() {
        return many;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView titleTextView;
        public MyViewHolder(View v) {
            super(v);
            titleTextView = v.findViewById(R.id.titleTextSets);
        }
    }

}
