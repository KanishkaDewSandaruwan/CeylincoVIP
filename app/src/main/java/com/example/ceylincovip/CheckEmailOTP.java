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
import com.example.ceylincovip.Modal.OTP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckEmailOTP extends AppCompatActivity {

    private EditText otp;
    private static final String TAG = "OTPCHECK";
    private Button validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_email_otp);

        validate = findViewById(R.id.btnvalidateEmailChange);

        otp = findViewById(R.id.validate_email_otp);

        Intent intent = getIntent();
        int insertedId = intent.getIntExtra("INSERTED", 0);
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");
        String getnewemail = intent.getStringExtra("NEWEMAIL");

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getotp = otp.getText().toString();

                if (getotp.isEmpty()) {
                    otp.setError("Please Enter OTP");
                } else {
                    checkEmailOTP(getotp, token, insertedId, getnewemail, dealerId);
                }
            }
        });
    }

    public void checkEmailOTP(String otp, String token, int insertedId, String newEmail, int dealerId) {
        OTP Otp = new OTP(Integer.parseInt(otp), insertedId, newEmail, dealerId);

        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ApiResponse> call = api.checkEmailOTP(token, Otp);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {

                    ApiResponse apiResponse = response.body();
                    String message = apiResponse.getMessage();
                    showResponseDialog(message, token);
                } else {
                    Log.e(TAG, "Failed to parse response JSON");
                    showErrorDialog("Failed to reset password.");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, "API call failed", t);
                showErrorDialog("An error occurred. Please try again.");
            }
        });
    }

    private void showResponseDialog(String message, String token) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Response")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startNextActivity();
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

    private void startNextActivity() {
        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String tokenpass = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        Intent intent1 = new Intent(CheckEmailOTP.this, DealerLogin.class);
        startActivity(intent1);
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        Intent intent1 = new Intent(CheckEmailOTP.this, Account.class);
        intent1.putExtra("DEALER", dealerId);
        intent1.putExtra("TOKEN", token);
        intent1.putExtra("FULLNESS", dealerFullname);
        startActivity(intent1);
    }

}