package com.example.trainx;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class DataManager implements Serializable {
    private ArrayList<TrainingPlan> trainingPlans;

    public DataManager() {
        trainingPlans = new ArrayList<>();
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();


        DatabaseReference ref = mDatabase.child("users").child("MG").child("Plans");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot plansSnapshot : snapshot.getChildren()) {
                    String name = plansSnapshot.child("name").getValue(String.class);
                    String type = plansSnapshot.child("type").getValue(String.class);
                    if(plansSnapshot.child("isActive").getValue(boolean.class) == null)
                        Log.i("Saving", "is Active is null");
                    boolean isActive;
                    if(plansSnapshot.child("isActive").getValue(boolean.class))
                        isActive = true;
                    else
                        isActive = false;
                    //plansSnapshot.child("active").getValue(boolean.class);
                    ArrayList<TrainingUnit> trainingUnitArrayList = new ArrayList<>();
                    if(name == null)
                        Log.i("DataManagerLog", "null");
                    else
                        Log.i("DataManagerLog", name);
                    if(type !=null)
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
                        for(DataSnapshot pom2Snapshot : pomSnapshot.child("exerciseArrayList").getChildren()) {
                            String exerciseName = pom2Snapshot.child("name").getValue(String.class);
                            int reps = pom2Snapshot.child("reps").getValue(int.class);
                            int sets = pom2Snapshot.child("sets").getValue(int.class);
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
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.child("users").child("MG").child("Plans");
        ref.child(Objects.requireNonNull(ref.push().getKey())).setValue(tp);
    }

    public void deleteFromTrainingList(String title){
        for(TrainingPlan tp : trainingPlans){
            if(tp.getName().equals(title)){
                trainingPlans.remove(tp);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query query = ref.child("users").child("MG").child("Plans").orderByChild("title").equalTo(title);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                            dataSnapshot.getRef().removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;
            }
        }
    }
}
