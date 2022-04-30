package com.zene.tmtpawssyetm.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.zene.tmtpawssyetm.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TemperatureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemperatureFragment extends Fragment {

    MaterialButton btnThermal, bgraph;
    TextView temperature1, temperature2;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    DatabaseReference databaseReference, databaseReference2;
    FirebaseAuth fAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TemperatureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TemperatureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TemperatureFragment newInstance(String param1, String param2) {
        TemperatureFragment fragment = new TemperatureFragment();
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
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);

        btnThermal = view.findViewById(R.id.btnThermal);
        bgraph = view.findViewById(R.id.btnGraph);
        temperature1 = view.findViewById(R.id.temperature1);
        temperature2 = view.findViewById(R.id.temperature2);

        isUser();

        btnThermal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfraredFragment infraredFragment = new InfraredFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), infraredFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        bgraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TemperatureGraph temperatureGraph = new TemperatureGraph();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), temperatureGraph, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
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
                    String serialnumber = snapshot.child("serialnumber").getValue(String.class);

                    databaseReference2 = firebaseDatabase.getReference("SensorData").child(serialnumber);

                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Float temperature = snapshot.child("Caltemp").getValue(Float.class);

                            String Temp = Float.toString(temperature);
                            temperature1.setText(Temp + "°");
                            temperature2.setText(Temp + "°");
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
}