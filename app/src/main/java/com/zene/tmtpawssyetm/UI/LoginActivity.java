package com.zene.tmtpawssyetm.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.zene.tmtpawssyetm.R;

public class LoginActivity extends AppCompatActivity {

    MaterialButton login_btn;
    MaterialButton register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = findViewById(R.id.btnLogin);
        register_btn = findViewById(R.id.btnRegister);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivityV2.class));
                finish();
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivityV2.class));
                finish();
            }
        });
    }
}