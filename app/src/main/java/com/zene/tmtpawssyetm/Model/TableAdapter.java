package com.zene.tmtpawssyetm.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.type.DateTime;
import com.zene.tmtpawssyetm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    Context context;
    ArrayList<TableShit> table_list;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E MMMM dd yyyy hh:mm a");

    public TableAdapter(Context context, ArrayList<TableShit> table_list) {
        this.context = context;
        this.table_list = table_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(table_list != null && table_list.size() > 0){
            TableShit model = table_list.get(position);
            holder.temp_c.setText(Float.toString(model.getCaltemp()) + "°C");
            holder.time_c.setText(simpleDateFormat.format(new Date(model.getTs())));
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return table_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView temp_c, time_c;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            temp_c = itemView.findViewById(R.id.temp_c);
            time_c = itemView.findViewById(R.id.time_c);
        }
    }
}
