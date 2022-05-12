package com.zene.tmtpawssyetm.UI;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.zene.tmtpawssyetm.Model.Infrared;
import com.zene.tmtpawssyetm.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TemperatureGraph#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemperatureGraph extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    LineChart lineChart;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    MaterialButton scrollview;
    DatabaseReference databaseReference, databaseReference2;
    FirebaseAuth fAuth;
    GraphView graphView;
    LineGraphSeries series;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm");
    int counter = 0;
//    LineDataSet lineDataSet = new LineDataSet(null,null);
//    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
//    LineData lineData;

    public TemperatureGraph() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TemperatureGraph.
     */
    // TODO: Rename and change types and number of parameters
    public static TemperatureGraph newInstance(String param1, String param2) {
        TemperatureGraph fragment = new TemperatureGraph();
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
        View view = inflater.inflate(R.layout.fragment_temperature_graph, container, false);

//        lineChart = view.findViewById(R.id.linearChart);
//
//        isUser();
//        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        lineDataSet.setDrawFilled(true);
//        lineDataSet.setFillColor(ContextCompat.getColor(getContext(),R.color.lightGreen));
//        lineDataSet.setLineWidth(4);

        graphView = view.findViewById(R.id.graphView);
        scrollview = view.findViewById(R.id.scrollview);

        series = new LineGraphSeries();
        graphView.addSeries(series);


        firebaseDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        databaseReference = firebaseDatabase.getReference("userInfo").child(user.getUid());


//        graphView.getViewport().setYAxisBoundsManual(true);
//        graphView.getViewport().setMinY(0);
//        graphView.getViewport().setMaxY(50);
//
//        graphView.getViewport().setXAxisBoundsManual(true);
//        graphView.getViewport().setMinX(4);
//        graphView.getViewport().setMaxX(12);
//
//        graphView.getViewport().setScrollable(true);
//        graphView.getViewport().setScrollableY(true);

//        graphView.getViewport().setScalable(true);
//        graphView.getViewport().setScalableY(true);

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX){
                    return simpleDateFormat.format(new Date((long) value));
                }else {
                    return super.formatLabel(value, false);
                }
            }
        });

//        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
//        staticLabelsFormatter.setHorizontalLabels(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
//        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

//        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));

        graphView.getGridLabelRenderer().setNumVerticalLabels(6);
//        graphView.getViewport().setScrollable(true);
//        graphView.getGridLabelRenderer().setNumHorizontalLabels(3);
//        graphView.getGridLabelRenderer().setNumHorizontalLabels(3);

        series.setColor(Color.RED);
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.argb(60, 95, 226, 156));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(5);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                String msg = "hour: " + simpleDateFormat.format(new Date((long) dataPoint.getX())) + "\nTemperature:" + dataPoint.getY() + "°C";
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
            }
        });

        scrollview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphView.getGridLabelRenderer().setNumVerticalLabels(7);
                graphView.getViewport().setScalable(true);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String serialnumber = snapshot.child("serialnumber").getValue(String.class);

                    databaseReference2 = firebaseDatabase.getReference("ChartValues").child(serialnumber);

                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            DataPoint[] dp = new DataPoint[(int) snapshot.getChildrenCount()];
                            int index = 0;
                            for(DataSnapshot myDataSnapshot:snapshot.getChildren()){
                                Infrared.PointValues pointValues = myDataSnapshot.getValue(Infrared.PointValues.class);

//                                Date d = new Date(pointValues.getTimestamp());
//                                String date = new SimpleDateFormat("dd-MM", Locale.getDefault()).format(d);

                                dp[index] = new DataPoint(pointValues.getTs(), pointValues.getCaltemp());
                                index++;
                            }

                            series.resetData(dp);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(getContext(), "Intruder Alert!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //    private void isUser() {
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        fAuth = FirebaseAuth.getInstance();
//        user = fAuth.getCurrentUser();
//
//        databaseReference = firebaseDatabase.getReference("userInfo").child(user.getUid());
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    String serialnumber = snapshot.child("serialnumber").getValue(String.class);
//
//                    databaseReference2 = firebaseDatabase.getReference("ChartValues").child(serialnumber);
//
//                    databaseReference2.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                            ArrayList<Entry> dataVals = new ArrayList<Entry>();
//                            ArrayList<Map.Entry> datasa = new ArrayList<Map.Entry>();
//
//                            if(snapshot.hasChildren()){
//                                for(DataSnapshot myDataSnapshot:snapshot.getChildren()){
//                                    DataPoint dataPoint = myDataSnapshot.getValue(DataPoint.class);
//                                    dataVals.add(new Entry(dataPoint.getTimestamp(),dataPoint.getTemperature()));
//                                }
//                                showChart(dataVals);
//                            }
//                            else{
//                                lineChart.clear();
//                                lineChart.invalidate();
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                else{
//                    Toast.makeText(getContext(), "Intruder Alert!!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    private void showChart(ArrayList<Entry> dataVals){
//        lineDataSet.setValues(dataVals);
//        lineDataSet.setLabel("DataSet 1");
//        iLineDataSets.clear();
//        iLineDataSets.add(lineDataSet);
//        lineData = new LineData(iLineDataSets);
//        lineChart.clear();
//        lineChart.setData(lineData);
//        lineChart.invalidate();
//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setValueFormatter(new MyAxisValueFormatter());
//    }
//
//    private class MyAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter{
//
//        @Override
//        public String getFormattedValue(float value, AxisBase axis) {
//            Date d = new Date(Float.valueOf(value).longValue());
//            String date = new SimpleDateFormat("dd-MM", Locale.getDefault()).format(d);
//            return date;
//        }
//    }
}