package com.example.trainx.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
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

import com.example.trainx.models.Exercise;
import com.example.trainx.adapters.MyAdapter;
import com.example.trainx.R;
import com.example.trainx.interfaces.ExerciseCallback;
import com.example.trainx.interfaces.TrainingUnitCallback;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class CustomDialogFragment extends DialogFragment {
    private TextInputEditText nameInputDialog;
    private TextInputEditText repsInputDialog;

    private TextInputEditText setsInputDialog;
    private RecyclerView recyclerView;

    private ArrayList<String> tab1;
    private ArrayList<Exercise> tabExercise;

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

        prepareRecyclerView(myView);
        prepareAddButton(myView);
        prepareSaveButton(myView);

        return myView;
    }

    private void prepareRecyclerView(View myView) {
        recyclerView = (RecyclerView) myView.findViewById(R.id.my_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        tab1 = new ArrayList<>();
        tabExercise = new ArrayList<>();
        String[] tab = new String[tab1.size()];
        tab = tab1.toArray(tab);
        MyAdapter mAdapter = new MyAdapter(tab);
        recyclerView.setAdapter(mAdapter);
    }

    private void prepareAddButton(View myView) {
        Button addButton = (Button) myView.findViewById(R.id.AddExerciseButton);
        addButton.setOnClickListener(view -> openDialog(this::newExercise));
    }

    private void prepareSaveButton(View myView) {
        TextInputEditText nameInput = (TextInputEditText) myView.findViewById(R.id.NamePlanInput);
        ExtendedFloatingActionButton saveButton = (ExtendedFloatingActionButton) myView.findViewById(R.id.saveUnitButton);
        saveButton.setOnClickListener(view -> {
            String namePlan = Objects.requireNonNull(nameInput.getText()).toString();
            myCallback.onCallback(namePlan, tabExercise);
            Objects.requireNonNull(getDialog()).cancel();
        });
    }

    public static void display(FragmentManager fragmentManager, TrainingUnitCallback callback) {
        CustomDialogFragment customDialogFragment = new CustomDialogFragment(callback);
        customDialogFragment.show(fragmentManager, "CustomDialogFragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialToolbar toolbar = view.findViewById(R.id.NewUnitDialog);
        toolbar.setNavigationOnClickListener(v -> dismiss());
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if(dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogChangeEmail);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.addexercise_dialog, null);

        builder.setView(v);
        builder.setPositiveButton("Add", (dialogInterface, i) -> {
            String name = Objects.requireNonNull(nameInputDialog.getText()).toString();
            int sets = Integer.parseInt(Objects.requireNonNull(setsInputDialog.getText()).toString());
            int reps = Integer.parseInt(Objects.requireNonNull(repsInputDialog.getText()).toString());
            Exercise newExercise = new Exercise(name,sets,reps);
            callback.onCallback(newExercise);
        });
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();

        nameInputDialog = (TextInputEditText) dialog.findViewById(R.id.TextInputNameExercise);
        repsInputDialog = (TextInputEditText) dialog.findViewById(R.id.textInputReps);
        setsInputDialog = (TextInputEditText) dialog.findViewById(R.id.textInputSets);
    }
}
