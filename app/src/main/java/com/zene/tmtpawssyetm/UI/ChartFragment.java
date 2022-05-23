package com.zene.tmtpawssyetm.UI;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zene.tmtpawssyetm.Model.DataPoint;
import com.zene.tmtpawssyetm.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment {

    LineChart lineChart;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference2;
    FirebaseUser user;
    FirebaseAuth fAuth;
    LineDataSet lineDataSet = new LineDataSet(null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    float x =0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartFragment newInstance(String param1, String param2) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        lineChart = view.findViewById(R.id.lineChart);
        firebaseDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        databaseReference = firebaseDatabase.getReference("userInfo").child(user.getUid());


        lineChart.setTouchEnabled(true);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);

        lineChart.setPinchZoom(true);

        lineChart.setBackgroundColor(Color.WHITE);

        LineData data = new LineData();
        data.setValueTextColor(Color.BLACK);

        Legend legend = lineChart.getLegend();

        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextColor(Color.BLACK);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setAvoidFirstLastClipping(true);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setTextColor(Color.BLACK);
        yAxis.setAxisMaxValue(40f);
        yAxis.setDrawGridLines(true);

        YAxis yAxis1 = lineChart.getAxisRight();
        yAxis1.setEnabled(false);

        retrieveData();

        return view;
    }

    private void retrieveData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String serialnumber = snapshot.child("serialnumber").getValue(String.class);

                    databaseReference2 = firebaseDatabase.getReference("ChartValues").child(serialnumber);

                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<Entry> dataVals = new ArrayList<Entry>();

                            if(snapshot.hasChildren()){
                                for(DataSnapshot myDataSnapshot : snapshot.getChildren()){
                                    DataPoint dataPoint = myDataSnapshot.getValue(DataPoint.class);
                                    dataVals.add(new Entry(x, dataPoint.getCaltemp()));
                                    x++;
                                }

                                showChart(dataVals);
                            }
                            else{
                                lineChart.clear();
                                lineChart.invalidate();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showChart(ArrayList<Entry> dataVals) {
        lineDataSet.setValues(dataVals);
        lineDataSet.setLabel("Temperature");
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setCubicIntensity(0.2f);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setColor(ColorTemplate.getHoloBlue());
        lineDataSet.setCircleColor(ColorTemplate.getHoloBlue());
        lineDataSet.setLineWidth(2f);
        lineDataSet.setCircleSize(4f);
        lineDataSet.setFillAlpha(65);
        lineDataSet.setFillColor(ColorTemplate.getHoloBlue());
        lineDataSet.setHighLightColor(Color.rgb(244,117,177));
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(ContextCompat.getColor(getContext(),R.color.themeColor));
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.clear();
        lineChart.setVisibleXRangeMaximum(6);
        lineChart.moveViewToX(lineData.getDataSetCount() - 7);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}