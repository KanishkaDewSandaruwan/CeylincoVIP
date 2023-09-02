package com.example.ceylincovip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ceylincovip.ConfirmDealreRegistration;
import com.example.ceylincovip.Connection.RetrofitClient;
import com.example.ceylincovip.DealerRegister;
import com.example.ceylincovip.Interface.Methods;
import com.example.ceylincovip.Modal.ApiResponse;
import com.example.ceylincovip.Modal.Company;
import com.example.ceylincovip.Modal.Dealer;
import com.example.ceylincovip.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyDetails extends AppCompatActivity {

    private EditText companyNameEditText;
    private EditText companyAddressEditText;
    private EditText addcompanybranch;
    private EditText companyEmailEditText;
    private EditText companyPhoneNumberEditText;
    private Button backButton;
    private Button nextButton;

    private static final String TAG = "StartApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        companyNameEditText = findViewById(R.id.addcompanyname);
        companyAddressEditText = findViewById(R.id.addcompanyAddress);
        addcompanybranch = findViewById(R.id.addcompanybranch);
        companyEmailEditText = findViewById(R.id.addcompanyemailaddress);
        companyPhoneNumberEditText = findViewById(R.id.addcompanynumber);
        backButton = findViewById(com.example.ceylincovip.R.id.btnbacktodealerreg);
        nextButton = findViewById(R.id.btnsubmitdealerreg);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CompanyDetails.this, DealerRegister.class));
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String companyName = companyNameEditText.getText().toString().trim();
                String companyAddress = companyAddressEditText.getText().toString().trim();
                String companyEmail = companyEmailEditText.getText().toString().trim();
                String companyPhoneNumber = companyPhoneNumberEditText.getText().toString().trim();
                String companyBranch = addcompanybranch.getText().toString().trim();

                Intent intent = getIntent();
                String fullName = intent.getStringExtra("fullName");
                String address = intent.getStringExtra("address");
                String nic = intent.getStringExtra("nic");
                String whatsApp = intent.getStringExtra("whatsApp");
                String email = intent.getStringExtra("email");
                String password = intent.getStringExtra("password");
                String contactNumber = intent.getStringExtra("contactNumber");

                if (validateInputFields(companyName, companyAddress, companyEmail, companyPhoneNumber)) {
                    addCompanyAndDealer(companyName, companyBranch, companyAddress, companyPhoneNumber, companyEmail, fullName, address, nic, contactNumber, whatsApp, email, password);
                }
            }
        });
    }

    private boolean validateInputFields(String companyName, String companyAddress, String companyEmail, String companyPhoneNumber) {
        if (companyName.isEmpty()) {
            companyNameEditText.setError("Company name cannot be empty.");
            return false;
        }

        if (companyAddress.isEmpty()) {
            companyAddressEditText.setError("Company address cannot be empty.");
            return false;
        }

        if (companyEmail.isEmpty()) {
            companyEmailEditText.setError("Company email address cannot be empty.");
            return false;
        }

        if (!isValidEmail(companyEmail)) {
            companyEmailEditText.setError("Please enter a valid email address.");
            return false;
        }

        if (companyPhoneNumber.isEmpty()) {
            companyPhoneNumberEditText.setError("Company phone number cannot be empty.");
            return false;
        }

        if (!isValidPhoneNumber(companyPhoneNumber)) {
            companyPhoneNumberEditText.setError("Please enter a valid phone number.");
            return false;
        }

        return true;
    }

    private void showSuccessDialog(final String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Success")
                .setMessage("Company and dealer added successfully.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startNextActivity(email);
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Automatically dismiss the dialog after a few seconds
        final Handler handler = new Handler();
        final int delayMillis = 1000; // Change this to the desired delay in milliseconds
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                    startNextActivity(email);
                }
            }
        }, delayMillis);
    }

    private void startNextActivity(String email) {
        Intent intent = new Intent(CompanyDetails.this, ConfirmDealreRegistration.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }

    private void showErrorDialog(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage(errorMessage)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void addCompanyAndDealer(String companyName, String companyBranch, String companyAddress, String companyPhoneNumber, String companyEmail, String fullName, String address, String nic, String contactNumber, String whatsApp, String email, String password) {
        addCompany(companyName, companyBranch, companyAddress, companyPhoneNumber, companyEmail, new CompanyAdditionCallback() {
            @Override
            public void onCompanyAdded(int companyId) {
                if (companyId != -1) {
                    addDealer(fullName, address, nic, contactNumber, whatsApp, email, password, companyId, new DealerAdditionCallback() {
                        @Override
                        public void onDealerAdded(int dealerId) {
                            if (dealerId != -1) {
                                showSuccessDialog(email);
                            } else {
                                showErrorDialog("Failed to add dealer");
                            }
                        }
                    });
                } else {
                    showErrorDialog("Failed to add company");
                }
            }
        });
    }

    public void addCompany(String companyName, String companyBranch, String companyAddress, String companyPhone, String companyEmail, CompanyAdditionCallback callback) {

        Company company = new Company(companyName, companyBranch, companyAddress, companyPhone, companyEmail);

        // Make the API call to add the company using the company object
        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ApiResponse> call = api.createCompany(company);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();

                    boolean success = apiResponse.isSuccess();
                    int companyId = apiResponse.getCompany_id();

                    // Invoke the callback with the companyId
                    callback.onCompanyAdded(companyId);
                } else {
                    Log.e(TAG, "Failed to parse response JSON");
                    // Invoke the callback with a default value in case of failure
                    callback.onCompanyAdded(-1);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d(TAG, "Company addition failed");
                // Invoke the callback with a default value in case of failure
                callback.onCompanyAdded(-1);
            }
        });
    }

    public void addDealer(String fullName, String address, String nic, String phone, String whatsApp, String email, String password, int companyId, DealerAdditionCallback callback) {

        Dealer dealer = new Dealer(fullName, address, nic, phone, whatsApp, email, password, companyId);

        // Make the API call to add the dealer using the dealer object
        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ApiResponse> call = api.createDealer(dealer);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();

                    boolean success = apiResponse.isSuccess();
                    int dealerId = apiResponse.getDealer_id();

                    if (success) {
                        // Show a toast message with the dealer ID
                        Toast.makeText(CompanyDetails.this, "Dealer added successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Show a toast message indicating failure
                        Toast.makeText(CompanyDetails.this, "Failed to add dealer", Toast.LENGTH_SHORT).show();
                    }

                    // Invoke the callback with the dealerId
                    callback.onDealerAdded(dealerId);
                } else {
                    Log.e(TAG, "Failed to parse response JSON");
                    // Invoke the callback with a default value in case of failure
                    callback.onDealerAdded(-1);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d(TAG, "Dealer addition failed");
                // Invoke the callback with a default value in case of failure
                callback.onDealerAdded(-1);
            }
        });
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Use a regular expression to validate phone number format
        // This example assumes a 10-digit phone number without any formatting characters
        String phonePattern = "\\d{10}";
        return phoneNumber.matches(phonePattern);
    }

    public interface DealerAdditionCallback {
        void onDealerAdded(int dealerId);
    }

    public interface CompanyAdditionCallback {
        void onCompanyAdded(int companyId);
    }
}
