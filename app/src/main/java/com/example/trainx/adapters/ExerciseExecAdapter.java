package com.example.trainx.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainx.models.FinishedSet;
import com.example.trainx.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;


public class ExerciseExecAdapter extends RecyclerView.Adapter<ExerciseExecAdapter.MyViewHolder>  {
    public int many;
    private List<FinishedSet> _retData;
    public ExerciseExecAdapter(int myDataset) {
        many = myDataset;
        Log.i("AdapterLog", "Size: " + myDataset);
        _retData = new ArrayList<FinishedSet>(myDataset);
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
        Log.i("AdapterLog", holder1.getRepsInput());
        _retData.add(position, new FinishedSet());
        holder.editTextReps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("AdapterLog", charSequence.toString());
                _retData.get(position).setReps(Integer.parseInt(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        holder.editTextWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("AdapterLog", charSequence.toString());
                _retData.get(position).setWeight(Integer.parseInt(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @Override
    public int getItemCount() {
        return many;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView titleTextView;
        public TextInputEditText editTextReps;
        public TextInputEditText editTextWeight;
        public MyViewHolder(View v) {
            super(v);
            titleTextView = v.findViewById(R.id.titleTextSets);
            editTextReps = v.findViewById(R.id.textInputRepsSetCard);
            editTextWeight = v.findViewById(R.id.textInputWeightSetCard);
        }

        public String getRepsInput() {
            return editTextReps.getText().toString();
        }

        public String getWeightInput() {
            return editTextWeight.getText().toString();
        }
    }
    public List<FinishedSet> retrieveData(){
        return _retData;
    }
}
