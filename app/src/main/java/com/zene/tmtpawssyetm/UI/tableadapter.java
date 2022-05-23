package com.zene.tmtpawssyetm.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zene.tmtpawssyetm.Model.Infrared;
import com.zene.tmtpawssyetm.Model.InfraredShit;
import com.zene.tmtpawssyetm.Model.MainAdapter;
import com.zene.tmtpawssyetm.R;

import java.util.ArrayList;
import java.util.List;


public class tableadapter extends Fragment {
/*
    Context context;
    ArrayList<Infrared> list;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MainAdapter mainAdapter;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    DatabaseReference databaseReference, databaseReference2, databaseReference3;
    FirebaseAuth fAuth;
    ArrayList<InfraredShit> list;

    @NonNull
    @Override
    public tableadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tableadapter.ViewHolder holder, int position) {
       if(list != null&& list.size()>0){
           Infrared table = list.get(position);
       //    holder.id_tv.setText(table.getClass());
           holder.temp_tv.setText((int) table.getTemp());
       //    holder.time_tv.setText( Infrared.getTs());
       }else{
           return;
       }


    }


    @Override
    public int getItemCount() {
        return list.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id_tv,temp_tv,time_tv;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            id_tv = itemView.findViewById(R.id.id_tv);
            temp_tv= itemView.findViewById(R.id.temp_tv);
            time_tv= itemView.findViewById(R.id.time_tv);
        }
    }*/
}
