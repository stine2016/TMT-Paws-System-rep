package com.zene.tmtpawssyetm.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.zene.tmtpawssyetm.R;

public class ForgotPassword extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {
    CheckBox checkBox;
    GoogleApiClient googleApiClient;
    String Sitekey = "6LdFyzggAAAAADs4dCGkPXdUbZ72-o4bq3DIvmg5";
    EditText email;
    MaterialButton materialButton;
    String emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        checkBox = findViewById(R.id.recaptcha);
        email = findViewById(R.id.editTextName);
        materialButton = findViewById(R.id.ForgotButton);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(ForgotPassword.this)
                .build();
        googleApiClient.connect();
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkBox.isChecked()){
                    SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleApiClient,Sitekey)
                            .setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                                @Override
                                public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {
                                    Status status = recaptchaTokenResult.getStatus();
                                    if((status!=null) && status.isSuccess()){
                                        checkBox.setTextColor(Color.GREEN);

                                        materialButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                emails = email.getText().toString();

                                                if(emails.isEmpty()){
                                                    Toast.makeText(ForgotPassword.this, "Please provide your email", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    forgotpassword();
                                                }
                                            }
                                        });
                                    }
                                    if((status!=null) && status.isCanceled()){
                                        checkBox.setChecked(false);
                                    }
                                }
                            });
                }
                else{
                    checkBox.setTextColor(Color.BLACK);
                }
            }
        });

    }

    private void forgotpassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(emails)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPassword.this, "Check your email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgotPassword.this, LoginActivityV2.class));
                            finish();
                        }
                        else{
                            Toast.makeText(ForgotPassword.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}