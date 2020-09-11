package com.example.trainx.adapters;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainx.R;
import com.example.trainx.models.Measure;
import com.example.trainx.models.Measurements;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MeasurementsAdapter extends RecyclerView.Adapter<MeasurementsAdapter.MyViewHolder>  {
    Context context;
    boolean isCardExpanded;
    String[] measurementNames;
    ArrayList<ArrayList<Measure>> measures;
    public MeasurementsAdapter(String[] dataSet, ArrayList<ArrayList<Measure>> me, Context context) {
        this.measurementNames = dataSet;
        this.measures = me;
        this.context = context;
    }

    @NonNull
    @Override
    public MeasurementsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.measure_card_recycler_layout, parent, false);
        return new MeasurementsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MeasurementsAdapter.MyViewHolder holder, int position) {
        holder.nameMeasureText.setText(measurementNames[position]);
        try {
            setMeasureChart(holder.lineChart, measures.get(position));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.updateMeasureButtonData.setOnClickListener(view -> createDialog(position, holder.lineChart));

        holder.expandButton.setOnClickListener(view -> expandCard(view, holder, position));
    }


    private void expandCard(View view, MeasurementsAdapter.MyViewHolder holder, int position) {
        prepareRecyclerView(view, holder.recyclerView, measures.get(position));
        TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
        if(isCardExpanded){
            holder.recyclerView.setVisibility(View.GONE);
            holder.expandButton.setIconResource(R.drawable.ic_baseline_arrow_drop_down_24);
            isCardExpanded = false;
        } else {
            holder.recyclerView.setVisibility(View.VISIBLE);
            holder.expandButton.setIconResource(R.drawable.ic_baseline_arrow_drop_up_24);
            isCardExpanded = true;
        }
    }

    @Override
    public int getItemCount() {
        return measurementNames.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public  MaterialCardView cardView;
        public MaterialTextView nameMeasureText;
        public LineChart lineChart;
        public MaterialButton expandButton;
        public Button updateMeasureButtonData;
        public RecyclerView recyclerView;
        public MyViewHolder(View v) {
            super(v);
            nameMeasureText = (MaterialTextView) v.findViewById(R.id.nameMeasureText);
            updateMeasureButtonData = (Button) v.findViewById(R.id.updateMeasureButtonData);
            lineChart = (LineChart) v.findViewById(R.id.measureChart);
            expandButton = (MaterialButton) v.findViewById(R.id.expandButton);
            recyclerView = (RecyclerView) v.findViewById(R.id.dataMeasureRecycler);
            cardView = (MaterialCardView) v.findViewById(R.id.measureCard);
        }
    }

    private void setMeasureChart(LineChart lineChart, ArrayList<Measure> measures) throws ParseException {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        ValueFormatter dateFormat = new MyAXisFormatter();
        xAxis.setValueFormatter(dateFormat);
        List<Entry> entries = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        for(Measure m : measures) {
            Date date = format.parse(m.getDate());
            assert date != null;
            entries.add(new Entry((long) date.getTime(), (float) m.getValue()));
        }
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }
    private static class MyAXisFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            Date date = new Date((long)value);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return sdf.format(date);
        }
    }
    private void prepareRecyclerView(View view, RecyclerView recyclerView, ArrayList<Measure> measures){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        MeasureAdapter mAdapter = new MeasureAdapter(measures);
        recyclerView.setAdapter(mAdapter);
    }

    private void createDialog(int position, LineChart lineChart){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.updatecustondialog,null);
        TextInputEditText valueInput = (TextInputEditText) dialogView.findViewById(R.id.valueInputEdit);
        TextInputEditText dateInput = (TextInputEditText) dialogView.findViewById(R.id.dateInputEdit);
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
            myCalendar.set(Calendar.YEAR, i);
            myCalendar.set(Calendar.MONTH, i1);
            myCalendar.set(Calendar.DAY_OF_MONTH, i2);
            updateLabel(dateInput, myCalendar);
        };
        dateInput.setOnClickListener(view -> new DatePickerDialog(view.getContext(),date,myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        builder.setView(dialogView)
                .setPositiveButton("Save", (dialogInterface, i) -> {
                    double value = Double.parseDouble(Objects.requireNonNull(valueInput.getText()).toString());
                    String date1 = Objects.requireNonNull(dateInput.getText()).toString();
                    Measure newMeasure = new Measure(date1, value);
                    measures.get(position).add(newMeasure);
                    Collections.sort(measures.get(position), new Measurements.CustomComparator());
                    Measurements.addToMeasurementsList(measures.get(position), measurementNames[position], newMeasure);
                    try {
                        setMeasureChart(lineChart, measures.get(position));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {});
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    private void updateLabel(TextInputEditText editText,Calendar calendar){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        editText.setText(sdf.format(calendar.getTime()));
    }



}
