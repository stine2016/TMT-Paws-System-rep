package com.zene.tmtpawssyetm.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zene.tmtpawssyetm.R;

import org.w3c.dom.Text;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtUserName;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        txtUserName = (TextView)itemView.findViewById(R.id.userName_textView);
    }

    @Override
    public void onClick(View v) {

    }
}
