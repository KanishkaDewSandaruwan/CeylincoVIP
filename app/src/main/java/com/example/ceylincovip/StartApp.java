package com.example.ceylincovip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartApp extends AppCompatActivity {

    Button f_ins;
    Button t_party;
    Button dealer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startapp);

        f_ins = (Button) findViewById(R.id.full_in);
        t_party = (Button) findViewById(R.id.third_p);
        dealer = (Button) findViewById(R.id.buttondealer);

        f_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Vehicle_Selection.class));
            }
        });

        t_party.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Vehicle_Selection.class));
            }
        });
    }
}