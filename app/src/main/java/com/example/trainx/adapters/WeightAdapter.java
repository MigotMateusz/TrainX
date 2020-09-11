package com.example.trainx.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainx.R;
import com.example.trainx.models.Weight;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.MyViewHolder> {

    public ArrayList<Weight> weights;
    public WeightAdapter(ArrayList<Weight> myDataSet) {
        weights = myDataSet;
    }

    @NonNull
    @Override
    public WeightAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weightdatalayout, parent, false);
        return new MyViewHolder((LinearLayout)v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeightAdapter.MyViewHolder holder, int position) {
        Weight weight = weights.get(position);
        String date = weight.getDate();
        String value = weight.getWeight() + " kg";
        holder.valueText.setText(value);
        holder.dateText.setText(date);
    }

    @Override
    public int getItemCount() {
        return weights.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView valueText;
        public MaterialTextView dateText;
        public MyViewHolder(LinearLayout v) {
            super(v);
            valueText = v.findViewById(R.id.valueText);
            dateText = v.findViewById(R.id.dateText);
        }
    }
}
