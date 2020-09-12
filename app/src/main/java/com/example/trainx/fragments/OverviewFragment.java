package com.example.trainx.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trainx.R;
import com.example.trainx.activities.ShuffleActivity;
import com.example.trainx.activities.TrainingExecActivity;
import com.example.trainx.data.DataManager;
import com.example.trainx.interfaces.OnOverviewDataReceive;
import com.example.trainx.models.TrainingExecution;
import com.example.trainx.models.Weight;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OverviewFragment extends Fragment {

    public OverviewFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_overview, container, false);
        MaterialButton button = myView.findViewById(R.id.startTrainingButton);
        MaterialTextView dayTextView = myView.findViewById(R.id.dayText);
        MaterialTextView yourText = myView.findViewById(R.id.yourText);
        MaterialTextView nextText = myView.findViewById(R.id.nextTrainingDayText);
        MaterialTextView weightTextView = myView.findViewById(R.id.weightTextView);
        DataManager.loadOverviewData(new OnOverviewDataReceive() {
            @Override
            public void onDataReceived(String date, double weight, int strike) {
                dayTextView.setText(String.valueOf(strike));
                nextText.setText(date);
                weightTextView.setText(String.valueOf(weight));
            }
        });
        //int day = getDays();
        //dayTextView.setText(String.valueOf(day));
        try {
            DataManager dataManager = (DataManager) getArguments().getSerializable("DataManager");

            if(dataManager != null)
                if(dataManager.isTrainingToday() == false){
                    ArrayList<TrainingExecution> exec = null;
                    try {
                        exec = thisWeekTrainings(dataManager);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(exec.size() == 0){
                        nextText.setText("You can make a plan for this week in \"this week\" tab");
                        yourText.setText("There is no planned trainings this week!");
                    }
                    else {
                        button.setVisibility(View.VISIBLE);
                        String date = exec.get(0).getDate();
                        for(int i = 1; i < exec.size(); i++) {
                                //date = exec.get(i).getDate();
                        }
                        yourText.setText("Your next training is on");
                    }

                }
                else {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dataManager.isTrainingDone())
                                Toast.makeText(getActivity(), "Training is done already", Toast.LENGTH_SHORT).show();
                            else {
                                Intent intent = new Intent(getContext(), TrainingExecActivity.class);
                                Bundle bundle = getArguments();
                                intent.putExtra("DataManager", bundle);
                                startActivity(intent);
                            }

                        }
                    });
                }
            prepareWeightTextView(myView,dataManager);
            prepareUpdateButton(myView, dataManager);
            prepareShuffleButton(myView, dataManager);
        } catch(NullPointerException exception) {
            Toast.makeText(getActivity(), "Couldn't load data", Toast.LENGTH_SHORT).show();
        }

        return myView;
    }

    private int getDays() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("name", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int days = sharedPreferences.getInt("days", 0);
        int next_day;
        if(isLastLoggedDayYesterday() == true)
            next_day = days + 1;
        else
            if(isLastLoggedDayToday() == false)
                next_day = 1;
            else
                next_day = days;
        editor.putInt("days", next_day);
        editor.commit();

        return next_day;
    }

    private boolean isLastLoggedDayYesterday(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("name", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Date lastDate = new Date(sharedPreferences.getLong("time", 0));

        Date today = new Date(System.currentTimeMillis());

        long millis1 = today.getTime() - lastDate.getTime();
        long daysBetween = TimeUnit.DAYS.convert(millis1, TimeUnit.MILLISECONDS);
        long millis = today.getTime();
        editor.putLong("time", millis).apply();
        return daysBetween == 1;
    }
    private boolean isLastLoggedDayToday(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("name", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Date lastDate = new Date(sharedPreferences.getLong("time", 0));

        Date today = new Date(System.currentTimeMillis());

        long millis1 = today.getTime() - lastDate.getTime();
        long daysBetween = TimeUnit.DAYS.convert(millis1, TimeUnit.MILLISECONDS);
        long millis = today.getTime();
        editor.putLong("time", millis).apply();
        return daysBetween == 0;
    }

    private void prepareWeightTextView(View myView, DataManager dataManager){
        MaterialTextView weightTextView = myView.findViewById(R.id.weightTextView);
        if(dataManager.getWeightsUser().size() != 0){
            double w = dataManager.getWeightsUser().get(dataManager.getWeightsUser().size()-1).getWeight();
            weightTextView.setText(w + " kg");
        }

        else
            weightTextView.setText("no saved data");
    }

    private ArrayList<TrainingExecution> thisWeekTrainings(DataManager dataManager) throws ParseException {
        ArrayList<TrainingExecution> trainingExecutions = new ArrayList<>();
        Date dateNow = new Date();
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date;
        for(int i = 0; i < 7; i++){
            date = df.format(c.getTime());
            for(TrainingExecution te : dataManager.getTrainingExecutions()){
                Log.i("DataManagerLog", te.getUnit());
                Date now = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                Date trainingDate = new SimpleDateFormat("yyyy-MM-dd").parse(te.getDate());
                if(te.getDate().equals(date)){
                    trainingExecutions.add(te);
                    break;
                }
            }
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        return trainingExecutions;
    }

    private void prepareUpdateButton(View myView, DataManager dataManager) {
        MaterialButton updateButton = myView.findViewById(R.id.updateWeightButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogUpdateWeight);
                builder.setTitle("Update your weight");
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        double w = Double.parseDouble(input.getText().toString());
                        Date currentDate = Calendar.getInstance().getTime();
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String date = df.format(currentDate);
                        dataManager.addNewWeight(new Weight(w, date));
                        prepareWeightTextView(myView, dataManager);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    private void prepareShuffleButton(View myView, DataManager dataManager) {
        MaterialButton shuffleButton = myView.findViewById(R.id.shuffleButton);
        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), ShuffleActivity.class);
                bundle.putSerializable("dataManager", dataManager);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
    }

}