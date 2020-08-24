package com.example.trainx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class TrainerActivity extends AppCompatActivity {
    DataManager dataManager;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        setToolbar();
        getBundle();
        prepareRecyclerView();
    }

    private void setToolbar() {
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.trainerToolbar);
        setSupportActionBar(toolbar);
    }

    private void getBundle() {
        Bundle bundle = getIntent().getBundleExtra("Bundle");
        dataManager = (DataManager) bundle.getSerializable("DataManager");
        date = bundle.getString("Date");
    }

    private void prepareRecyclerView(){
        TrainingUnit unit = getTrainingUnit();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerExercises);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Exercise> tabExercise = new ArrayList<>();

        for(Exercise e : unit.getExerciseArrayList())
            tabExercise.add(e);

        ExerciseTrainerAdapter mAdapter = new ExerciseTrainerAdapter(tabExercise);
        recyclerView.setAdapter(mAdapter);
    }
    private TrainingUnit getTrainingUnit(){
        String nameUnit = null;
        String namePlan = null;
        for(TrainingExecution te : dataManager.getTrainingExecutions()){
            if(te.getDate().equals(date)){
                nameUnit = te.getUnit();
                namePlan = te.getPlan();
                break;
            }
        }
        for(TrainingPlan tp : dataManager.getTrainingPlans()){
            if(tp.getName().equals(namePlan)){
                for(TrainingUnit tu : tp.getUnitArrayList()){
                    if(tu.getName().equals(nameUnit)){
                        return tu;
                    }
                }
            }
        }
        return null;
    }
}