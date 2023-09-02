package com.example.ceylincovip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ceylincovip.Connection.RetrofitClient;
import com.example.ceylincovip.DealerRegisterPersonalInformation;
import com.example.ceylincovip.Interface.Methods;
import com.example.ceylincovip.Modal.ApiResponse;
import com.example.ceylincovip.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerRegister extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextContactNumber;

    private Button dealerRegisterToPersonal;
    private Button dealerRegister03;
    private static final String TAG = "DealerRegister";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_register);

        dealerRegisterToPersonal = findViewById(R.id.btnDealerRegister_topersonal);
        dealerRegister03 = findViewById(com.example.ceylincovip.R.id.btnDealerRegister_03);

        editTextEmail = findViewById(R.id.addDealerEmail);
        editTextPassword = findViewById(R.id.adddealerPassword);
        editTextConfirmPassword = findViewById(R.id.addDealerconfirmPassword);
        editTextContactNumber = findViewById(R.id.adDealerContactNumber);

        dealerRegisterToPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DealerRegister.this, DealerRegisterPersonalInformation.class));
            }
        });

        dealerRegister03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();
                String contactNumber = editTextContactNumber.getText().toString().trim();

                if (validateInputFields(email, password, confirmPassword, contactNumber)) {
                    validate("dealer_email", email, new ValidationCallback() {
                        @Override
                        public void onValidationSuccess(int emailCount) {
                            if (emailCount == 0) {
                                validate("dealer_phone", contactNumber, new ValidationCallback() {
                                    @Override
                                    public void onValidationSuccess(int phoneCount) {
                                        if (phoneCount == 0) {
                                            proceedToCompanyDetails(email, password, contactNumber);
                                        } else {
                                            showErrorDialog("Phone number already exists!");
                                        }
                                    }

                                    @Override
                                    public void onValidationFailure() {
                                        showErrorDialog("Validation failed for phone number.");
                                    }
                                });
                            } else {
                                showErrorDialog("Email already exists!");
                            }
                        }

                        @Override
                        public void onValidationFailure() {
                            showErrorDialog("Validation failed for email.");
                        }
                    });
                }
            }
        });
    }

    private boolean validateInputFields(String email, String password, String confirmPassword, String contactNumber) {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || contactNumber.isEmpty()) {
            showErrorDialog("Please fill all the fields.");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showErrorDialog("Passwords do not match.");
            return false;
        }

        if (!isValidEmail(email)) {
            showErrorDialog("Email is not valid");
            return false;
        }

        if (!isPasswordValid(password)) {
            showErrorDialog("Password must contain at least 8 characters, one uppercase letter, one lowercase letter, and one digit or special character.");
            return false;
        }

        return true;
    }

    private void showErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void proceedToCompanyDetails(String email, String password, String contactNumber) {
        Intent intent = getIntent();
        String fullName = intent.getStringExtra("fullName");
        String address = intent.getStringExtra("address");
        String nic = intent.getStringExtra("nic");
        String whatsApp = intent.getStringExtra("whatsApp");

        Intent companyIntent = new Intent(DealerRegister.this, CompanyDetails.class);
        companyIntent.putExtra("email", email);
        companyIntent.putExtra("password", password);
        companyIntent.putExtra("contactNumber", contactNumber);
        companyIntent.putExtra("fullName", fullName);
        companyIntent.putExtra("address", address);
        companyIntent.putExtra("nic", nic);
        companyIntent.putExtra("whatsApp", whatsApp);
        startActivity(companyIntent);
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
    private boolean isValidEmail(String email) {
        // Use a regular expression to validate email format
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }


    private boolean isPasswordValid(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d|[^\\da-zA-Z]).{8,}$";
        return password.matches(regex);
    }

    interface ValidationCallback {
        void onValidationSuccess(int count);
        void onValidationFailure();
    }
}
