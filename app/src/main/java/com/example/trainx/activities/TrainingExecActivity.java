package com.example.trainx.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.trainx.R;
import com.example.trainx.data.DataManager;
import com.example.trainx.fragments.ExerciseFragment;
import com.example.trainx.models.Exercise;
import com.example.trainx.models.FinishedTraining;
import com.example.trainx.models.TrainingExecution;
import com.example.trainx.models.TrainingPlan;
import com.example.trainx.models.TrainingUnit;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TrainingExecActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_exec);
        MaterialToolbar toolbar = findViewById(R.id.trainerExecToolbar);
        setSupportActionBar(toolbar);
        DataManager dataManager = (DataManager) getIntent().getBundleExtra("DataManager").getSerializable("DataManager");
        TrainingUnit currentTraining = prepareTrainingUnit(dataManager);
        TrainingExecution currentTrainingExecution = prepareTrainingExecution(dataManager);
        int position = 0;
        Exercise e = currentTraining.getExerciseArrayList().get(position);
        FinishedTraining currentFinishedTraining = new FinishedTraining();
        currentFinishedTraining.setTrainingExecution(currentTrainingExecution);
        currentFinishedTraining.setExercises(new ArrayList<>());
        ExerciseFragment exerciseFragment = new ExerciseFragment();
        Bundle bundle = getIntent().getExtras();
        bundle.putSerializable("Exercise", e);
        bundle.putSerializable("DataManager", dataManager);
        bundle.putSerializable("FinishedTraining", currentFinishedTraining);
        bundle.putSerializable("ExerciseArray", currentTraining.getExerciseArrayList());
        bundle.putInt("Position", position);
        exerciseFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayoutExec, exerciseFragment).commit();


    }

    public TrainingUnit prepareTrainingUnit(DataManager dataManager){
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = df.format(currentDate);
        for(TrainingExecution te : dataManager.getTrainingExecutions()) {
            if(te.getDate().equals(date)){
                for(TrainingPlan tp : dataManager.getTrainingPlans()){
                    if(tp.getName().equals(te.getPlan())){
                        for(TrainingUnit tu : tp.getUnitArrayList()){
                            if(tu.getName().equals(te.getUnit())){
                                Log.i("TrainingExecActivityLog", tu.getName());
                                return tu;
                            }
                        }
                    }
                }
            }
        }
    return null;
    }
    public TrainingExecution prepareTrainingExecution(DataManager dataManager){
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = df.format(currentDate);
        for(TrainingExecution te : dataManager.getTrainingExecutions()) {
            if(te.getDate().equals(date)){
                for(TrainingPlan tp : dataManager.getTrainingPlans()){
                    if(tp.getName().equals(te.getPlan())){
                        for(TrainingUnit tu : tp.getUnitArrayList()){
                            if(tu.getName().equals(te.getUnit())){
                                Log.i("TrainingExecActivityLog", tu.getName());
                                return te;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}