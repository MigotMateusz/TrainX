package com.example.trainx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.appbar.MaterialToolbar;

public class TrainingExecActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_exec);
        MaterialToolbar toolbar = findViewById(R.id.trainerExecToolbar);
        setSupportActionBar(toolbar);
        DataManager dataManager = (DataManager) getIntent().getBundleExtra("DataManager").getSerializable("DataManager");
        TrainingUnit currentTraining = prepareTrainingUnit(dataManager);
        for(Exercise e : currentTraining.getExerciseArrayList()){
            Log.i("TrainingExecActivityLog", e.getName());
            ExerciseFragment exerciseFragment = new ExerciseFragment();
            Bundle bundle = getIntent().getExtras();
            bundle.putSerializable("Exercise", e);
            exerciseFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameLayoutExec, exerciseFragment).commit();
            break;
        }

    }

    public TrainingUnit prepareTrainingUnit(DataManager dataManager){
        for(TrainingExecution te : dataManager.getTrainingExecutions()) {
            if(te.getDate().equals("2020-08-25")){
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
}