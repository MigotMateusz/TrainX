package com.example.trainx;

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
                    boolean isActive = plansSnapshot.child("isActive").getValue(boolean.class);
                    ArrayList<TrainingUnit> trainingUnitArrayList = new ArrayList<>();

                    for(DataSnapshot pomSnapshot : plansSnapshot.child("unitArrayList").getChildren()){
                        String planName =  pomSnapshot.child("name").getValue(String.class);
                        ArrayList<Exercise> arrayListofExercises = new ArrayList<>();
                        for(DataSnapshot pom2Snapshot : pomSnapshot.child("exerciseArrayList").getChildren()) {
                            String exerciseName = pom2Snapshot.child("name").getValue(String.class);
                            int reps = pom2Snapshot.child("reps").getValue(int.class);
                            int sets = pom2Snapshot.child("sets").getValue(int.class);
                            Exercise newExercise = new Exercise(exerciseName,sets,reps);
                            arrayListofExercises.add(newExercise);
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
