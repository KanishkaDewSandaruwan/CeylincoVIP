package com.example.ceylincovip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VehicleAdditionalInformation extends AppCompatActivity {

    EditText addVehicleColor, addHorsePower, addValueOfvehicl, leasingCompany;
    private static final String TAG = "StartApp";
    Button next, back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_additional_information);

        addVehicleColor = findViewById(R.id.addVehicleColor);
        addHorsePower = findViewById(R.id.addHorsePower);
        addValueOfvehicl = findViewById(R.id.addValueOfvehicl);
        leasingCompany = findViewById(R.id.addLeasingCompany);

        next = findViewById(R.id.btnNextSetp03);
        back = findViewById(R.id.btnPreviousSetp01);

        // Retrieve intent extras
        Intent intent = getIntent();
        String fullName = intent.getStringExtra("FULL_NAME");
        String address = intent.getStringExtra("ADDRESS");
        String idNumber = intent.getStringExtra("NIC");
        String contactNumber = intent.getStringExtra("CONTACT");
        String customerEmail = intent.getStringExtra("EMAIL");
        int type = intent.getIntExtra("TYPE", 0);
        int vehicleType = intent.getIntExtra("VEHICLE_TYPE", 0);
        String engineNumber = intent.getStringExtra("ENGINE");
        String chassisNumber = intent.getStringExtra("CHASSIS");
        String modal = intent.getStringExtra("MODAL");
        String yearOfMake = intent.getStringExtra("YEAR");
        String regNo = intent.getStringExtra("RENO");

        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        // Set the retrieved values to the respective fields
        addVehicleColor.setText(intent.getStringExtra("COLOR"));
        addHorsePower.setText(intent.getStringExtra("HORSE_POWER"));
        addValueOfvehicl.setText(intent.getStringExtra("VALUE"));
        leasingCompany.setText(intent.getStringExtra("LEASING_COMPANY"));

        Log.e(TAG, "ENGINE " + engineNumber);
        Log.e(TAG, "CHASSIS " + chassisNumber);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent and pass the previous values
                Intent intent = new Intent(VehicleAdditionalInformation.this, VehicleInformation.class);
                intent.putExtra("FULL_NAME", fullName);
                intent.putExtra("ADDRESS", address);
                intent.putExtra("NIC", idNumber);
                intent.putExtra("CONTACT", contactNumber);
                intent.putExtra("EMAIL", customerEmail);
                intent.putExtra("TYPE", type);
                intent.putExtra("VEHICLE_TYPE", vehicleType);
                intent.putExtra("ENGINE", engineNumber);
                intent.putExtra("CHASSIS", chassisNumber);
                intent.putExtra("MODAL", modal);
                intent.putExtra("YEAR", yearOfMake);
                intent.putExtra("RENO", regNo);
                intent.putExtra("COLOR", addVehicleColor.getText().toString());
                intent.putExtra("HORSE_POWER", addHorsePower.getText().toString());
                intent.putExtra("VALUE", addValueOfvehicl.getText().toString());
                intent.putExtra("LEASING_COMPANY", leasingCompany.getText().toString());

                intent.putExtra("DEALER", dealerId);
                intent.putExtra("TOKEN", token);
                intent.putExtra("FULLNESS", dealerFullname);
                startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    Intent intent = new Intent(VehicleAdditionalInformation.this, InsurenceInformation.class);
                    intent.putExtra("FULL_NAME", fullName);
                    intent.putExtra("ADDRESS", address);
                    intent.putExtra("NIC", idNumber);
                    intent.putExtra("CONTACT", contactNumber);
                    intent.putExtra("EMAIL", customerEmail);
                    intent.putExtra("TYPE", type);
                    intent.putExtra("VEHICLE_TYPE", vehicleType);
                    intent.putExtra("ENGINE", engineNumber);
                    intent.putExtra("CHASSIS", chassisNumber);
                    intent.putExtra("MODAL", modal);
                    intent.putExtra("YEAR", yearOfMake);
                    intent.putExtra("RENO", regNo);
                    intent.putExtra("COLOR", addVehicleColor.getText().toString());
                    intent.putExtra("HORSE_POWER", addHorsePower.getText().toString());
                    intent.putExtra("VALUE", addValueOfvehicl.getText().toString());
                    intent.putExtra("LEASING_COMPANY", leasingCompany.getText().toString());

                    intent.putExtra("DEALER", dealerId);
                    intent.putExtra("TOKEN", token);
                    intent.putExtra("FULLNESS", dealerFullname);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateInput() {
        String color = addVehicleColor.getText().toString().trim();
        String horsePower = addHorsePower.getText().toString().trim();
        String valueOfVehicle = addValueOfvehicl.getText().toString().trim();
        String leasingCompanyValue = leasingCompany.getText().toString().trim();

        if (color.isEmpty()) {
            addVehicleColor.setError("Please enter vehicle color");
            addVehicleColor.requestFocus();
            return false;
        }

        if (horsePower.isEmpty()) {
            addHorsePower.setError("Please enter vehicle horse power");
            addHorsePower.requestFocus();
            return false;
        }

        if (valueOfVehicle.isEmpty()) {
            addValueOfvehicl.setError("Please enter value of vehicle");
            addValueOfvehicl.requestFocus();
            return false;
        }

        if (!isNumeric(valueOfVehicle)) {
            addValueOfvehicl.setError("Please enter a valid numeric value for value of vehicle");
            addValueOfvehicl.requestFocus();
            return false;
        }


        return true; // All input fields are valid
    }

    private boolean isNumeric(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
