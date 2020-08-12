package com.example.trainx;

import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class DataManager implements Serializable {
    private ArrayList<TrainingPlan> trainingPlans;

    public DataManager() {
        trainingPlans = new ArrayList<TrainingPlan>();
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        DatabaseReference ref = mDatabase.child("users").child("MG").child("Plans");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot plansSnapshot : snapshot.getChildren()) {
                    //TrainingPlan trainingPlan = plansSnapshot.getValue(TrainingPlan.class);
                    String name = plansSnapshot.child("name").getValue(String.class);
                    String type = plansSnapshot.child("type").getValue(String.class);
                    boolean isActive = plansSnapshot.child("isActive").getValue(boolean.class);
                    ArrayList<TrainingUnit> trainingUnitArrayList = new ArrayList<>();
                    if(name == null)
                        Log.i("DataManagerLog", "null");
                    else
                        Log.i("DataManagerLog", name);
                    Log.i("DataManagerLog", type);
                    if(isActive == true)
                        Log.i("DataManagerLog","isActive is true");
                    else
                        Log.i("DataManagerLog","isActive is false");

                    /*This loop is reading all saved training unit in specific training plan*/
                    for(DataSnapshot pomSnapshot : plansSnapshot.child("unitArrayList").getChildren()){
                        String planName =  pomSnapshot.child("name").getValue(String.class);
                        Log.i("DataManagerLog", planName);
                        ArrayList<Exercise> arrayListofExercises = new ArrayList<>();
                        for(DataSnapshot pom2Snapshot : pomSnapshot.child("exerciseArray").getChildren()) {
                            String exerciseName = pom2Snapshot.child("name").getValue(String.class);
                            int reps = pom2Snapshot.child("Reps").getValue(int.class);
                            int sets = pom2Snapshot.child("Sets").getValue(int.class);
                            Exercise newExercise = new Exercise(exerciseName,sets,reps);
                            arrayListofExercises.add(newExercise);
                            Log.i("DataManagerLog",exerciseName);
                            Log.i("DataManagerLog",String.valueOf(sets));
                            Log.i("DataManagerLog",String.valueOf(reps));
                        }
                        TrainingUnit newTrainingUnit = new TrainingUnit(planName,arrayListofExercises);
                        trainingUnitArrayList.add(newTrainingUnit);
                    }
                    TrainingPlan newTrainingPlan = new TrainingPlan(name,type,isActive,trainingUnitArrayList);
                    trainingPlans.add(newTrainingPlan);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

    }

    public static DataManager INSTANCE;

    public static DataManager getInstance() {
        if(INSTANCE == null)
            INSTANCE = new DataManager();
        return INSTANCE;
    }

    public ArrayList<TrainingPlan> getTrainingPlans() {
        return trainingPlans;
    }

    public void setTrainingPlans(ArrayList<TrainingPlan> trainingPlans) {
        this.trainingPlans = trainingPlans;
    }

    public void addToTrainingList(TrainingPlan tp) {
        trainingPlans.add(tp);
    }

    public void deleteFromTrainingList(TrainingPlan tp){
        trainingPlans.remove(tp);
    }
}
