package com.example.trainx.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.trainx.R;
import com.example.trainx.adapters.ExerciseTrainerAdapter;
import com.example.trainx.data.DataManager;
import com.example.trainx.models.Exercise;
import com.example.trainx.models.TrainingExecution;
import com.example.trainx.models.TrainingPlan;
import com.example.trainx.models.TrainingUnit;
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
        MaterialToolbar toolbar = findViewById(R.id.trainerToolbar);
        setSupportActionBar(toolbar);
    }

    private void getBundle() {
        Bundle bundle = getIntent().getBundleExtra("Bundle");
        assert bundle != null;
        dataManager = (DataManager) bundle.getSerializable("DataManager");
        date = bundle.getString("Date");
    }

    private void prepareRecyclerView(){
        TrainingUnit unit = getTrainingUnit();
        RecyclerView recyclerView = findViewById(R.id.recyclerExercises);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        assert unit != null;
        ArrayList<Exercise> tabExercise = new ArrayList<>(unit.getExerciseArrayList());

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