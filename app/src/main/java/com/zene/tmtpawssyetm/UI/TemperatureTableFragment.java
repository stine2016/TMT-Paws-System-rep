package com.zene.tmtpawssyetm.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zene.tmtpawssyetm.Model.TableAdapter;
import com.zene.tmtpawssyetm.Model.TableModel;
import com.zene.tmtpawssyetm.Model.TableShit;
import com.zene.tmtpawssyetm.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TemperatureTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemperatureTableFragment extends Fragment {

    RecyclerView recyclerView;
    TableAdapter tableAdapter;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    DatabaseReference databaseReference, databaseReference2, databaseReference3;
    FirebaseAuth fAuth;
    ArrayList<TableShit> list;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TemperatureTableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TemperatureTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TemperatureTableFragment newInstance(String param1, String param2) {
        TemperatureTableFragment fragment = new TemperatureTableFragment();
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
        View view = inflater.inflate(R.layout.fragment_temperature_table, container, false);

        recyclerView = view.findViewById(R.id.tableRecycleView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<TableShit>();
        tableAdapter = new TableAdapter(getContext(), list);
        recyclerView.setAdapter(tableAdapter);

        databaseReference = firebaseDatabase.getReference("userInfo").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String serialnumber = snapshot.child("serialnumber").getValue(String.class);

                    databaseReference2 = firebaseDatabase.getReference("ChartValues").child(serialnumber);

                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list.clear();

                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                TableShit tableModel = dataSnapshot.getValue(TableShit.class);
                                if(tableModel != null){
                                    list.add(tableModel);
                                }
                            }
                            tableAdapter.notifyDataSetChanged();
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

        return view;
    }
}