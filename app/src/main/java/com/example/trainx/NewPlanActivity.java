package com.example.trainx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import static java.security.AccessController.getContext;

public class NewPlanActivity extends AppCompatActivity {
    public Menu navMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plan);

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
                        openDialog();
                    }
                });
                MaterialCardView cardView = (MaterialCardView) findViewById(R.id.GoptionCard);
                cardView.setVisibility(View.GONE);
                goNextButton.setVisibility(View.GONE);
                extendedFloatingActionButton.setVisibility(View.VISIBLE);


                String nameOftheTraining = textInputPlanName.getText().toString();
                String typeOftheTraining = typeOfTraining.getText().toString();
                boolean activeTraining = activeSwitch.isChecked();

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
        navMenu.findItem(R.id.savePlan).setVisible(true);
        MenuItem menuItemSave = navMenu.findItem(R.id.savePlan);
        menuItemSave.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                new MaterialAlertDialogBuilder(NewPlanActivity.this)
                        .setTitle("Saving new plan")
                        .setMessage("Are you sure to save this plan?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Save", null)
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
    }
    public void openDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //CustomDialogFragment newFragment = new CustomDialogFragment();
        CustomDialogFragment.display(fragmentManager);
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

}