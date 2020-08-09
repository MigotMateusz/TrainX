package com.example.trainx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class NewPlanActivity extends AppCompatActivity {
    DataFromActivityToFragment data;
    public Menu navMenu;
    private ArrayList<String> tab1;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private String namePlan;
    private String type;
    private boolean activated;
    public NewPlanActivity() {}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plan);

        PlansFragment plansFragment = new PlansFragment();
        android.app.FragmentManager fm = getFragmentManager();
        data = (DataFromActivityToFragment) plansFragment;


        final MaterialToolbar toolbar = (MaterialToolbar)findViewById(R.id.topAppBarMain);
        setSupportActionBar(toolbar);

                String[] trainingTypes = new String[] {"Split", "Full Body Workout", "Push-Pull", "Push-Pull-Legs"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(NewPlanActivity.this, R.layout.dropdown_menu_popup_item, trainingTypes);

        AutoCompleteTextView dropdownMenuTypes = findViewById(R.id.Dropdown_typeTraining);
        dropdownMenuTypes.setAdapter(adapter);
        dropdownMenuTypes.setInputType(0);


        final SwitchMaterial activeSwitch = (SwitchMaterial) findViewById(R.id.ActivePlanSwitch);
        final boolean isactive;

        final TextInputEditText textInputPlanName = (TextInputEditText)findViewById(R.id.PlanNameInput);
        final AutoCompleteTextView typeOfTraining = (AutoCompleteTextView) findViewById(R.id.Dropdown_typeTraining);

        final Button goNextButton = (Button) findViewById(R.id.GoStructureButton);
        goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) findViewById(R.id.NewTrainingUnitButton);
                extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openDialog(new TrainingUnitCallback() {
                            @Override
                            public void onCallback(String namePlan, ArrayList<String> exerciseArray) {
                                int s = exerciseArray.size();
                                addTrainingUnit(namePlan, s);
                            }
                        });
                    }
                });
                MaterialCardView cardView = (MaterialCardView) findViewById(R.id.GoptionCard);
                cardView.setVisibility(View.GONE);
                goNextButton.setVisibility(View.GONE);
                extendedFloatingActionButton.setVisibility(View.VISIBLE);


                String nameOftheTraining = textInputPlanName.getText().toString();
                String typeOftheTraining = typeOfTraining.getText().toString();
                boolean activeTraining = activeSwitch.isChecked();
                namePlan = nameOftheTraining;
                type = typeOftheTraining;
                activated = activeTraining;

                createTrainingUnitScreen(nameOftheTraining, typeOftheTraining, activeTraining);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createTrainingUnitScreen(String name, String type, boolean isActive) {
        //temp layout
        LinearLayout ll = (LinearLayout)findViewById(R.id.LlPlanCreator);
        MaterialTextView nameTitle = new MaterialTextView(this);
        nameTitle.setText(name);
        nameTitle.setTextAppearance(R.style.TextAppearance_MaterialComponents_Headline5);
        nameTitle.setGravity(View.TEXT_ALIGNMENT_CENTER);
        nameTitle.setPadding(0,0,0,10);
        MaterialTextView typeTitle = new MaterialTextView(this);
        typeTitle.setText(type);
        typeTitle.setGravity(View.TEXT_ALIGNMENT_CENTER);
        ll.addView(nameTitle);
        ll.addView(typeTitle);
        //end of temp layout
        createTrainingUnitTitle();
        //openDialog();
        navMenu.findItem(R.id.saveBigPlan).setVisible(true);
        MenuItem menuItemSave = navMenu.findItem(R.id.saveBigPlan);
        menuItemSave.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                new MaterialAlertDialogBuilder(NewPlanActivity.this)
                        .setTitle("Saving new plan")
                        .setMessage("Are you sure to save this plan?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //callback.onCallback(namePlan, type, activated, tab1);
                                final Handler handler = new Handler();
                                final Runnable r = new Runnable() {
                                    @Override
                                    public void run() {
                                        data.sentData(namePlan, type, activated, tab1);
                                    }
                                };
                                handler.post(r);
                                goBackToMainActTab3(namePlan, type, activated, tab1);
                            }
                        })
                        .show();
                return false;
            }
        });
    }

    public void createTrainingUnitTitle() {
        LinearLayout ll = (LinearLayout)findViewById(R.id.LlPlanCreator);
        ll.setPadding(40,20,0,0);

        MaterialTextView newTitle = new MaterialTextView(this);
        newTitle.setText("List of training units");
        newTitle.setTextAppearance(R.style.TextAppearance_MaterialComponents_Headline5);
        ll.addView(newTitle);
        recyclerView = new RecyclerView(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        tab1 = new ArrayList<String>();
        String[] tab = new String[tab1.size()];
        tab = tab1.toArray(tab);
        mAdapter = new MyAdapter(tab);
        recyclerView.setAdapter(mAdapter);
        ll.addView(recyclerView);
    }
    public void openDialog(TrainingUnitCallback callback) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //CustomDialogFragment newFragment = new CustomDialogFragment();
        CustomDialogFragment.display(fragmentManager, callback);
        //show as dialog
        //newFragment.show(fragmentManager, "dialog");

        //show as fullscreen
        //FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //transaction.add(R.id.LlPlanCreator, newFragment).addToBackStack(null).commit();
        //addExerciseHandler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        navMenu = menu;
        return true;
    }
    private void addTrainingUnit(String name_and_numberExercises, int size){
        String result = name_and_numberExercises + " " + "size: " + size;
        tab1.add(result);
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
        Fragment sentData(String name, String type, boolean active, ArrayList<String> arrayOfUnits);
    }
    public void goBackToMainActTab3(String name, String type, boolean active, ArrayList<String> arrayOfUnits) {
        MainActivity mainActivity = new MainActivity();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("doWhat", 2);
        Bundle arguments = new Bundle();
        arguments.putString("name", name);
        arguments.putString("type", type);
        arguments.putBoolean("active", active);
        arguments.putStringArrayList("arrayOfUnits",arrayOfUnits);
        intent.putExtra("BundleNewPlan", arguments);
        startActivity(intent);
        /*TabLayout tabLayout = (TabLayout) mainActivity.findViewById(R.id.TopTabLayout);
        TabLayout.Tab tab = tabLayout.getTabAt(2);
        tab.select();*/
    }
}