package com.example.ceylincovip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PersonalInformation extends AppCompatActivity {

    EditText fullName, Address, idNumber, contactNumber, customerEmail;
    private static final String TAG = "StartApp";
    Button next, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        fullName = findViewById(R.id.addFullName);
        Address = findViewById(R.id.addAddress);
        idNumber = findViewById(R.id.addIDNumber);
        contactNumber = findViewById(R.id.addContactNumber);
        customerEmail = findViewById(R.id.addCustomerEmail);

        next = findViewById(R.id.btnNextSetp02);
        back = findViewById(R.id.btnbackToVehicleSelection);

        Intent getIntent = getIntent();
        int type = getIntent.getIntExtra("TYPE", 0);
        int vehicleType = getIntent.getIntExtra("VEHICLE_TYPE", 0);
        int dealerId = getIntent.getIntExtra("DEALER", 0);
        String token = getIntent.getStringExtra("TOKEN");
        String dealerFullname = getIntent.getStringExtra("FULLNESS");

        // Set the values from the Intent to the text boxes
        fullName.setText(getIntent.getStringExtra("FULL_NAME"));
        Address.setText(getIntent.getStringExtra("ADDRESS"));
        idNumber.setText(getIntent.getStringExtra("NIC"));
        contactNumber.setText(getIntent.getStringExtra("CONTACT"));
        customerEmail.setText(getIntent.getStringExtra("EMAIL"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInformation.this, StartApp.class);
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
                    Intent intent = new Intent(PersonalInformation.this, VehicleInformation.class);
                    intent.putExtra("FULL_NAME", fullName.getText().toString());
                    intent.putExtra("ADDRESS", Address.getText().toString());
                    intent.putExtra("NIC", idNumber.getText().toString());
                    intent.putExtra("CONTACT", contactNumber.getText().toString());
                    intent.putExtra("EMAIL", customerEmail.getText().toString());
                    intent.putExtra("TYPE", type);
                    intent.putExtra("VEHICLE_TYPE", vehicleType);

                    intent.putExtra("ENGINE", getIntent.getStringExtra("ENGINE"));
                    intent.putExtra("CHASSIS", getIntent.getStringExtra("CHASSIS"));
                    intent.putExtra("MODAL", getIntent.getStringExtra("MODAL"));
                    intent.putExtra("YEAR", getIntent.getStringExtra("YEAR"));
                    intent.putExtra("RENO", getIntent.getStringExtra("RENO"));

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
    }

    private boolean validateInput() {
        String fullNameInput = fullName.getText().toString().trim();
        String addressInput = Address.getText().toString().trim();
        String idNumberInput = idNumber.getText().toString().trim();
        String contactNumberInput = contactNumber.getText().toString().trim();
        String emailInput = customerEmail.getText().toString().trim();

        if (fullNameInput.isEmpty()) {
            fullName.setError("Please enter your full name");
            fullName.requestFocus();
            return false;
        }

        if (addressInput.isEmpty()) {
            Address.setError("Please enter your address");
            Address.requestFocus();
            return false;
        }

        if (idNumberInput.isEmpty()) {
            idNumber.setError("Please enter your ID number");
            idNumber.requestFocus();
            return false;
        }

        if (idNumberInput.length() < 9 && idNumberInput.length() > 13) {
            idNumber.setError("Please enter a valid NIC number");
            idNumber.requestFocus();
            return false;
        }

        if (contactNumberInput.isEmpty()) {
            contactNumber.setError("Please enter your contact number");
            contactNumber.requestFocus();
            return false;
        }

        if (!isValidPhoneNumber(contactNumberInput)) {
            contactNumber.setError("Please enter a valid phone number");
            contactNumber.requestFocus();
            return false;
        }

        if (emailInput.isEmpty()) {
            customerEmail.setError("Please enter your email address");
            customerEmail.requestFocus();
            return false;
        }

        if (!isValidEmail(emailInput)) {
            customerEmail.setError("Please enter a valid email address");
            customerEmail.requestFocus();
            return false;
        }

        return true; // All input fields are valid
    }

    private boolean isValidEmail(String email) {
        // Use a regular expression to validate email format
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Use a regular expression to validate phone number format
        // This example assumes a 10-digit phone number without any formatting characters
        String phonePattern = "\\d{10}";
        return phoneNumber.matches(phonePattern);
    }


    @Override
    public void onBackPressed() {

        Intent intent1 = getIntent();
        int dealerId = intent1.getIntExtra("DEALER", 0);
        String token = intent1.getStringExtra("TOKEN");
        String dealerFullname = intent1.getStringExtra("FULLNESS");

        Intent intent = new Intent(PersonalInformation.this, StartApp.class);
        intent.putExtra("DEALER", dealerId);
        intent.putExtra("TOKEN", token);
        intent.putExtra("FULLNESS", dealerFullname);
        startActivity(intent);
    }
}