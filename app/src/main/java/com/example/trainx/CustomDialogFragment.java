package com.example.trainx;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class CustomDialogFragment extends DialogFragment {
    private MaterialToolbar toolbar;
    private TextInputEditText nameInput;
    private TextInputEditText repsInput;
    private TextInputEditText setsInput;

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    ArrayList<String> tab1;
    ArrayList<Exercise> tabExercise;

    private TrainingUnitCallback myCallback;

    public CustomDialogFragment(TrainingUnitCallback call) {
        this.myCallback = call;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container, savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);

        View myView = inflater.inflate(R.layout.add_new_unit_dialog, container, false);

        recyclerView = (RecyclerView) myView.findViewById(R.id.my_recycler_view);
        TextInputEditText nameInput = (TextInputEditText) myView.findViewById(R.id.NamePlanInput);
        ExtendedFloatingActionButton saveButton = (ExtendedFloatingActionButton) myView.findViewById(R.id.saveUnitButton);
        toolbar = myView.findViewById(R.id.NewUnitDialog);
        Button addButton = (Button) myView.findViewById(R.id.AddExerciseButton);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        tab1 = new ArrayList<>();
        tabExercise = new ArrayList<>();
        String[] tab = new String[tab1.size()];
        tab = tab1.toArray(tab);
        mAdapter = new MyAdapter(tab);
        recyclerView.setAdapter(mAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(new ExerciseCallback() {
                    @Override
                    public void onCallback(Exercise value) {
                        newExercise(value);
                    }
                });
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namePlan = nameInput.getText().toString();
                myCallback.onCallback(namePlan, tabExercise);
                getDialog().cancel();
            }
        });

        return myView;
    }

    public static CustomDialogFragment display(FragmentManager fragmentManager, TrainingUnitCallback callback) {
        CustomDialogFragment customDialogFragment = new CustomDialogFragment(callback);
        customDialogFragment.show(fragmentManager, "CustomDialogFragment");
        return customDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> dismiss());
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if(dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
    public void newExercise(Exercise value) {
        tabExercise.add(value);
        String string = value.getName() + " " + value.getSets() + "x" + value.getReps();
        tab1.add(string);
        String[] newTab = new String[tab1.size()];
        newTab = tab1.toArray(newTab);
        MyAdapter newAdapter = new MyAdapter(newTab);
        recyclerView.setAdapter(newAdapter);
    }
    private void openDialog(final ExerciseCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.addexercise_dialog, null);

        builder.setView(v);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = nameInput.getText().toString();
                int sets = Integer.parseInt(setsInput.getText().toString());
                int reps = Integer.parseInt(repsInput.getText().toString());
                Exercise newExercise = new Exercise(name,sets,reps);
                callback.onCallback(newExercise);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        nameInput = (TextInputEditText) dialog.findViewById(R.id.TextInputNameExercise);
        repsInput = (TextInputEditText) dialog.findViewById(R.id.textInputReps);
        setsInput = (TextInputEditText) dialog.findViewById(R.id.textInputSets);
    }
}
