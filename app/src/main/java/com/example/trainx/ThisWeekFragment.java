package com.example.trainx;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ThisWeekFragment extends Fragment {

    public ThisWeekFragment() {
        // Required empty public constructor
    }

    public static ThisWeekFragment newInstance(String param1, String param2) {
        ThisWeekFragment fragment = new ThisWeekFragment();
        return fragment;
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


        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date;

        for(int i = 0; i < 7; i++){
            date = df.format(c.getTime());
            for(TrainingExecution te : dataManager.getTrainingExecutions()){
                if(te.getDate().equals(date)){
                    date = date + "-" + te.getUnit();
                    break;
                }

            }
            weekTextView[i].setText(date);
            c.add(Calendar.DAY_OF_MONTH, 1);
        }

    }

}