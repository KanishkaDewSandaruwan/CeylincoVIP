package com.example.ceylincovip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AboutUS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

    }

    @Override
    public void onBackPressed() {

        Intent intent1 = new Intent(AboutUS.this, DealerLogin.class);
        startActivity(intent1);
    }
}