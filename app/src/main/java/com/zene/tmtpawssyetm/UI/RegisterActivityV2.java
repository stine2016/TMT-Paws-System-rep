package com.zene.tmtpawssyetm.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zene.tmtpawssyetm.Model.Note;
import com.zene.tmtpawssyetm.R;

public class RegisterActivityV2 extends AppCompatActivity {
    private EditText emailTextView, phoneTextView, passwordTextView, serialnumberTextView, nameTextView;
    private Button Btn;
    private FirebaseAuth mAuth;
    private ProgressBar progressbar;
    String userID;

    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    DatabaseReference databaseReference;

    Note.TMTPawsUserData tmtPawsUserData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_v2);
        changeStatusBarColor();

        mAuth = FirebaseAuth.getInstance();
        emailTextView = findViewById(R.id.editTextEmail);
        phoneTextView = findViewById(R.id.editTextPhone);
        passwordTextView = findViewById(R.id.editTextPassword);
        serialnumberTextView = findViewById(R.id.editTextSerial);
        nameTextView = findViewById(R.id.editTextName);
        progressbar = findViewById(R.id.progressbar);
        Btn = findViewById(R.id.RegisterButton);

        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = firebaseDatabase.getReference("userInfo");

        tmtPawsUserData = new Note.TMTPawsUserData();

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivityV2.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }

    private void registerNewUser()
    {
        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, phonenumber, password, name, serialnumber;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();
        phonenumber = phoneTextView.getText().toString();
        name = nameTextView.getText().toString();
        serialnumber = serialnumberTextView.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
        }

        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    addDatatoFirebase(email, phonenumber, password, name, serialnumber, user.getUid());
                                    Toast.makeText(RegisterActivityV2.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                }
                            });

                            Toast.makeText(getApplicationContext(),
                                    "User Created!",
                                    Toast.LENGTH_LONG)
                                    .show();
                            userID = mAuth.getCurrentUser().getUid();
                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(RegisterActivityV2.this,
                                    LoginActivityV2.class);
                            startActivity(intent);
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });

    }

    private void addDatatoFirebase(String email, String phonenumber, String password, String name, String serialnumber, String userID) {
        // below 3 lines of code is used to set
        // data in our object class.
        tmtPawsUserData.setEmail(email);
        tmtPawsUserData.setPhonenumber(phonenumber);
        tmtPawsUserData.setPassword(password);
        tmtPawsUserData.setName(name);
        tmtPawsUserData.setSerialnumber(serialnumber);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new  ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.child(userID).setValue(tmtPawsUserData);

                // after adding this data we are showing toast message.
                Toast.makeText(RegisterActivityV2.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(RegisterActivityV2.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
