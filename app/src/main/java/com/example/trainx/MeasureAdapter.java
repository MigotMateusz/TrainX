package com.example.trainx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.security.MessageDigest;
import java.util.ArrayList;

public class MeasureAdapter extends RecyclerView.Adapter<MeasureAdapter.MyViewHolder> {

    public ArrayList<Measure> measures;
    public MeasureAdapter(ArrayList<Measure> myDataset) {
        measures = myDataset;
    }

    @NonNull
    @Override
    public MeasureAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weightdatalayout, parent, false);
        MyViewHolder vh = new MyViewHolder((LinearLayout)v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MeasureAdapter.MyViewHolder holder, int position) {
        Measure measure = measures.get(position);
        String date = measure.getDate();
        String value = measure.getValue() + " cm";
        holder.valueText.setText(value);
        holder.dateText.setText(date);
    }

    @Override
    public int getItemCount() {
        return measures.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView valueText;
        public MaterialTextView dateText;
        public MyViewHolder(LinearLayout v) {
            super(v);
            valueText = (MaterialTextView) v.findViewById(R.id.valueText);
            dateText = (MaterialTextView) v.findViewById(R.id.dateText);
        }
    }
}

