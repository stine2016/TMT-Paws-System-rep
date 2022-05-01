package com.zene.tmtpawssyetm.Model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.zene.tmtpawssyetm.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    Context context;

    ArrayList<Infrared> list;

    public MainAdapter(Context context, ArrayList<Infrared> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.infrareditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        Infrared infrared = list.get(position);
        int redColorValue = Color.RED;
        int orangeColorValue = Color.parseColor("#FFA500");
        int yellowColorValue = Color.YELLOW;
        int blueColorValue = Color.BLUE;
        int skyblueColorValue = Color.parseColor("#87CEEB");
//        holder.content1.setText(infrared.getCounter());
        if(infrared.getTemp() >= 38){
            holder.mCardView.setCardBackgroundColor(redColorValue);
        }
        else if(infrared.getTemp() >= 35 && infrared.getTemp() <= 37){
            holder.mCardView.setCardBackgroundColor(orangeColorValue);
        }
        else if(infrared.getTemp() >= 31 && infrared.getTemp() <= 34){
            holder.mCardView.setCardBackgroundColor(yellowColorValue);
        }
        else if(infrared.getTemp() <= 30){
            holder.mCardView.setCardBackgroundColor(blueColorValue);
        }
        else if(infrared.getTemp() <= 25){
            holder.mCardView.setCardBackgroundColor(skyblueColorValue);
        }
        holder.content2.setText(Long.toString(infrared.getTemp()) + "°C");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView content1;
        public TextView content2;
        CardView mCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            content1 = itemView.findViewById(R.id.content);
            content2 = itemView.findViewById(R.id.content2);
            mCardView = itemView.findViewById(R.id.noteCard);

        }
    }
}
