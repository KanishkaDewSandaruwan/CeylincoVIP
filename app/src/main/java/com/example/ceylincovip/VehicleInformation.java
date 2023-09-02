package com.example.ceylincovip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

public class VehicleInformation extends AppCompatActivity {

    EditText EnginNo, ChasisNo, addmodal, addYeardOfMake, VehicleRegNo;
    private static final String TAG = "StartApp";

    Spinner yearSpinner;
    Button next, back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_information);

        VehicleRegNo = findViewById(R.id.addVehicleRegNo);
        EnginNo = findViewById(R.id.addEnginNo);
        ChasisNo = findViewById(R.id.addChasisNo);
        addmodal = findViewById(R.id.addmodal);

        next = findViewById(R.id.btnNextSetp03);
        back = findViewById(R.id.btnPreviousSetp01);
        yearSpinner = findViewById(R.id.yearSpinner);

        Intent getIntent = getIntent();
        String fullName = getIntent.getStringExtra("FULL_NAME");
        String address = getIntent.getStringExtra("ADDRESS");
        String idNumber = getIntent.getStringExtra("NIC");
        String contactNumber = getIntent.getStringExtra("CONTACT");
        String customerEmail = getIntent.getStringExtra("EMAIL");
        int type = getIntent.getIntExtra("TYPE", 0);
        int vehicleType = getIntent.getIntExtra("VEHICLE_TYPE", 0);
        int dealerId = getIntent.getIntExtra("DEALER", 0);
        String token = getIntent.getStringExtra("TOKEN");
        String dealerFullname = getIntent.getStringExtra("FULLNESS");

        // Set the retrieved values to the respective fields
        EnginNo.setText(getIntent.getStringExtra("ENGINE"));
        ChasisNo.setText(getIntent.getStringExtra("CHASSIS"));
        addmodal.setText(getIntent.getStringExtra("MODAL"));
        VehicleRegNo.setText(getIntent.getStringExtra("RENO"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VehicleInformation.this, PersonalInformation.class);
                intent.putExtra("FULL_NAME", fullName);
                intent.putExtra("ADDRESS", address);
                intent.putExtra("NIC", idNumber);
                intent.putExtra("CONTACT", contactNumber);
                intent.putExtra("EMAIL", customerEmail);
                intent.putExtra("TYPE", type);
                intent.putExtra("VEHICLE_TYPE", vehicleType);

                intent.putExtra("ENGINE", EnginNo.getText().toString());
                intent.putExtra("CHASSIS", ChasisNo.getText().toString());
                intent.putExtra("MODAL", addmodal.getText().toString());
                intent.putExtra("YEAR", getSelectedYear());
                intent.putExtra("RENO", VehicleRegNo.getText().toString());

                intent.putExtra("COLOR", getIntent.getStringExtra("COLOR"));
                intent.putExtra("HORSE_POWER", getIntent.getStringExtra("HORSE_POWER"));
                intent.putExtra("VALUE", getIntent.getStringExtra("VALUE"));
                intent.putExtra("LEASING_COMPANY", getIntent.getStringExtra("LEASING_COMPANY"));
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
                    Intent intent = new Intent(VehicleInformation.this, VehicleAdditionalInformation.class);
                    intent.putExtra("FULL_NAME", fullName);
                    intent.putExtra("ADDRESS", address);
                    intent.putExtra("NIC", idNumber);
                    intent.putExtra("CONTACT", contactNumber);
                    intent.putExtra("EMAIL", customerEmail);
                    intent.putExtra("TYPE", type);
                    intent.putExtra("VEHICLE_TYPE", vehicleType);

                    intent.putExtra("ENGINE", EnginNo.getText().toString());
                    intent.putExtra("CHASSIS", ChasisNo.getText().toString());
                    intent.putExtra("MODAL", addmodal.getText().toString());
                    intent.putExtra("YEAR", getSelectedYear());
                    intent.putExtra("RENO", VehicleRegNo.getText().toString());

                    intent.putExtra("COLOR", getIntent.getStringExtra("COLOR"));
                    intent.putExtra("HORSE_POWER", getIntent.getStringExtra("HORSE_POWER"));
                    intent.putExtra("VALUE", getIntent.getStringExtra("VALUE"));
                    intent.putExtra("LEASING_COMPANY", getIntent.getStringExtra("LEASING_COMPANY"));
                    intent.putExtra("DEALER", dealerId);
                    intent.putExtra("TOKEN", token);
                    intent.putExtra("FULLNESS", dealerFullname);
                    startActivity(intent);
                }
            }
        });

        // Set up year spinner
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getPastYears());
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
    }

    private boolean validateInput() {
        String engineNumber = EnginNo.getText().toString().trim();
        String chassisNumber = ChasisNo.getText().toString().trim();
        String modal = addmodal.getText().toString().trim();
        String yearOfMake = getSelectedYear();
        String regNo = VehicleRegNo.getText().toString().trim();

        if (regNo.isEmpty()) {
            VehicleRegNo.setError("Please enter the vehicle registration number");
            VehicleRegNo.requestFocus();
            return false;
        }

        if (engineNumber.isEmpty()) {
            EnginNo.setError("Please enter the engine number");
            EnginNo.requestFocus();
            return false;
        }

        if (chassisNumber.isEmpty()) {
            ChasisNo.setError("Please enter the chassis number");
            ChasisNo.requestFocus();
            return false;
        }

        if (modal.isEmpty()) {
            addmodal.setError("Please enter the vehicle modal");
            addmodal.requestFocus();
            return false;
        }

        return true; // All input fields are valid
    }

    private ArrayList<String> getPastYears() {
        ArrayList<String> years = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= currentYear - 100; i--) {
            years.add(String.valueOf(i));
        }
        return years;
    }

    private String getSelectedYear() {
        return yearSpinner.getSelectedItem().toString();
    }
}
