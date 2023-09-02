package com.example.ceylincovip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConfirmDealreRegistration extends AppCompatActivity {

    TextView email;
    Button backtologin;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_dealre_registration);

        email = findViewById(R.id.textConfirmEmail2);
        backtologin = findViewById(R.id.btnGoToLogin);

        Intent intent = getIntent();
        String getemail = intent.getStringExtra("EMAIL");

        email.setText(getemail);

        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfirmDealreRegistration.this, DealerLogin.class));
            }
        });
    }
}