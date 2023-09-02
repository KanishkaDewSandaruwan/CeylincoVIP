package com.example.ceylincovip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Vehicle_Selection extends AppCompatActivity {

    private static final String TAG = "StartApp";
    ImageButton btnCar,btnVan,btnBus,btnTruck, btnMotorCycle, btnThreeWheel, btnTractor, btnOther, info_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_selection);

        btnCar = findViewById(R.id.carImg);
        btnVan = findViewById(R.id.vanImg);
        btnBus = findViewById(R.id.dbusImg);
        btnTruck = findViewById(R.id.lorryImg);
        btnMotorCycle = findViewById(R.id.cycleImg);
        btnThreeWheel = findViewById(R.id.wheelImg);
        btnTractor = findViewById(R.id.tractImg);
        btnOther = findViewById(R.id.otherImg);

        Intent intensity = getIntent();
        int policy_type = intensity.getIntExtra("TYPE", 0);
        int dealerId = intensity.getIntExtra("DEALER", 0);
        String token = intensity.getStringExtra("TOKEN");
        String dealerFullname = intensity.getStringExtra("FULLNESS");

        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Vehicle_Selection.this, "This is the info button", Toast.LENGTH_SHORT).show();
            }
        });

        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vehicle_Selection.this, PersonalInformation.class);
                //This is the Car
                intent.putExtra("VEHICLE_TYPE", 1);
                intent.putExtra("TYPE", policy_type);
                intent.putExtra("DEALER", dealerId);
                intent.putExtra("TOKEN", token);
                intent.putExtra("FULLNESS", dealerFullname);
                startActivity(intent);
            }
        });

        btnVan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vehicle_Selection.this, PersonalInformation.class);
                //This is the Van
                intent.putExtra("VEHICLE_TYPE", 2);
                intent.putExtra("TYPE", policy_type);
                intent.putExtra("DEALER", dealerId);
                intent.putExtra("TOKEN", token);
                intent.putExtra("FULLNESS", dealerFullname);
                startActivity(intent);
            }
        });

        btnBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vehicle_Selection.this, PersonalInformation.class);
                //This is the Bus
                intent.putExtra("VEHICLE_TYPE", 3);
                intent.putExtra("TYPE", policy_type);
                intent.putExtra("DEALER", dealerId);
                intent.putExtra("TOKEN", token);
                intent.putExtra("FULLNESS", dealerFullname);
                startActivity(intent);
            }
        });

        btnTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vehicle_Selection.this, PersonalInformation.class);
                //This is the Lorry
                intent.putExtra("VEHICLE_TYPE", 4);
                intent.putExtra("TYPE", policy_type);
                intent.putExtra("DEALER", dealerId);
                intent.putExtra("TOKEN", token);
                intent.putExtra("FULLNESS", dealerFullname);
                startActivity(intent);
            }
        });

        btnMotorCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vehicle_Selection.this, PersonalInformation.class);
                //This is the MotorCycle
                intent.putExtra("VEHICLE_TYPE", 5);
                intent.putExtra("TYPE", policy_type);
                intent.putExtra("DEALER", dealerId);
                intent.putExtra("TOKEN", token);
                intent.putExtra("FULLNESS", dealerFullname);
                startActivity(intent);
            }
        });

        btnThreeWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vehicle_Selection.this, PersonalInformation.class);
                //This is the Three Wheel
                intent.putExtra("VEHICLE_TYPE", 6);
                intent.putExtra("TYPE", policy_type);
                intent.putExtra("DEALER", dealerId);
                intent.putExtra("TOKEN", token);
                intent.putExtra("FULLNESS", dealerFullname);
                startActivity(intent);
            }
        });

        btnTractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vehicle_Selection.this, PersonalInformation.class);
                //This is the Tractor
                intent.putExtra("VEHICLE_TYPE", 7);
                intent.putExtra("TYPE", policy_type);
                intent.putExtra("DEALER", dealerId);
                intent.putExtra("TOKEN", token);
                intent.putExtra("FULLNESS", dealerFullname);
                startActivity(intent);
            }
        });

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vehicle_Selection.this, PersonalInformation.class);
                //This is the Other Vehicle
                intent.putExtra("VEHICLE_TYPE", 8);
                intent.putExtra("TYPE", policy_type);
                intent.putExtra("DEALER", dealerId);
                intent.putExtra("TOKEN", token);
                intent.putExtra("FULLNESS", dealerFullname);
                startActivity(intent);
            }
        });

    }
}