package com.example.trainx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

import static java.security.AccessController.getContext;

public class NewPlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plan);

        String[] trainingTypes = new String[] {"Split", "Full Body Workout", "Push-Pull", "Push-Pull-Legs"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(NewPlanActivity.this, R.layout.dropdown_menu_popup_item, trainingTypes);

        AutoCompleteTextView dropdownMenuTypes = findViewById(R.id.Dropdown_typeTraining);
        dropdownMenuTypes.setAdapter(adapter);
        dropdownMenuTypes.setInputType(0);


        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.topAppBarMain);
        setSupportActionBar(toolbar);



        SwitchMaterial activeSwitch = (SwitchMaterial) findViewById(R.id.ActivePlanSwitch);
        final boolean isactive;

        activeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {

                } else {

                }
            }
        });

        final Button goNextButton = (Button) findViewById(R.id.GoStructureButton);
        goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) findViewById(R.id.NewTrainingUnitButton);
                MaterialCardView cardView = (MaterialCardView) findViewById(R.id.GoptionCard);
                cardView.setVisibility(View.GONE);
                goNextButton.setVisibility(View.GONE);
                extendedFloatingActionButton.setVisibility(View.VISIBLE);
                createTrainingUnitScreen();
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

    public void createTrainingUnitScreen() {
        createTrainingUnitTitle();
        openDialog();

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
        CustomDialogFragment newFragment = new CustomDialogFragment();
        //show as dialog
        //newFragment.show(fragmentManager, "dialog");

        //show as fullscreen
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.LlPlanCreator, newFragment).addToBackStack(null).commit();
        //addExerciseHandler();
    }
}