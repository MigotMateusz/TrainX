package com.example.trainx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

public class MeasurementsAdapter extends RecyclerView.Adapter<MeasurementsAdapter.MyViewHolder>  {

    String[] names;
    MeasurementsAdapter(String[] dataSet) {
        this.names = dataSet;
    }

    @NonNull
    @Override
    public MeasurementsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.measure_card_recycler_layout, parent, false);
        return new MeasurementsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MeasurementsAdapter.MyViewHolder holder, int position) {
        holder.nameMeasureText.setText(names[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView nameMeasureText;
        public MaterialTextView dateText;
        public MyViewHolder(View v) {
            super(v);
            nameMeasureText = (MaterialTextView) v.findViewById(R.id.nameMeasureText);

        }
    }
}
