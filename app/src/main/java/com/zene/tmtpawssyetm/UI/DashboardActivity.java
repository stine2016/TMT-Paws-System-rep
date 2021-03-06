package com.zene.tmtpawssyetm.UI;


import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDeepLinkBuilder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zene.tmtpawssyetm.R;
import com.zene.tmtpawssyetm.databinding.ActivityMainBinding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    DatabaseReference databaseReference, databaseReference2;
    FirebaseAuth fAuth;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
//
//        Intent intent = new Intent(this, backgroundProcess.class);
//        intent.setAction("BackgroundProcess");
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, 10, pendingIntent);
//
//        finish();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
            intent.putExtra("Check", true);
            intent.putExtra("fragment_name", "notificationdetails");
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    DashboardActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE
            );

            firebaseDatabase = FirebaseDatabase.getInstance();
            fAuth = FirebaseAuth.getInstance();
            user = fAuth.getCurrentUser();

            databaseReference = firebaseDatabase.getReference("userInfo").child(user.getUid());

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String serialnumber = snapshot.child("serialnumber").getValue(String.class);

                        databaseReference2 = firebaseDatabase.getReference("SensorData").child(serialnumber);

                        databaseReference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    Float temperature = snapshot.child("Caltemp").getValue(Float.class);
                                    if(temperature != null){
                                        Float Temp = temperature;

                                        if((Temp >= 34 && Temp <=37) && counter == 5){
                                            LocalDateTime myDateObj = LocalDateTime.now();
                                            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");

                                            String formattedDate = myDateObj.format(myFormatObj);

                                            intent.putExtra("notification", "Your dog might due today!");
                                            intent.putExtra("date", formattedDate);
                                            intent.putExtra("temp", Float.toString(Temp));

                                            if (Build.VERSION.SDK_INT >= 21){
                                                NotificationCompat.Builder builder = new NotificationCompat.Builder(DashboardActivity.this, "My Notification");
                                                builder.setContentTitle("Your dog check!");
                                                builder.setContentText("Your dog might due today! \nTemperature:" +  Float.toString(Temp));
                                                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                                builder.setSmallIcon(R.mipmap.ic_launcher);
                                                builder.setVibrate(new long[0]);
                                                builder.setAutoCancel(true);
                                                builder.setContentIntent(pendingIntent);

                                                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(DashboardActivity.this);
                                                managerCompat.notify(1,builder.build());
                                            }

                                            if(getIntent().getStringExtra("fragment_name") == "notificationdetails"){
                                                notificationdetails note = new notificationdetails();
                                                Bundle bundle = new Bundle();
                                                bundle.putString("notification", getIntent().getStringExtra("notification"));
                                                bundle.putString("date", getIntent().getStringExtra("date"));
                                                bundle.putString("temp", getIntent().getStringExtra("temp"));
                                                note.setArguments(bundle);
                                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, note).addToBackStack(null).commit();
                                            }
                                        }

                                        if(Temp >= 42 && counter == 5){
                                            LocalDateTime myDateObj = LocalDateTime.now();
                                            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");

                                            String formattedDate = myDateObj.format(myFormatObj);

                                            intent.putExtra("notification", "Your dog might due today!");
                                            intent.putExtra("date", formattedDate);
                                            intent.putExtra("temp", Float.toString(Temp));

                                            if (Build.VERSION.SDK_INT >= 21){
                                                NotificationCompat.Builder builder = new NotificationCompat.Builder(DashboardActivity.this, "My Notification");
                                                builder.setContentTitle("Your dog check!");
                                                builder.setContentText("Dog has possible fever! \nTemperature:" +  Float.toString(Temp));
                                                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                                builder.setSmallIcon(R.mipmap.ic_launcher);
                                                builder.setVibrate(new long[0]);
                                                builder.setAutoCancel(true);
                                                builder.setContentIntent(pendingIntent);

                                                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(DashboardActivity.this);
                                                managerCompat.notify(1,builder.build());
                                            }

                                            if(getIntent().getStringExtra("fragment_name") == "notificationdetails"){
                                                notificationdetails note = new notificationdetails();
                                                Bundle bundle = new Bundle();
                                                bundle.putString("notification", getIntent().getStringExtra("notification"));
                                                bundle.putString("date", getIntent().getStringExtra("date"));
                                                bundle.putString("temp", getIntent().getStringExtra("temp"));
                                                note.setArguments(bundle);
                                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, note).addToBackStack(null).commit();
                                            }
                                        }

                                        if(Temp <= 32 && counter == 5){
                                            LocalDateTime myDateObj = LocalDateTime.now();
                                            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");

                                            String formattedDate = myDateObj.format(myFormatObj);

                                            intent.putExtra("notification", "Your dog might due today!");
                                            intent.putExtra("date", formattedDate);
                                            intent.putExtra("temp", Float.toString(Temp));

                                            if (Build.VERSION.SDK_INT >= 21){
                                                NotificationCompat.Builder builder = new NotificationCompat.Builder(DashboardActivity.this, "My Notification");
                                                builder.setContentTitle("Your dog check!");
                                                builder.setContentText("Can't detect dog! \nTemperature:" +  Float.toString(Temp));
                                                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                                builder.setSmallIcon(R.mipmap.ic_launcher);
                                                builder.setVibrate(new long[0]);
                                                builder.setAutoCancel(true);
                                                builder.setContentIntent(pendingIntent);

                                                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(DashboardActivity.this);
                                                managerCompat.notify(1,builder.build());
                                            }

                                            if(getIntent().getStringExtra("fragment_name") == "notification details"){
                                                notificationdetails note = new notificationdetails();
                                                Bundle bundle = new Bundle();
                                                bundle.putString("notification", getIntent().getStringExtra("notification"));
                                                bundle.putString("date", getIntent().getStringExtra("date"));
                                                bundle.putString("temp", getIntent().getStringExtra("temp"));
                                                note.setArguments(bundle);
                                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, note).addToBackStack(null).commit();
                                            }
                                        }

                                        if(counter == 5){
                                            counter = 0;
                                        }

//                                    Toast.makeText(DashboardActivity.this, "Counter: " + Integer.toString(counter), Toast.LENGTH_SHORT).show();
                                        counter++;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(DashboardActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(DashboardActivity.this, "Intruder Alert!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(DashboardActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            });
        }

//        isUser();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // By using switch we can easily get
            // the selected fragment
            // by using there id.
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.home:
                    selectedFragment = new DashboardFragment();
                    break;
                case R.id.camera:
                    selectedFragment = new CameraView();
                    break;
                case R.id.notes:
                    selectedFragment = new NotesFragment();
                    break;
                case R.id.settings:
                    selectedFragment = new SettingsFragment();
                    break;
            }
            // It will help to replace the
            // one fragment to other.
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
            return true;
        }
    };

    private void isUser() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        databaseReference = firebaseDatabase.getReference("userInfo").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String serialnumber = snapshot.child("serialnumber").getValue(String.class);

                    databaseReference2 = firebaseDatabase.getReference("SensorData").child(serialnumber);

                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Float temperature = snapshot.child("Caltemp").getValue(Float.class);
                                if(temperature != null){
                                    Float Temp = temperature;

                                    if((Temp >= 34 && Temp <=37) && counter == 5){
                                        if (Build.VERSION.SDK_INT >= 21){
                                            NotificationCompat.Builder builder = new NotificationCompat.Builder(DashboardActivity.this, "My Notification");
                                            builder.setContentTitle("Your dog check!");
                                            builder.setContentText("Your dog might due today! \nTemperature:" +  Float.toString(Temp));
                                            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                            builder.setSmallIcon(R.mipmap.ic_launcher);
                                            builder.setVibrate(new long[0]);
                                            builder.setAutoCancel(true);

                                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(DashboardActivity.this);
                                            managerCompat.notify(1,builder.build());
                                        }
                                    }

                                    if(Temp >= 42 && counter == 5){
                                        if (Build.VERSION.SDK_INT >= 21){
                                            NotificationCompat.Builder builder = new NotificationCompat.Builder(DashboardActivity.this, "My Notification");
                                            builder.setContentTitle("Your dog check!");
                                            builder.setContentText("Your dog might due today! \nTemperature:" +  Float.toString(Temp));
                                            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                            builder.setSmallIcon(R.mipmap.ic_launcher);
                                            builder.setVibrate(new long[0]);
                                            builder.setAutoCancel(true);

                                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(DashboardActivity.this);
                                            managerCompat.notify(1,builder.build());
                                        }
                                    }

                                    if(Temp <= 32 && counter == 5){
                                        if (Build.VERSION.SDK_INT >= 21){
                                            NotificationCompat.Builder builder = new NotificationCompat.Builder(DashboardActivity.this, "My Notification");
                                            builder.setContentTitle("Your dog check!");
                                            builder.setContentText("Your dog might due today! \nTemperature:" +  Float.toString(Temp));
                                            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                            builder.setSmallIcon(R.mipmap.ic_launcher);
                                            builder.setVibrate(new long[0]);
                                            builder.setAutoCancel(true);

                                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(DashboardActivity.this);
                                            managerCompat.notify(1,builder.build());
                                        }
                                    }

                                    if(counter == 5){
                                        counter = 0;
                                    }

//                                    Toast.makeText(DashboardActivity.this, "Counter: " + Integer.toString(counter), Toast.LENGTH_SHORT).show();
                                    counter++;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(DashboardActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(DashboardActivity.this, "Intruder Alert!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}