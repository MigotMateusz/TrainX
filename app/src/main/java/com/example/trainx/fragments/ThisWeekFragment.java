package com.example.trainx.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.trainx.activities.PlannerActivity;
import com.example.trainx.R;
import com.example.trainx.activities.TrainerActivity;
import com.example.trainx.data.DataManager;
import com.example.trainx.models.TrainingExecution;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ThisWeekFragment extends Fragment {

    public ThisWeekFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_this_week, container, false);

        setDayCard(myView);
        ExtendedFloatingActionButton plannerButton = myView.findViewById(R.id.plannerButton);
        plannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PlannerActivity.class);
                Bundle bundleData = getArguments();
                if(bundleData!=null)
                    Log.i("ThisWeekLog", "not null");
                intent.putExtra("DataManager", bundleData);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);

                Toast.makeText(getActivity(),"Test OnClick", Toast.LENGTH_SHORT).show();
            }
        });

        return myView;
    }

    public void setDayCard(View myView){
        DataManager dataManager = (DataManager) getArguments().getSerializable("DataManager");

        MaterialCardView[] weekCardView = {myView.findViewById(R.id.mondayCard),
        myView.findViewById(R.id.tuesdayCard), myView.findViewById(R.id.wednesdayCard),
        myView.findViewById(R.id.thursdayCard), myView.findViewById(R.id.fridayCard),
        myView.findViewById(R.id.saturdayCard), myView.findViewById(R.id.sundayCard)};

        MaterialTextView[] weekTextView = { myView.findViewById(R.id.mondayTraining),
        myView.findViewById(R.id.tuesdayTraining), myView.findViewById(R.id.wednesdayTraining),
        myView.findViewById(R.id.thursdayTraining), myView.findViewById(R.id.fridayTraining),
        myView.findViewById(R.id.saturdayTraining), myView.findViewById(R.id.sundayTraining) };


        FloatingActionButton[] floatingActionButtons = {myView.findViewById(R.id.mondayButton),
                myView.findViewById(R.id.tuesdayButton), myView.findViewById(R.id.wednesdayButton),
                myView.findViewById(R.id.thursdayButton), myView.findViewById(R.id.fridayButton),
                myView.findViewById(R.id.saturdayButton), myView.findViewById(R.id.sundayButton) };

        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date;

        for(int i = 0; i < 7; i++){
            date = df.format(c.getTime());
            String addText = ": no training";
            setListenerNoTrainingWeekCardView(weekCardView[i]);
            setListenerNoTrainingFloatingButton(floatingActionButtons[i]);
            for(TrainingExecution te : dataManager.getTrainingExecutions()){
                Log.i("DataManagerLog", te.getUnit());
                if(te.getDate().equals(date)){
                    addText = ": " + te.getUnit();
                    //date = date + ": " + te.getUnit();
                    setListenerTrainingActiveFloatingButton(floatingActionButtons[i]);
                    onClickWeekCardView(weekCardView[i], c, dataManager);
                    break;
                }
            }
            weekTextView[i].setText(date + addText);
            c.add(Calendar.DAY_OF_MONTH, 1);
        }

    }

    public void onClickWeekCardView(MaterialCardView cardView, Calendar calendar, DataManager dM) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date;
        date = df.format(calendar.getTime());
        setListenerWeekCardView(cardView, date, dM);
    }

    public void setListenerWeekCardView(MaterialCardView cardView, String date, DataManager dM) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(getContext(), TrainerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DataManager", dM);
                bundle.putString("Date", date);
                newIntent.putExtra("Bundle", bundle);
                startActivity(newIntent);
            }
        });
    }
    public void setListenerNoTrainingWeekCardView(MaterialCardView cardView) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "There is no training planned on this day", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setListenerNoTrainingFloatingButton(FloatingActionButton floatingButton) {
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "There is no training planned on this day", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setListenerTrainingActiveFloatingButton(FloatingActionButton floatingButton){
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "O tak tak byczku, trenujemy", Toast.LENGTH_SHORT).show();
            }
        });
    }

}