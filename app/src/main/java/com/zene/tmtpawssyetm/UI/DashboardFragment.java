package com.zene.tmtpawssyetm.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zene.tmtpawssyetm.Model.SensorData;
import com.zene.tmtpawssyetm.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    TextView textView2;
    ImageView imageThermo, imageCamera, imageNotification, imageThermal, imageGraph, imageTable, imageSchedule, imageBarChart, imageUserManual;
    MaterialButton btnCommunity;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    DatabaseReference databaseReference, databaseReference2, addDataTemp;
    FirebaseAuth fAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        textView2 = view.findViewById(R.id.textView2);
        imageThermo = view.findViewById(R.id.imageThermo);
        imageCamera = view.findViewById(R.id.imageCamera);
        imageNotification = view.findViewById(R.id.imageNotification);
        imageThermal = view.findViewById(R.id.imageThermal);
        imageGraph = view.findViewById(R.id.imageGraph);
        imageTable = view.findViewById(R.id.imageTable);
        imageSchedule = view.findViewById(R.id.imageSchedule);
        imageBarChart = view.findViewById(R.id.imageBarChart);
        imageUserManual = view.findViewById(R.id.imageUserManual);
        btnCommunity = view.findViewById(R.id.btnCommunity);

        isUser();

        imageThermo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TemperatureFragment temperatureFragment = new TemperatureFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), temperatureFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        imageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isUserCamera();
            }
        });

        imageNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationFragment notificationFragment = new NotificationFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), notificationFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://discord.gg/jMGXsnSyMq");
            }
        });

        imageThermal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfraredFragment infraredFragment = new InfraredFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), infraredFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        imageGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BarchartFragment barchartFragment = new BarchartFragment();
                ChartFragment chartFragment = new ChartFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), chartFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        imageTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TemperatureTableFragment temperatureTableFragment = new TemperatureTableFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), temperatureTableFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        imageSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNote addNote = new AddNote();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), addNote, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        imageBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BarchartFragment barchartFragment = new BarchartFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), barchartFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        imageUserManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://docs.google.com/document/d/14OecKvakJMlKmio92hQkKwcmMTcA30vJaTMIvUNgVLI/edit?usp=sharin");
            }
        });

        return view;
    }

    private boolean adduserTemp() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        databaseReference = firebaseDatabase.getReference("userInfo").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String serialnumber = snapshot.child("serialnumber").getValue(String.class);

                    addDataTemp = FirebaseDatabase.getInstance().getReference().child("SensorData").child(serialnumber);

                    SensorData sensorData = new SensorData(35, 35);

                    addDataTemp.setValue(sensorData);
                }
                else{
                    Toast.makeText(getContext(), "Intruder Alert!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }
    private void isUser() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        databaseReference = firebaseDatabase.getReference("userInfo").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue(String.class);

                    textView2.setText(name);
                }
                else{
                    Toast.makeText(getContext(), "Intruder Alert!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void isUserCamera() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        databaseReference = firebaseDatabase.getReference("userInfo").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String serialnumber = snapshot.child("serialnumber").getValue(String.class);

                    databaseReference2 = firebaseDatabase.getReference("SensorData").child(serialnumber).child("CameraIP");

                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String ipAddress = snapshot.getValue(String.class);

                                gotoUrl("http://" + ipAddress);
                            }
                            else{
                                Toast.makeText(getContext(), "No Camera Detected", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(getContext(), "Intruder Alert!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}