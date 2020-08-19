package com.example.trainx;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView textView;
        public MyViewHolder(MaterialTextView v) {
            super(v);
            textView = v;
        }
    }

    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialTextView v = (MaterialTextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textview_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public void addData(ArrayList<String> list, String newData) {
        list.add(newData);
        this.notifyDataSetChanged();
    }
}
