package com.example.trainx;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataFragment extends Fragment {
    private boolean isExpanded = false;

    public DataFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_data, container, false);
        DataManager dataManager = (DataManager) getArguments().getSerializable("DataManager");
        try {
            setWeightChart(myView, dataManager.getWeightsUser());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        updateWeight(myView,dataManager);
        prepareExpandButton(myView);
        prepareRecyclerView(myView, dataManager);
        return myView;
    }
    private void setWeightChart(View myView, ArrayList<Weight> weights) throws ParseException {
        LineChart weightLineChart = (LineChart) myView.findViewById(R.id.weightChart);
        XAxis xAxis = weightLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        ValueFormatter dateFormat = new MyAXisFormatter();
        xAxis.setValueFormatter(dateFormat);
        int i = 1;
        List<Entry> entries = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for(Weight w : weights) {
            Date date = format.parse(w.getDate());
            entries.add(new Entry((long) date.getTime(), (float) w.getWeight()));
            i++;
        }
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        LineData lineData = new LineData(dataSet);
        weightLineChart.setData(lineData);
        lineData.notifyDataChanged();
        weightLineChart.notifyDataSetChanged();
        weightLineChart.invalidate();
    }

    private void updateWeight(View myView, DataManager dataManager) {
        Button updateButton = (Button) myView.findViewById(R.id.updateWeightButtonData);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                        try {
                            setWeightChart(myView, dataManager.getWeightsUser());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
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
    private void prepareExpandButton(View myView) {
        MaterialButton expandButton = myView.findViewById(R.id.expandButton);
        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialButton expandButton = myView.findViewById(R.id.expandButton);
                //MaterialTextView textView = myView.findViewById(R.id.testExpandText);
                RecyclerView recyclerView = (RecyclerView) myView.findViewById(R.id.dataWeightRecycler);
                MaterialCardView cardView = myView.findViewById(R.id.weightCard);
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                if(isExpanded){
                    //textView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    expandButton.setIconResource(R.drawable.ic_baseline_arrow_drop_down_24);
                    isExpanded = false;
                } else {
                    //textView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    expandButton.setIconResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    isExpanded = true;
                }
            }
        });

    }
    private void prepareRecyclerView(View myView, DataManager dataManager){
        ArrayList<Weight> weights = dataManager.getWeightsUser();
        RecyclerView recyclerView = (RecyclerView) myView.findViewById(R.id.dataWeightRecycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        WeightAdapter mAdapter = new WeightAdapter(weights);
        recyclerView.setAdapter(mAdapter);
    }

    private class MyAXisFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            Date date = new Date((long)value);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }
    }
}
