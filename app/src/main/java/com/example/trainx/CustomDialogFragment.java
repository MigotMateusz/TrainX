package com.example.trainx;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.trainx.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class CustomDialogFragment extends DialogFragment {
    private MaterialToolbar toolbar;
    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container, savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
        View myView = inflater.inflate(R.layout.add_new_unit_dialog, container, false);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getContext(), R.layout.add_new_unit_dialog, arrayList);
        listView = myView.findViewById(R.id.ListViewExercise);
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
                        newExercise("XD",1,15);
                    }
                });
            }
        });
        return myView;
    }


    public static CustomDialogFragment display(FragmentManager fragmentManager) {
        CustomDialogFragment customDialogFragment = new CustomDialogFragment();
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
    public void newExercise(String name, int sets, int reps) {
        String result = name + " " + sets + "x" + reps;
        arrayList.add(result);
        listView.setAdapter(adapter);
    }
    private void openDialog(final MyCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Title test");
        builder.setView(R.layout.addexercise_dialog);
        TextInputEditText repsInput = (TextInputEditText) getActivity().findViewById(R.id.textInputReps);
        TextInputEditText setsInput = (TextInputEditText) getActivity().findViewById(R.id.textInputSets);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //callback.onCallback(((TextInputEditText)getActivity().findViewById(R.id.TextInputNameExercise)).getText().toString());
            }
        });
        builder.show();
    }
}
