package com.zene.tmtpawssyetm.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zene.tmtpawssyetm.Model.Infrared;
import com.zene.tmtpawssyetm.R;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>{

    private List<Infrared.ScreenItems> screenItems;

    public OnboardingAdapter(List<Infrared.ScreenItems> screenItems) {
        this.screenItems = screenItems;
    }



    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.screen_items, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {

        holder.setOnboardingData(screenItems.get(position));
    }

    @Override
    public int getItemCount() {
        return screenItems.size();
    }

    class OnboardingViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;
        private TextView textDescription;
        private ImageView imageOnboarding;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            imageOnboarding = itemView.findViewById(R.id.imageOnboarding);
        }

        void setOnboardingData(Infrared.ScreenItems screenItems){
            textTitle.setText(screenItems.getTitle());
            textDescription.setText(screenItems.getDescription());
            imageOnboarding.setImageResource(screenItems.getImage());
        }

    }
}
