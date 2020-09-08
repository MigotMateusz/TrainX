package com.example.trainx;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Measurements implements Serializable {
    private ArrayList<Measure> neckMeasurements;
    private ArrayList<Measure> shouldersMeasurements;
    private ArrayList<Measure> bicepsMeasurements;
    private ArrayList<Measure> forearmMeasurements;
    private ArrayList<Measure> chestMeasurements;
    private ArrayList<Measure> waistMeasurements;
    private ArrayList<Measure> hipsMeasurements;
    private ArrayList<Measure> thighsMeasurements;
    private ArrayList<Measure> calvesMeasurements;

    public Measurements() {
        neckMeasurements = new ArrayList<>();
        bicepsMeasurements = new ArrayList<>();
        shouldersMeasurements = new ArrayList<>();
        forearmMeasurements = new ArrayList<>();
        chestMeasurements = new ArrayList<>();
        waistMeasurements = new ArrayList<>();
        hipsMeasurements = new ArrayList<>();
        thighsMeasurements = new ArrayList<>();
        calvesMeasurements = new ArrayList<>();

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        DatabaseReference ref = mDatabase.child("users").child(user.getUid()).child("Measurements");
        loadArray(neckMeasurements, "Neck", ref);
        loadArray(bicepsMeasurements, "Biceps", ref);
        loadArray(shouldersMeasurements, "Shoulders", ref);
        loadArray(forearmMeasurements, "Forearm", ref);
        loadArray(chestMeasurements, "Chest", ref);
        loadArray(waistMeasurements, "Waist", ref);
        loadArray(hipsMeasurements, "Hips", ref);
        loadArray(thighsMeasurements, "Thighs", ref);
        loadArray(calvesMeasurements, "Calves", ref);

    }
    private void loadArray(ArrayList<Measure> measures, String name, DatabaseReference ref) {
        DatabaseReference reference = ref.child(name);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot measureSnapshot : snapshot.getChildren()){
                    Measure newMeasure = measureSnapshot.getValue(Measure.class);
                    Log.i("MeasureLog", newMeasure.getDate() + " " + newMeasure.getValue());
                    measures.add(newMeasure);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    public void addToMeasurementsList(ArrayList<Measure> measures, String name, Measure measure) {
        measures.add(measure);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Measurements").child(name);
        ref.child(Objects.requireNonNull(ref.push().getKey())).setValue(measure);
    }

    public ArrayList<Measure> getNeckMeasurements() {
        return neckMeasurements;
    }

    public void setNeckMeasurements(ArrayList<Measure> neckMeasurements) {
        this.neckMeasurements = neckMeasurements;
    }

    public ArrayList<Measure> getShouldersMeasurements() {
        return shouldersMeasurements;
    }

    public void setShouldersMeasurements(ArrayList<Measure> shouldersMeasurements) {
        this.shouldersMeasurements = shouldersMeasurements;
    }

    public ArrayList<Measure> getBicepsMeasurements() {
        return bicepsMeasurements;
    }

    public void setBicepsMeasurements(ArrayList<Measure> bicepsMeasurements) {
        this.bicepsMeasurements = bicepsMeasurements;
    }

    public ArrayList<Measure> getForearmMeasurements() {
        return forearmMeasurements;
    }

    public void setForearmMeasurements(ArrayList<Measure> forearmMeasurements) {
        this.forearmMeasurements = forearmMeasurements;
    }

    public ArrayList<Measure> getChestMeasurements() {
        return chestMeasurements;
    }

    public void setChestMeasurements(ArrayList<Measure> chestMeasurements) {
        this.chestMeasurements = chestMeasurements;
    }

    public ArrayList<Measure> getWaistMeasurements() {
        return waistMeasurements;
    }

    public void setWaistMeasurements(ArrayList<Measure> waistMeasurements) {
        this.waistMeasurements = waistMeasurements;
    }

    public ArrayList<Measure> getHipsMeasurements() {
        return hipsMeasurements;
    }

    public void setHipsMeasurements(ArrayList<Measure> hipsMeasurements) {
        this.hipsMeasurements = hipsMeasurements;
    }

    public ArrayList<Measure> getThighsMeasurements() {
        return thighsMeasurements;
    }

    public void setThighsMeasurements(ArrayList<Measure> thighsMeasurements) {
        this.thighsMeasurements = thighsMeasurements;
    }

    public ArrayList<Measure> getCalvesMeasurements() {
        return calvesMeasurements;
    }

    public void setCalvesMeasurements(ArrayList<Measure> calvesMeasurements) {
        this.calvesMeasurements = calvesMeasurements;
    }

}
