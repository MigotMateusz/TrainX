package com.example.trainx;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainx.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class CustomDialogFragment extends DialogFragment {
    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextInputEditText nameInput;
    private TextInputEditText repsInput;
    private TextInputEditText setsInput;
    private TrainingUnitCallback myCallback;
    ArrayList<String> tab1;
    public CustomDialogFragment(TrainingUnitCallback call) { this.myCallback = call; }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container, savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
        View myView = inflater.inflate(R.layout.add_new_unit_dialog, container, false);
            /*arrayList = new ArrayList<String>();
            adapter = new ArrayAdapter<String>(getActivity(), R.layout.listview_exercise_layout, arrayList);
            listView = myView.findViewById(R.id.ListViewExercise);*/
        recyclerView = (RecyclerView) myView.findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        tab1 = new ArrayList<String>();
        String[] tab = new String[tab1.size()];
        tab = tab1.toArray(tab);
        mAdapter = new MyAdapter(tab);
        recyclerView.setAdapter(mAdapter);
        toolbar = myView.findViewById(R.id.NewUnitDialog);
        Button addButton = (Button) myView.findViewById(R.id.AddExerciseButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                CustomDialog newFragment = new CustomDialog();
                //show as dialog
                newFragment.show(fragmentManager, "dialog");*/
                openDialog(new MyCallback() {
                    @Override
                    public void onCallback(String value) {
                        Log.i("Texting", value);
                        newExercise(value);
                    }
                });
            }
        });
        TextInputEditText nameInput = (TextInputEditText) myView.findViewById(R.id.NamePlanInput);
        ExtendedFloatingActionButton saveButton = (ExtendedFloatingActionButton) myView.findViewById(R.id.saveUnitButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Zapisz tytuł i tablice z ćwiczeniami w funkcji
                String namePlan = nameInput.getText().toString();
                Log.i("nameInput", namePlan);
                for(String t : tab1){
                    Log.i("Tablica: ", t);
                }
                myCallback.onCallback(namePlan, tab1);
                getDialog().cancel();
            }
        });
        return myView;
    }

    public static CustomDialogFragment display(FragmentManager fragmentManager, TrainingUnitCallback callback) {
        CustomDialogFragment customDialogFragment = new CustomDialogFragment(callback);
        customDialogFragment.show(fragmentManager, "TEST");
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
    public void newExercise(String value) {
        tab1.add(value);
        for(String t : tab1){
            Log.i("For", t);
        }
        mAdapter.notifyItemInserted(tab1.size() - 1);
        String[] newTab = new String[tab1.size()];
        newTab = tab1.toArray(newTab);
        MyAdapter newAdapter = new MyAdapter(newTab);
        recyclerView.setAdapter(newAdapter);
    }
    private void openDialog(final MyCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.addexercise_dialog, null);
        builder.setView(v);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //callback.onCallback();
                String name = nameInput.getText().toString();
                String sets = setsInput.getText().toString();
                String reps = repsInput.getText().toString();
                String result = name + " " + sets + " x " + reps;
                Log.i("Result: ", result);
                callback.onCallback(result);
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
