package com.example.trainx;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class PlanModule extends Fragment implements View.OnClickListener {
    public Button activeButton;
    public MaterialTextView titleText;
    public MaterialTextView structureText;

    private String titleString;
    private String structureString;
    private boolean isActive;

    public PlanModule() {
    }

    public PlanModule(String title, String structure, boolean isActive) {
        this.titleString = title;
        this.structureString = structure;
        this.isActive = isActive;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_plan_module, container, false);

        MaterialCardView materialCardView = myView.findViewById(R.id.PlanCardView);
        activeButton = myView.findViewById(R.id.ActiveFlag);
        titleText = myView.findViewById(R.id.PlanTitleText);
        structureText = myView.findViewById(R.id.PlanStructureText);

        if(!isActive)
            activeButton.setVisibility(View.GONE);

        titleText.setText(titleString);
        structureText.setText(structureString);

        materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(titleString);
            }
        });
        materialCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                materialCardView.setChecked(true);

                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Delete Training Plan")
                        .setMessage("Are you sure to delete selected training plan?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                Query query = ref.child("users").child("MG").child("Plans").orderByChild("name").equalTo(titleString);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                            dataSnapshot.getRef().removeValue();
                                            materialCardView.setVisibility(View.GONE);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.e("DeletingError", "onCancelled", error.toException());
                                    }
                                });
                            }
                        })
                        .show();
                return true;
            }
        });
        return myView;
    }

    @Override
    public void onClick(View view) {

    }
}