package com.example.trainx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class LoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        DataManager dataManager = new DataManager();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loadDataProgressBar);
        progressBar.setIndeterminate(true);
        for(int i = 0; i >= 0; i++) {
            Log.i("LoaderLog", dataManager.getTrainingPlans().size() + " plans");
            Log.i("LoaderLog", dataManager.getFinishedTrainings().size() + " finished");
            Log.i("LoaderLog", dataManager.getTrainingExecutions().size() + " exec");
            Log.i("LoaderLog", dataManager.getWeightsUser().size() + " weight");
            if(dataManager.getTrainingPlans().size() != 0){
                Bundle dataBundle = new Bundle();
                dataBundle.putSerializable("dataManager", dataManager);
                Intent intent = new Intent(LoaderActivity.this, MainActivity.class);
                intent.putExtra("dataBundle", dataBundle);
                startActivity(intent);
                break;
            }
        }
    }
}