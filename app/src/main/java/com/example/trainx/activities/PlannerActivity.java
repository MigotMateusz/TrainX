package com.example.trainx.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trainx.R;
import com.example.trainx.data.DataManager;
import com.example.trainx.models.TrainingExecution;
import com.example.trainx.models.TrainingPlan;
import com.example.trainx.models.TrainingUnit;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class PlannerActivity extends AppCompatActivity {
    private DataManager dataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        readDataManager();
        prepareToolbar();
        loadDropDowns();

    }
    public void setListener(AutoCompleteTextView autoCompleteTextView, int position){
        AutoCompleteTextView[] dropdownMenuUnits =
                {findViewById(R.id.dropdownTrainingUnitsMonday), findViewById(R.id.dropdownTrainingUnitsTuesday),
                        findViewById(R.id.dropdownTrainingUnitsWednesday), findViewById(R.id.dropdownTrainingUnitsThursday),
                        findViewById(R.id.dropdownTrainingUnitsFriday), findViewById(R.id.dropdownTrainingUnitsSaturday),
                        findViewById(R.id.dropdownTrainingUnitsSunday)};

        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            dropdownMenuUnits[position].setEnabled(true);
            ArrayList<String> trainingUnits = new ArrayList<>();
            for(TrainingPlan tp :  dataManager.getTrainingPlans()){
                if(tp.getName().equals(autoCompleteTextView.getText().toString())){
                    for(TrainingUnit tu : tp.getUnitArrayList())
                        trainingUnits.add(tu.getName());
                    break;
                }
            }
            ArrayAdapter<String> adapterUnits =
                    new ArrayAdapter<>(PlannerActivity.this, R.layout.dropdown_menu_popup_item, trainingUnits);

            dropdownMenuUnits[position].setAdapter(adapterUnits);
            dropdownMenuUnits[position].setInputType(0);
        });

    }
    private void prepareToolbar(){
        final MaterialToolbar toolbar = findViewById(R.id.toolbarPlanner);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        prepareSaveButton();
    }

    private void prepareSaveButton() {
        FloatingActionButton saveButton = findViewById(R.id.saveToolbar);
        saveButton.setColorFilter(R.color.white);
        saveButton.setOnClickListener(view -> saveWeekPlan());
    }

    private void readDataManager(){
        if(getIntent().getBundleExtra("DataManager").getSerializable("DataManager") != null)
            dataManager = (DataManager) getIntent().getBundleExtra("DataManager").getSerializable("DataManager");
    }

    private void loadDropDowns(){
        ArrayList<String> trainingPlans = new ArrayList<>();

        for(TrainingPlan tp : dataManager.getTrainingPlans()){
            trainingPlans.add(tp.getName());
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(PlannerActivity.this, R.layout.dropdown_menu_popup_item, trainingPlans);

        AutoCompleteTextView[] dropdownMenuPlans =
                {findViewById(R.id.dropdownTrainingPlansMonday), findViewById(R.id.dropdownTrainingPlansTuesday),
                        findViewById(R.id.dropdownTrainingPlansWednesday), findViewById(R.id.dropdownTrainingPlansThursday),
                        findViewById(R.id.dropdownTrainingPlansFriday), findViewById(R.id.dropdownTrainingPlansSaturday),
                        findViewById(R.id.dropdownTrainingPlansSunday)};

        for(AutoCompleteTextView textView : dropdownMenuPlans){
            textView.setAdapter(adapter);
            textView.setInputType(0);
        }

        for(int i=0; i<7; i++) {
            setListener(dropdownMenuPlans[i], i);
        }
    }
    private void saveWeekPlan() {
        AutoCompleteTextView[] dropdownMenuPlans =
                {findViewById(R.id.dropdownTrainingPlansMonday), findViewById(R.id.dropdownTrainingPlansTuesday),
                        findViewById(R.id.dropdownTrainingPlansWednesday), findViewById(R.id.dropdownTrainingPlansThursday),
                        findViewById(R.id.dropdownTrainingPlansFriday), findViewById(R.id.dropdownTrainingPlansSaturday),
                        findViewById(R.id.dropdownTrainingPlansSunday)};

        AutoCompleteTextView[] dropdownMenuUnits =
                {findViewById(R.id.dropdownTrainingUnitsMonday), findViewById(R.id.dropdownTrainingUnitsTuesday),
                        findViewById(R.id.dropdownTrainingUnitsWednesday), findViewById(R.id.dropdownTrainingUnitsThursday),
                        findViewById(R.id.dropdownTrainingUnitsFriday), findViewById(R.id.dropdownTrainingUnitsSaturday),
                        findViewById(R.id.dropdownTrainingUnitsSunday)};

        for(int i = 0; i < 7; i++) {
            if(!dropdownMenuPlans[i].getText().toString().equals("") && !dropdownMenuUnits[i].getText().toString().equals("")){
                Calendar c = Calendar.getInstance();
                c.setFirstDayOfWeek(Calendar.MONDAY);
                c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
                c.add(Calendar.DAY_OF_MONTH, i);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String date = df.format(c.getTime());
                TrainingExecution newExecution = new TrainingExecution(date, dropdownMenuUnits[i].getText().toString(),
                        dropdownMenuPlans[i].getText().toString());
                dataManager.addToTrainingExecutionList(newExecution);
            }

        }
        Intent intent = new Intent(PlannerActivity.this, MainActivity.class);
        //intent.putExtra("doWhat", 0);
        startActivity(intent);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }
}
