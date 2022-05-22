package com.zene.tmtpawssyetm.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TemperatureTable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemperatureTable extends Fragment {

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

    public TemperatureTable() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TemperatureTable.
     */
    // TODO: Rename and change types and number of parameters
    public static TemperatureTable newInstance(String param1, String param2) {
        TemperatureTable fragment = new TemperatureTable();
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
        View view = inflater.inflate(R.layout.fragment_temperature_table2, container, false);

        recyclerView = view.findViewById(R.id.tableRecycleView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

//    private void setRecyclerView() {
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        tableAdapter = new TableAdapter(getContext(), getList());
//        recyclerView.setAdapter(tableAdapter);
//    }
//
//    private List<TableModel> getList(){
//        List<TableModel> table_list = new ArrayList<>();
//        table_list.add(new TableModel(29, 1653235316661L));
//        table_list.add(new TableModel(30, 1653235363900L));
//        table_list.add(new TableModel(28, 1653235411921L));
//        table_list.add(new TableModel(31, 1653235411921L));
//        return table_list;
//    }
}