package com.example.trainx.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trainx.models.FinishedTraining;
import com.example.trainx.models.Measurements;
import com.example.trainx.models.ShuffleExercise;
import com.example.trainx.models.ShuffleTraining;
import com.example.trainx.models.TrainingExecution;
import com.example.trainx.models.TrainingPlan;
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
    private static DataManager INSTANCE;
    private int numberOfLoads = 0;
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

    private DataManager(MainActivity activity) {
        initArrays();
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        readTrainingPlansData(mDatabase, currentUser, activity);
        readExecutionTrainingsData(mDatabase, currentUser, activity);
        readFinishedTrainingsData(mDatabase, currentUser, activity);
        readWeightData(mDatabase, currentUser, activity);
        readShuffleData(mDatabase, currentUser, activity);
    }

    public static DataManager getInstance(MainActivity mainActivity) {
        if(INSTANCE == null) {
            INSTANCE = new DataManager(mainActivity);
        }
        return INSTANCE;
    }

    public static DataManager getInstance() {
        return INSTANCE;
    }

    private void initArrays() {
        measurements = new Measurements();
        trainingPlans = new ArrayList<>();
        trainingExecutions = new ArrayList<>();
        finishedTrainings = new ArrayList<>();
        weightsUser = new ArrayList<>();
        shuffleTrainings = new ArrayList<>();
    }

    private void readTrainingPlansData(DatabaseReference mDatabase, FirebaseUser currentUser, MainActivity activity) {
        DatabaseReference ref = mDatabase.child("users").child(currentUser.getUid()).child("Plans");
        if(ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot plansSnapshot : snapshot.getChildren()) {
                        TrainingPlan newTrainingPlan = plansSnapshot.getValue(TrainingPlan.class);
                        trainingPlans.add(newTrainingPlan);
                    }
                    numberOfLoads++;
                    numberOfLoads++;
                    Log.v("numberOfLoads", "training plans: " + numberOfLoads);
                    if(numberOfLoads == 5) {
                        activity.display();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    throw error.toException();
                }
            });
        } else {
            numberOfLoads++;
            Log.v("numberOfLoads", "training plans: " + numberOfLoads);
            if(numberOfLoads == 5) {
                activity.display();
            }
        }


    }

    private void readExecutionTrainingsData(DatabaseReference mDatabase, FirebaseUser currentUser, MainActivity activity){
        DatabaseReference ref = mDatabase.child("users").child(currentUser.getUid()).child("Execution");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot plansSnapshot : snapshot.getChildren()) {
                    TrainingExecution newTrainingExecution = plansSnapshot.getValue(TrainingExecution.class);
                    trainingExecutions.add(newTrainingExecution);
                }
                Collections.sort(trainingExecutions, new CustomExecutionComparator());
                numberOfLoads++;
                Log.v("numberOfLoads", "execution: " + numberOfLoads);
                if(numberOfLoads == 5) {

                    activity.display();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

    }

    private void readFinishedTrainingsData(DatabaseReference mDatabase, FirebaseUser currentUser, MainActivity activity) {
        DatabaseReference ref = mDatabase.child("users").child(currentUser.getUid()).child("Finished Trainings");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot plansSnapshot : snapshot.getChildren()) {
                    FinishedTraining newTrainingExecution = plansSnapshot.getValue(FinishedTraining.class);
                    finishedTrainings.add(newTrainingExecution);
                }

                numberOfLoads++;
                Log.v("numberOfLoads", "finished: " + numberOfLoads);
                if(numberOfLoads == 5) {

                    activity.display();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    private void readWeightData(DatabaseReference mDatabase, FirebaseUser currentUser, MainActivity activity) {
        DatabaseReference ref = mDatabase.child("users").child(currentUser.getUid()).child("Weight");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot plansSnapshot : snapshot.getChildren()) {
                    Weight newWeight = plansSnapshot.getValue(Weight.class);
                    weightsUser.add(newWeight);
                }
                Collections.sort(weightsUser, new CustomWeightComparator());
                numberOfLoads++;
                Log.v("numberOfLoads", "weight: " + numberOfLoads);
                if(numberOfLoads == 5) {

                    activity.display();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    private void readShuffleData(DatabaseReference mDatabase, FirebaseUser currentUser, MainActivity activity) {
        boolean exists = false;
        DatabaseReference ref = mDatabase.child("users").child(currentUser.getUid()).child("Shuffle");
        Log.v("shuffle ref", "shuffle: " + ref);

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
                    if(numberOfLoads == 5) {
                        activity.display();
                    }
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
        updateNextTrainingIfNecessary(te);
    }

    public void updateNextTrainingIfNecessary(TrainingExecution te) {
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDateText = df.format(currentDate);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("OverviewInfo");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String date = snapshot.child("nextTraining").getValue(String.class);
                try {
                    Date today = new SimpleDateFormat("yyyy-MM-dd").parse(currentDateText);
                    Date oldDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                    Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(te.getDate());
                    if(oldDate.before(today) && newDate.after(today))
                        ref.child("nextTraining").setValue(te.getDate());
                    else
                        if(newDate.after(today) && newDate.before(oldDate))
                            ref.child("nextTraining").setValue(te.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref.child("nextTraining").setValue(te.getDate());
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
        DatabaseReference refOverview = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("OverviewInfo");
        refOverview.child("currentWeight").setValue(newWeight.getWeight());
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
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String date;
                int dayStrike;
                double currentWeight;
                String lastDate;
                if(snapshot.child("nextTraining").getValue(String.class) != null)
                    date = snapshot.child("nextTraining").getValue(String.class);
                else
                    date = "not specified";
                if(snapshot.child("daysInARow").getValue(Integer.class) != null)
                    dayStrike = snapshot.child("daysInARow").getValue(Integer.class);
                else
                    dayStrike = 0;
                if(snapshot.child("currentWeight").getValue(Double.class) != null)
                    currentWeight = snapshot.child("currentWeight").getValue(Double.class);
                else
                    currentWeight = 0;
                if(snapshot.child("lastLoggedDate").getValue(String.class) != null)
                    lastDate = snapshot.child("lastLoggedDate").getValue(String.class);
                else
                    lastDate = "1970-01-01";
                Log.i("OverviewInfo", lastDate);
                try {
                    callback.onDataReceived(date, currentWeight, dayStrike, lastDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public static class CustomWeightComparator implements Comparator<Weight> {
        @Override
        public int compare(Weight weight, Weight t1) {
            try {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).parse(weight.getDate());
                Date date2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(t1.getDate());
                assert date1 != null;
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
                Date date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(trainingExecution.getDate());
                Date date2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(t1.getDate());
                assert date1 != null;
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return -1;
        }
    }
    public static void updateDaysInARow(int days, String todayDate) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("OverviewInfo");
        ref.child("daysInARow").setValue(days);
        ref.child("lastLoggedDate").setValue(todayDate);
    }
}
