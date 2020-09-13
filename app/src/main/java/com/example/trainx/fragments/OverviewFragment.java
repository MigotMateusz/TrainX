package com.example.trainx.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OverviewFragment extends Fragment {
    MaterialTextView yourText;
    MaterialTextView nextText;
    MaterialTextView dayTextView;

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
        dayTextView = myView.findViewById(R.id.dayText);
        yourText = myView.findViewById(R.id.yourText);
        nextText = myView.findViewById(R.id.nextTrainingDayText);
        MaterialTextView weightTextView = myView.findViewById(R.id.weightTextView);
        DataManager.loadOverviewData(new OnOverviewDataReceive() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataReceived(String date, double weight, int strike, String lastDate) throws ParseException {
                setDaysInARow(strike, lastDate);
                weightTextView.setText(String.valueOf(weight));
                nextText.setText(date);
            }
        });

        try {
            DataManager dataManager = (DataManager) getArguments().getSerializable("DataManager");

            if(dataManager != null)
                if(dataManager.isTrainingToday() == true){
                    button.setVisibility(View.VISIBLE);
                    yourText.setText("You have training planned today");
                    nextText.setVisibility(View.GONE);
                    button.setOnClickListener(view -> {
                        if (dataManager.isTrainingDone())
                            Toast.makeText(getActivity(), "Training is done already", Toast.LENGTH_SHORT).show();
                        else {
                            Intent intent = new Intent(getContext(), TrainingExecActivity.class);
                            Bundle bundle = getArguments();
                            intent.putExtra("DataManager", bundle);
                            startActivity(intent);
                        }
                    });
                }
                else {
                    Date currentDate = Calendar.getInstance().getTime();
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String date = df.format(currentDate);
                    for(int i = 0; i < dataManager.getTrainingExecutions().size(); i++) {
                        Date today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date);
                        Date executionDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(
                                dataManager.getTrainingExecutions().get(i).getDate());
                        if(executionDate.after(today)) {
                            Log.i("OverviewLog", dataManager.getTrainingExecutions().get(i).getDate());
                            nextText.setText(dataManager.getTrainingExecutions().get(i).getDate());
                            break;
                        }
                    }
                }



            prepareWeightTextView(myView,dataManager);
            prepareUpdateButton(myView, dataManager);
            prepareShuffleButton(myView, dataManager);
        } catch(NullPointerException | ParseException exception) {
            Toast.makeText(getActivity(), "Couldn't load data", Toast.LENGTH_SHORT).show();
        }

        return myView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDaysInARow(int days, String date) throws ParseException {
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = df.format(currentDate);
        Date today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(todayDate);
        Date lastDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date);
        int next_day;
        if(today.equals(lastDate))
            next_day = days;
        else {
            long daysBetween = (today.getTime() - lastDate.getTime()) / (1000*60*60*24);
            if(daysBetween == 1)
                next_day = days + 1;
            else
                next_day = 1;

        }
        dayTextView.setText(String.valueOf(next_day));
        DataManager.updateDaysInARow(next_day, todayDate);
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