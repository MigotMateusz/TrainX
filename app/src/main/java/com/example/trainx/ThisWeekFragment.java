package com.example.trainx;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
                //Bundle bundleData = new Bundle();
                //bundleData.putSerializable("DataManager1", dataManager);
                //intent.putExtra("DataManager", bundleData);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);

                Toast.makeText(getActivity(),"Test OnClick", Toast.LENGTH_SHORT).show();
            }
        });

        return myView;
    }

    public void setDayCard(View myView){
        MaterialCardView mondayCardView = myView.findViewById(R.id.mondayCard);
        MaterialCardView tuesdayCardView = myView.findViewById(R.id.tuesdayCard);
        MaterialCardView wednesdayCardView = myView.findViewById(R.id.wednesdayCard);
        MaterialCardView thursdayCardView = myView.findViewById(R.id.thursdayCard);
        MaterialCardView fridayCardView = myView.findViewById(R.id.fridayCard);
        MaterialCardView saturdayCardView = myView.findViewById(R.id.saturdayCard);
        MaterialCardView sundayCardView = myView.findViewById(R.id.sundayCard);

        MaterialTextView mondayTextView = myView.findViewById(R.id.mondayTraining);
        MaterialTextView tuesdayTextView = myView.findViewById(R.id.tuesdayTraining);
        MaterialTextView wednesdayTextView = myView.findViewById(R.id.wednesdayTraining);
        MaterialTextView thursdayTextView = myView.findViewById(R.id.thursdayTraining);
        MaterialTextView fridayTextView = myView.findViewById(R.id.fridayTraining);
        MaterialTextView saturdayTextView = myView.findViewById(R.id.saturdayTraining);
        MaterialTextView sundayTextView = myView.findViewById(R.id.sundayTraining);


        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date;
        date = df.format(c.getTime());
        mondayTextView.setText(date);

        c.add(Calendar.DAY_OF_MONTH, 1);
        date = df.format(c.getTime());
        tuesdayTextView.setText(date);

        c.add(Calendar.DAY_OF_MONTH, 1);
        date = df.format(c.getTime());
        wednesdayTextView.setText(date);

        c.add(Calendar.DAY_OF_MONTH, 1);
        date = df.format(c.getTime());
        thursdayTextView.setText(date);

        c.add(Calendar.DAY_OF_MONTH, 1);
        date = df.format(c.getTime());
        fridayTextView.setText(date);

        c.add(Calendar.DAY_OF_MONTH, 1);
        date = df.format(c.getTime());
        saturdayTextView.setText(date);

        c.add(Calendar.DAY_OF_MONTH, 1);
        date = df.format(c.getTime());
        sundayTextView.setText(date);

    }

}