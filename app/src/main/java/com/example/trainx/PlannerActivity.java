package com.example.trainx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PlannerActivity extends AppCompatActivity {
    DataManager dataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        readDataManager();
        prepareToolbar();
        loadDropdowns();

    }
    public void setListener(AutoCompleteTextView autoCompleteTextView, int position){
        AutoCompleteTextView[] dropdownMenuUnits =
                {findViewById(R.id.dropdownTrainingUnitsMonday), findViewById(R.id.dropdownTrainingUnitsTuesday),
                        findViewById(R.id.dropdownTrainingUnitsWednesday), findViewById(R.id.dropdownTrainingUnitsThursday),
                        findViewById(R.id.dropdownTrainingUnitsFriday), findViewById(R.id.dropdownTrainingUnitsSaturday),
                        findViewById(R.id.dropdownTrainingUnitsSunday)};

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
                        new ArrayAdapter<String>(PlannerActivity.this, R.layout.dropdown_menu_popup_item, trainingUnits);

                dropdownMenuUnits[position].setAdapter(adapterUnits);
                dropdownMenuUnits[position].setInputType(0);
            }
        });

    }
    private void prepareToolbar(){
        final MaterialToolbar toolbar = (MaterialToolbar)findViewById(R.id.toolbarPlanner);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prepareSaveButton();
    }

    private void prepareSaveButton() {
        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.saveToolbar);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveWeekPlan();
            }
        });
    }

    private void readDataManager(){
        if(getIntent().getBundleExtra("DataManager").getSerializable("DataManager") != null)
            dataManager = (DataManager) getIntent().getBundleExtra("DataManager").getSerializable("DataManager");
    }

    private void loadDropdowns(){
        ArrayList<String> trainingPlans = new ArrayList<>();

        for(TrainingPlan tp : dataManager.getTrainingPlans()){
            trainingPlans.add(tp.getName());
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(PlannerActivity.this, R.layout.dropdown_menu_popup_item, trainingPlans);

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
                Log.i("PlannerLog", String.valueOf(i) + ": " + dropdownMenuPlans[i].getText().toString() +
                        " " + dropdownMenuUnits[i].getText().toString());
                Calendar c = Calendar.getInstance();
                c.setFirstDayOfWeek(Calendar.MONDAY);
                c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
                c.add(Calendar.DAY_OF_MONTH, i);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String date = df.format(c.getTime());
                TrainingExecution newExecution = new TrainingExecution(date, dropdownMenuUnits[i].getText().toString());
                dataManager.addToTrainingExecutionList(newExecution);
            }

        }

    }
}
