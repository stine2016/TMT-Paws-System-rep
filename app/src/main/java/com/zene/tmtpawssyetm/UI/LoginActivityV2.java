package com.zene.tmtpawssyetm.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zene.tmtpawssyetm.R;

public class LoginActivityV2 extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private Button Btn;
    private ProgressBar progressbar;
    private CheckBox rememberMe;
    TextView forgotPass;
    String userId;
    FirebaseUser user;
    boolean passwordVisible;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login_v2);

        mAuth = FirebaseAuth.getInstance();

        emailTextView = findViewById(R.id.editTextEmail);
        passwordTextView = findViewById(R.id.editTextPassword);
        progressbar = findViewById(R.id.progressbar);
        Btn = findViewById(R.id.LoginButton);
        rememberMe = findViewById(R.id.rememberMe);
        forgotPass = findViewById(R.id.forgotPassword);


        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkBox = preferences.getString("remember","");
        if(checkBox.equals("true")){
            Intent intent = new Intent(LoginActivityV2.this, DashboardActivity.class);
            startActivity(intent);
        }else if(checkBox.equals("false")){
            Toast.makeText(this, "Please Sign In", Toast.LENGTH_SHORT).show();
        }

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
            }
        });

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(LoginActivityV2.this, "Checked", Toast.LENGTH_SHORT).show();
                }else if (!compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(LoginActivityV2.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(LoginActivityV2.this,
                        ForgotPassword.class);
                startActivity(intent);
            }
        });

        passwordTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=passwordTextView.getRight()-passwordTextView.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = passwordTextView.getSelectionEnd();
                        if(passwordVisible){
                            passwordTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            passwordTextView.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        }else{
                            passwordTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                            passwordTextView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        passwordTextView.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });
    }

    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivityV2.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }

    private void loginUserAccount()
    {

        // show the visibility of progress bar to show loading

        // Take the value of two edit texts in Strings
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    userId = mAuth.getCurrentUser().getUid();
                                    user = mAuth.getCurrentUser();

                                    if(!user.isEmailVerified()){
                                        Toast.makeText(getApplicationContext(),
                                                "Please verify your email!!",
                                                Toast.LENGTH_LONG)
                                                .show();
                                    }

                                    if(user.isEmailVerified()){
                                        Toast.makeText(getApplicationContext(),
                                                "Login successful!!",
                                                Toast.LENGTH_LONG)
                                                .show();

                                        // hide the progress bar

                                        // if sign-in is successful
                                        // intent to home activity
                                        Intent intent
                                                = new Intent(LoginActivityV2.this,
                                                DashboardActivity.class);
                                        startActivity(intent);
                                    }
                                }

                                else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                            "Login failed!!",
                                            Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                }
                            }
                        });
    }
}