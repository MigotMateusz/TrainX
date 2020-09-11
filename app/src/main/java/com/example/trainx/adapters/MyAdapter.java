package com.example.trainx.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainx.R;
import com.google.android.material.textview.MaterialTextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView textView;
        public MyViewHolder(MaterialTextView v) {
            super(v);
            textView = v;
        }
    }

    public MyAdapter(String[] myDataSet) {
        mDataSet = myDataSet;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialTextView v = (MaterialTextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textview_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(mDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
