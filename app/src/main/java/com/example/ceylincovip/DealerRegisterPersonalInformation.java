package com.example.ceylincovip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ceylincovip.Connection.RetrofitClient;
import com.example.ceylincovip.Interface.Methods;
import com.example.ceylincovip.Modal.ApiResponse;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerRegisterPersonalInformation extends AppCompatActivity {

    EditText fullName, nic, address, whatsApp;
    private static final String TAG = "StartApp";
    Button back, next;

    private static final String PHONE_NUMBER_REGEX = "\\d{10}";
    private static final String NIC_REGEX = "{12}";
    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_register_personal_information);

        fullName = findViewById(R.id.adDealerfullname);
        address = findViewById(R.id.addDealerAddress);
        nic = findViewById(R.id.addDealernic);
        whatsApp = findViewById(R.id.addDealerwhatsapp);

        back = findViewById(R.id.btnDealerRegisterPersonal_toLogin);
        next = findViewById(R.id.btnDealerRegister_next);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DealerRegisterPersonalInformation.this, DealerLogin.class));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getfullName = fullName.getText().toString();
                String getaddress = address.getText().toString();
                String getnic = nic.getText().toString();
                String getwhatsApp = whatsApp.getText().toString();

                String dealer_phone = "dealer_phone";
                String dealer_email = "dealer_email";

                if (getfullName.isEmpty()) {
                    fullName.setError("Full Name is empty!");
                } else if (getaddress.isEmpty()) {
                    address.setError("Address is empty!");
                } else if (getnic.isEmpty()) {
                    nic.setError("NIC is empty!");
                } else if (getwhatsApp.isEmpty()) {
                    whatsApp.setError("Phone Number is empty!");
                } else if (!isValidPhoneNumber(getwhatsApp)) {
                    whatsApp.setError("Invalid Phone Number!");
                } else {
                    validate(dealer_phone, getwhatsApp, new ValidationCallback() {
                        @Override
                        public void onValidationSuccess(int count) {
                            if (count == 0) {
                                // Create an intent to start the next activity
                                Intent intent = new Intent(DealerRegisterPersonalInformation.this, DealerRegister.class);
                                // Put the data as extras in the intent
                                intent.putExtra("fullName", getfullName);
                                intent.putExtra("address", getaddress);
                                intent.putExtra("nic", getnic);
                                intent.putExtra("whatsApp", getwhatsApp);
                                // Start the next activity with the intent
                                startActivity(intent);
                            } else {
                                whatsApp.setError("Phone Number already exists!");
                            }
                        }

                        @Override
                        public void onValidationFailure() {
                            // Your logic for handling validation failure
                        }
                    });
                }
            }
        });
    }

    private void validate(String field, String value, final ValidationCallback callback) {
        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ApiResponse> validate = api.checkDealer(field, value);
        validate.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null) {
                        int count = apiResponse.getCount();
                        Log.e(TAG, "count: " + count);
                        callback.onValidationSuccess(count);
                    }
                } else {
                    Log.e(TAG, "Failed API call: " + response.code());
                    callback.onValidationFailure();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
                callback.onValidationFailure();
            }
        });
    }

    private boolean isValidInput(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return isValidInput(phoneNumber, PHONE_NUMBER_REGEX);
    }

    private boolean isValidNIC(String nic) {
        return isValidInput(nic, NIC_REGEX);
    }

    private boolean isValidEmail(String email) {
        return isValidInput(email, EMAIL_REGEX);
    }

    // Define a callback interface
    interface ValidationCallback {
        void onValidationSuccess(int count);
        void onValidationFailure();
    }
}
