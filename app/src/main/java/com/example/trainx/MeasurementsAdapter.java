package com.example.trainx;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Date;
import java.util.List;

public class MeasurementsAdapter extends RecyclerView.Adapter<MeasurementsAdapter.MyViewHolder>  {
    boolean isExpanded;
    String[] names;
    ArrayList<ArrayList<Measure>> measures;
    MeasurementsAdapter(String[] dataSet, ArrayList<ArrayList<Measure>> me) {
        this.names = dataSet;
        this.measures = me;
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
        holder.nameMeasureText.setText(names[position]);

        try {
            setMeasureChart(holder.lineChart, measures.get(position));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.updateMeasureButtonData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"clicked", Toast.LENGTH_SHORT).show();
            }
        });
        holder.expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareRecyclerView(view, holder.recyclerView, measures.get(position));
                TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                if(isExpanded){
                    holder.recyclerView.setVisibility(View.GONE);
                    holder.expandButton.setIconResource(R.drawable.ic_baseline_arrow_drop_down_24);
                    isExpanded = false;
                } else {
                    holder.recyclerView.setVisibility(View.VISIBLE);
                    holder.expandButton.setIconResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    isExpanded = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
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
        ValueFormatter dateFormat = new MeasurementsAdapter.MyAXisFormatter();
        xAxis.setValueFormatter(dateFormat);
        int i = 1;
        List<Entry> entries = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for(Measure m : measures) {
            Date date = format.parse(m.getDate());
            entries.add(new Entry((long) date.getTime(), (float) m.getValue()));
            i++;
        }
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }
    private class MyAXisFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            Date date = new Date((long)value);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }
    }
    private void prepareRecyclerView(View view, RecyclerView recyclerView, ArrayList<Measure> measures){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        MeasureAdapter mAdapter = new MeasureAdapter(measures);
        recyclerView.setAdapter(mAdapter);
    }
}
