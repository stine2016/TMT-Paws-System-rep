package com.zene.tmtpawssyetm.Model;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.zene.tmtpawssyetm.R;
import com.zene.tmtpawssyetm.UI.NoteDetails;
import com.zene.tmtpawssyetm.UI.notificationdetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    Context context;

    ArrayList<NotificationTest> list;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E MMMM dd yyyy hh:mm a");

    public NotificationAdapter(Context context, ArrayList<NotificationTest> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.not_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        NotificationTest notificationTest = list.get(position);
        holder.content.setText(notificationTest.getNotification());
        holder.title1.setText(notificationTest.getTemp());
        holder.titles.setText(notificationTest.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                notificationdetails note = new notificationdetails();
                Bundle bundle = new Bundle();
                bundle.putString("notification", notificationTest.getNotification());
                bundle.putString("date", notificationTest.getDate());
                bundle.putString("temp", notificationTest.getTemp());
                note.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, note).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView content;
        public TextView titles;
        public TextView title1;
        CardView noteCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            content = itemView.findViewById(R.id.content);
            titles = itemView.findViewById(R.id.titles);
            title1 = itemView.findViewById(R.id.title1);
            noteCard = itemView.findViewById(R.id.noteCard);

        }
    }
}
