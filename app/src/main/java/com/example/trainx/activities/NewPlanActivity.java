package com.example.trainx.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainx.R;
import com.example.trainx.adapters.MyAdapter;
import com.example.trainx.data.DataManager;
import com.example.trainx.fragments.CustomDialogFragment;
import com.example.trainx.fragments.PlansFragment;
import com.example.trainx.interfaces.TrainingUnitCallback;
import com.example.trainx.models.Exercise;
import com.example.trainx.models.TrainingPlan;
import com.example.trainx.models.TrainingUnit;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Objects;

public class NewPlanActivity extends AppCompatActivity {
    DataFromActivityToFragment data;
    DataManager dataManager;
    public Menu navMenu;
    private ArrayList<TrainingUnit> tabUnits;
    private ArrayList<String> tab1;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private String namePlan;
    private boolean activated;
    private AutoCompleteTextView dropdownMenuTypes;
    public NewPlanActivity() {}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plan);
        tabUnits = new ArrayList<>();
        data = new PlansFragment();
        dataManager = (DataManager) Objects.requireNonNull(getIntent().getBundleExtra("DataManager")).getSerializable("DataManager1");

        setToolbar();
        prepareDropDownMenu();
        
        final SwitchMaterial activeSwitch = findViewById(R.id.ActivePlanSwitch);

        final TextInputEditText textInputPlanName = findViewById(R.id.PlanNameInput);

        final Button goNextButton = findViewById(R.id.GoStructureButton);
        goNextButton.setOnClickListener(view -> {
            ExtendedFloatingActionButton extendedFloatingActionButton = findViewById(R.id.NewTrainingUnitButton);
            extendedFloatingActionButton.setOnClickListener(view1 -> openDialog(this::addTrainingUnit));
            MaterialCardView cardView = findViewById(R.id.GoptionCard);
            cardView.setVisibility(View.GONE);
            goNextButton.setVisibility(View.GONE);
            extendedFloatingActionButton.setVisibility(View.VISIBLE);

            String nameOfTheTraining = textInputPlanName.getText().toString();
            String typeOfTheTraining = dropdownMenuTypes.getText().toString();
            boolean activeTraining = activeSwitch.isChecked();
            namePlan = nameOfTheTraining;
            activated = activeTraining;

            createTrainingUnitScreen(nameOfTheTraining, typeOfTheTraining);
        });

    }

    private void setToolbar() {
        final MaterialToolbar toolbar = findViewById(R.id.topAppBarMain);
        setSupportActionBar(toolbar);
    }

    private void prepareDropDownMenu() {
        String[] trainingTypes = new String[] {"Split", "Full Body Workout", "Push-Pull", "Push-Pull-Legs"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(NewPlanActivity.this, R.layout.dropdown_menu_popup_item, trainingTypes);

        dropdownMenuTypes = findViewById(R.id.Dropdown_typeTraining);
        dropdownMenuTypes.setAdapter(adapter);
        dropdownMenuTypes.setInputType(0);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createTrainingUnitScreen(String name, String type) {
        LinearLayout ll = findViewById(R.id.LlPlanCreator);
        MaterialTextView nameTitle = new MaterialTextView(this);
        nameTitle.setText(name);
        nameTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        nameTitle.setTextColor(getResources().getColor(R.color.colorAccent));
        nameTitle.setPadding(0,0,0, 20);
        nameTitle.setTextAppearance(R.style.TextAppearance_MaterialComponents_Headline5);
        ll.addView(nameTitle);
        createTrainingUnitTitle();
        navMenu.findItem(R.id.saveBigPlan).setVisible(true);
        MenuItem menuItemSave = navMenu.findItem(R.id.saveBigPlan);
        menuItemSave.setOnMenuItemClickListener(menuItem -> {
            new MaterialAlertDialogBuilder(NewPlanActivity.this)
                    .setTitle("Saving new plan")
                    .setMessage("Are you sure to save this plan?")
                    .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel())
                    .setPositiveButton("Save", (dialogInterface, i) -> {
                        final Handler handler = new Handler();
                        final Runnable r = () -> {
                            TrainingPlan trainingPlan = new TrainingPlan(namePlan, type, activated, tabUnits);
                            data.sentData(trainingPlan);
                        };
                        handler.post(r);
                        TrainingPlan newTraining = new TrainingPlan(name, type, activated, tabUnits);
                        goBackToMainActTab3(newTraining);
                    })
                    .show();
            return false;
        });
    }

    public void createTrainingUnitTitle() {
        LinearLayout ll = findViewById(R.id.LlPlanCreator);
        ll.setPadding(40,20,0,0);

        MaterialTextView newTitle = new MaterialTextView(this);
        newTitle.setText("List of training units");
        newTitle.setTextAppearance(R.style.TextAppearance_MaterialComponents_Headline5);
        ll.addView(newTitle);
        recyclerView = new RecyclerView(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        tab1 = new ArrayList<>();
        String[] tab = new String[tab1.size()];
        tab = tab1.toArray(tab);
        mAdapter = new MyAdapter(tab);
        recyclerView.setAdapter(mAdapter);
        ll.addView(recyclerView);
    }

    public void openDialog(TrainingUnitCallback callback) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CustomDialogFragment.display(fragmentManager, callback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        navMenu = menu;
        return true;
    }
    private void addTrainingUnit(String name, ArrayList<Exercise> arrayList){
        String result = name + ": " + "number of exercises: " + arrayList.size();
        tab1.add(result);
        tabUnits.add(new TrainingUnit(name,arrayList));
        mAdapter.notifyItemInserted(tab1.size() - 1);
        String[] newTab = new String[tab1.size()];
        newTab = tab1.toArray(newTab);
        MyAdapter newAdapter = new MyAdapter(newTab);
        recyclerView.setAdapter(newAdapter);
    }

    public String getNamePlan() {
        return namePlan;
    }

    public interface DataFromActivityToFragment {
        Fragment sentData(TrainingPlan plan);
    }

    public void goBackToMainActTab3(TrainingPlan newTraining) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("doWhat", 2);

        Bundle arguments = new Bundle();
        dataManager.addToTrainingList(newTraining);
        arguments.putSerializable("DataManager", dataManager);

        intent.putExtra("BundleNewPlan", arguments);
        startActivity(intent);
    }
}