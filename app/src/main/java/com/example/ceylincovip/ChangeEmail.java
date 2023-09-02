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
import com.example.ceylincovip.Interface.Methods;
import com.example.ceylincovip.Modal.ApiResponse;
import com.example.ceylincovip.Modal.ChangeEmailModal;
import com.example.ceylincovip.Modal.ChangeEmailModal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeEmail extends AppCompatActivity {

    private EditText currentemail, newemail;
    private static final String TAG = "ChangeEmails";
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        save = findViewById(R.id.btnChangeEmail);

        currentemail = findViewById(R.id.change_email_current);
        newemail = findViewById(R.id.change_email_new);

        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");
        Log.e(TAG, "Change Email" + token);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getcurrentemail = currentemail.getText().toString();
                String getnewemail = newemail.getText().toString();

                if (validateInputFields(getcurrentemail, getnewemail)) {
                    validate("dealer_email", getnewemail, new ChangeEmail.ValidationCallback() {
                        @Override
                        public void onValidationSuccess(int emailCount) {
                            if (emailCount == 0) {

                                changeEmail(dealerId, token, getcurrentemail, getnewemail, new ChangeEmail.ChangeEmailCallback() {
                                    @Override
                                    public void onSuccess() {
                                    }

                                    @Override
                                    public void onFailure() {
                                        // Handle failure
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

    private boolean validateInputFields(String getcurrentemail, String getnewemail) {
        if (getcurrentemail.isEmpty() || getnewemail.isEmpty()) {
            showErrorDialog("Please fill all the fields.");
            return false;
        }

        if (!isValidEmail(getcurrentemail)) {
            showErrorDialog("Email is not valid");
            return false;
        }

        if (!isValidEmail(getnewemail)) {
            showErrorDialog("Email is not valid");
            return false;
        }

        return true;
    }

    private void validate(String field, String value, final ChangeEmail.ValidationCallback callback) {
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

    private void changeEmail(int dealerId, String token, String currentemail, String newemail, final ChangeEmail.ChangeEmailCallback callback) {
        ChangeEmailModal changeEmailModal = new ChangeEmailModal(currentemail, newemail);

        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ApiResponse> dealerCall = api.changeEmail(token, dealerId, changeEmailModal);
        dealerCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle success
                    callback.onSuccess();
                    ApiResponse apiResponse = response.body();

                    String message = apiResponse.getMessage();
                    int insertedId = apiResponse.getinsertedId();
                    showResponseDialog(message, insertedId, newemail);

                } else {
                    // Handle failure
                    callback.onFailure();
                    // Show error dialog
                    showErrorDialog("Current Email is wrong");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Handle failure
                callback.onFailure();

                // Show error dialog
                showErrorDialog("An error occurred while updating profile");
            }
        });
    }

    private void showResponseDialog(String message, int insertedId, String newemail) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Response")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        Intent intent = getIntent();
                        int dealerId = intent.getIntExtra("DEALER", 0);
                        String token = intent.getStringExtra("TOKEN");
                        String dealerFullname = intent.getStringExtra("FULLNESS");

                        Intent intent1 = new Intent(ChangeEmail.this, CheckEmailOTP.class);
                        intent1.putExtra("DEALER", dealerId);
                        intent1.putExtra("TOKEN", token);
                        intent1.putExtra("FULLNESS", dealerFullname);
                        intent1.putExtra("INSERTED", insertedId);
                        intent1.putExtra("NEWEMAIL", newemail);
                        startActivity(intent1);
                    }
                })
                .show();
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



    interface ChangeEmailCallback {
        void onSuccess();

        void onFailure();
    }

    interface ValidationCallback {
        void onValidationSuccess(int count);
        void onValidationFailure();
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        Intent intent1 = new Intent(ChangeEmail.this, Account.class);
        intent1.putExtra("DEALER", dealerId);
        intent1.putExtra("TOKEN", token);
        intent1.putExtra("FULLNESS", dealerFullname);
        startActivity(intent1);
    }
}