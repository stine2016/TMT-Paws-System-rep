package com.zene.tmtpawssyetm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    OnboardingAdapter onboardingAdapter;
    LinearLayout layoutOnboardingIndicator;
    MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutOnboardingIndicator = findViewById(R.id.layout_indicator);
        buttonOnboardingAction = findViewById(R.id.btnAction);

        setupOnboardingItem();
        final ViewPager2 onboardingViewPager = findViewById(R.id.viewpager_parent);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicator();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                }
                else{
                    startActivity(new Intent(getApplicationContext(), LoginSignupActivity.class));
                    finish();
                }
            }
        });
    }

    private void setupOnboardingItem(){
        List<ScreenItems> screenItems = new ArrayList<>();

        ScreenItems nestingItems = new ScreenItems();
        nestingItems.setTitle("Nesting data");
        nestingItems.setDescription("Show data when nesting");
        nestingItems.setImage(R.drawable.img1);

        ScreenItems tempItems = new ScreenItems();
        tempItems.setTitle("Temperature data");
        tempItems.setDescription("Show temperature of the dog");
        tempItems.setImage(R.drawable.img2);

        ScreenItems graphItems = new ScreenItems();
        graphItems.setTitle("Modern Graphs");
        graphItems.setDescription("Every details you want to know");
        graphItems.setImage(R.drawable.img3);

        screenItems.add(nestingItems);
        screenItems.add(tempItems);
        screenItems.add(graphItems);

        onboardingAdapter = new OnboardingAdapter(screenItems);

    }

    private void setupOnboardingIndicator(){
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for(int i =0; i<indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));

            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicator.addView(indicators[i]);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnboardingIndicator.getChildCount();
        for (int i=0; i<childCount; i++){
            ImageView imageView = (ImageView) layoutOnboardingIndicator.getChildAt(i);
            if( i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),
                                R.drawable.onboarding_indicator_active)
                );
            }
            else{
                imageView.setImageDrawable((
                        ContextCompat.getDrawable(getApplicationContext(),
                                R.drawable.onboarding_indicator_inactive)));
            }
        }

        if (index == onboardingAdapter.getItemCount() - 1) {
            buttonOnboardingAction.setText("Start");
        }else {
            buttonOnboardingAction.setText("Next");
        }
    }
}