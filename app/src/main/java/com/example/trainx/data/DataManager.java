package com.example.trainx.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.trainx.models.Exercise;
import com.example.trainx.models.FinishedTraining;
import com.example.trainx.models.Measurements;
import com.example.trainx.models.ShuffleExercise;
import com.example.trainx.models.ShuffleTraining;
import com.example.trainx.models.TrainingExecution;
import com.example.trainx.models.TrainingPlan;
import com.example.trainx.models.TrainingUnit;
import com.example.trainx.models.Weight;
import com.example.trainx.activities.MainActivity;
import com.example.trainx.interfaces.OnOverviewDataReceive;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DataManager implements Serializable {
    private ArrayList<TrainingPlan> trainingPlans;
    private ArrayList<TrainingExecution> trainingExecutions;
    private ArrayList<FinishedTraining> finishedTrainings;
    private ArrayList<Weight> weightsUser;
    private Measurements measurements;

    public ArrayList<ShuffleTraining> getShuffleTrainings() {
        return shuffleTrainings;
    }

    public void setShuffleTrainings(ArrayList<ShuffleTraining> shuffleTrainings) {
        this.shuffleTrainings = shuffleTrainings;
    }

    private ArrayList<ShuffleTraining> shuffleTrainings;

    public DataManager(MainActivity activity) {
        measurements = new Measurements();
        trainingPlans = new ArrayList<>();
        trainingExecutions = new ArrayList<>();
        finishedTrainings = new ArrayList<>();
        weightsUser = new ArrayList<>();
        shuffleTrainings = new ArrayList<>();
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference ref = mDatabase.child("users").child(currentUser.getUid()).child("Plans");
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

        ref = mDatabase.child("users").child(currentUser.getUid()).child("Execution");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot plansSnapshot : snapshot.getChildren()) {
                    TrainingExecution newTrainingExecution = plansSnapshot.getValue(TrainingExecution.class);
                    trainingExecutions.add(newTrainingExecution);
                }
                Collections.sort(trainingExecutions, new CustomExecutionComparator());
                activity.display();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });


        ref = mDatabase.child("users").child(currentUser.getUid()).child("Finished Trainings");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot plansSnapshot : snapshot.getChildren()) {
                    FinishedTraining newTrainingExecution = plansSnapshot.getValue(FinishedTraining.class);
                    finishedTrainings.add(newTrainingExecution);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });


        ref = mDatabase.child("users").child(currentUser.getUid()).child("Weight");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot plansSnapshot : snapshot.getChildren()) {
                    Weight newWeight = plansSnapshot.getValue(Weight.class);
                    weightsUser.add(newWeight);
                }
                Collections.sort(weightsUser, new CustomWeightComparator());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

        ref = mDatabase.child("users").child(currentUser.getUid()).child("Shuffle");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot plansSnapshot : snapshot.getChildren()) {
                    ShuffleTraining newTraining = new ShuffleTraining();
                    for(DataSnapshot pomSnapshot : plansSnapshot.getChildren()){
                        for(DataSnapshot exerciseSnapshot : pomSnapshot.getChildren()){
                            ShuffleExercise newExercise = exerciseSnapshot.getValue(ShuffleExercise.class);
                            newTraining.add(newExercise);
                        }

                    }
                    shuffleTrainings.add(newTraining);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
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
        DatabaseReference ref = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Plans");
        ref.child(Objects.requireNonNull(ref.push().getKey())).setValue(tp);
    }
    public void addToTrainingExecutionList(TrainingExecution te) {
        trainingExecutions.add(te);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Execution");
        ref.child(Objects.requireNonNull(ref.push().getKey())).setValue(te);
        Collections.sort(trainingExecutions, new CustomExecutionComparator());
    }

    public void addSingleFinishedTraining(FinishedTraining ft) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Finished Trainings");
        ref.child(Objects.requireNonNull(ref.push().getKey())).setValue(ft);
    }

    public void addNewWeight(Weight newWeight){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Weight");
        ref.child(Objects.requireNonNull(ref.push().getKey())).setValue(newWeight);
        weightsUser.add(newWeight);
        Collections.sort(weightsUser, new CustomWeightComparator());
    }

    public void deleteFromTrainingList(String title){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Plans").orderByChild("name").equalTo(title);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    dataSnapshot.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DeletingError", "onCancelled", error.toException());
            }
        });

        for(TrainingPlan tp : trainingPlans){
            if(tp.getName().equals(title)){
                trainingPlans.remove(tp);
                break;
            }
        }
    }

    public ArrayList<TrainingExecution> getTrainingExecutions() {
        return trainingExecutions;
    }

    public void setTrainingExecutions(ArrayList<TrainingExecution> trainingExecutions) {
        this.trainingExecutions = trainingExecutions;
    }

    public boolean isTrainingDone() {
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = df.format(currentDate);

        for(FinishedTraining te : this.getFinishedTrainings()){
            if(te.getTrainingExecution().getDate().equals(date))
                return true;
        }
        return false;
    }

    public void setFinishedTrainings(ArrayList<FinishedTraining> finishedTrainings) {
        this.finishedTrainings = finishedTrainings;
    }
    public ArrayList<FinishedTraining> getFinishedTrainings() {
        return finishedTrainings;
    }

    public boolean isTrainingToday() {
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = df.format(currentDate);
        for(TrainingExecution te : this.getTrainingExecutions()) {
            if(te.getDate().equals(date))
                return true;
            }
        return false;
    }

    public ArrayList<Weight> getWeightsUser() {
        return weightsUser;
    }

    public void setWeightsUser(ArrayList<Weight> weightsUser) {
        this.weightsUser = weightsUser;
    }

    public Measurements getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Measurements measurements) {
        this.measurements = measurements;
    }


    public static void loadOverviewData(OnOverviewDataReceive callback) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("OverviewInfo");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String date = snapshot.child("nextTraining").getValue(String.class);
                int dayStrike = snapshot.child("daysStrike").getValue(Integer.class);
                double currentWeight = snapshot.child("currentWeight").getValue(Double.class);
                Log.i("OverviewInfo", date);
                Log.i("OverviewInfo", String.valueOf(dayStrike));
                Log.i("OverviewInfo", String.valueOf(currentWeight));
                callback.onDataReceived(date, currentWeight, dayStrike);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static class CustomWeightComparator implements Comparator<Weight> {

        @Override
        public int compare(Weight weight, Weight t1) {
            try {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(weight.getDate());
                Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(t1.getDate());
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return -1;
        }
    }
    public static class CustomExecutionComparator implements Comparator<TrainingExecution> {

        @Override
        public int compare(TrainingExecution trainingExecution, TrainingExecution t1) {
            try {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(trainingExecution.getDate());
                Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(t1.getDate());
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return -1;
        }
    }
}
