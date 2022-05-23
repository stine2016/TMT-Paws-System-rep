package com.zene.tmtpawssyetm.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.series.DataPoint;
import com.zene.tmtpawssyetm.Model.Infrared;
import com.zene.tmtpawssyetm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class tablelist extends AppCompatActivity {/*
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    FirebaseAuth fAuth;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm");

    RecyclerView recyclerView;
    DatabaseReference reference;
    tableadapter adapter;
    ArrayList<Infrared> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.table_layout);

       recyclerView = findViewById(R.id.table_list);
       reference = firebaseDatabase.getReference("userInfo").child(user.getUid());
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       list = new ArrayList<>();

       adapter = new tableadapter(this,list);
       recyclerView.setAdapter(adapter);
       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

              /*for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                   temperature_table temp = dataSnapshot.getValue(temperature_table.class);
                   list.add(temp);
               }
              adapter.notifyDataSetChanged();*/
/*
               DataPoint[] dp = new DataPoint[(int) snapshot.getChildrenCount()];
               int index = 0;
               for(DataSnapshot myDataSnapshot:snapshot.getChildren()){
                   Infrared.PointValues pointValues = myDataSnapshot.getValue(Infrared.PointValues.class);
                   dp[index] = new DataPoint(index, pointValues.getCaltemp(),pointValues.getTs());
                   index++;
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }


*/

}
