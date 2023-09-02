package com.example.ceylincovip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ceylincovip.Connection.RetrofitClient;
import com.example.ceylincovip.Interface.Methods;
import com.example.ceylincovip.Modal.ApiResponse;
import com.example.ceylincovip.Modal.EmailRequestBody;
import com.example.ceylincovip.Modal.OTP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOTP extends AppCompatActivity {

    Button checkotp, back;

    EditText otp;

    private static final String TAG = "CheckOTP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_otp);

        checkotp = findViewById(R.id.btncheckotp);
        back = findViewById(R.id.btnnBacktoReset);
        otp = findViewById(R.id.otp);

        Intent intent = getIntent();
        int insertedId = intent.getIntExtra("insertedId", -1);
        String token = intent.getStringExtra("token");

        Log.e(TAG, "token" + token);

        otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called before the text is changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called when the text is changing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This method is called after the text has changed
                String enteredOTP = editable.toString();

                if (enteredOTP.length() != 6) {
                    otp.setError("Please Enter Correct OTP");
                } else {
                    // Assuming you have a function to check OTP and you're passing token and insertedId
                    checkOTP(enteredOTP, token, insertedId);
                }
            }
        });

        checkotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getotp = otp.getText().toString();

                if (getotp.isEmpty()) {
                    otp.setError("Please Enter OTP");
                } else {
                    checkOTP(getotp, token, insertedId);
                }

            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckOTP.this, ResetPassword.class));
            }
        });
    }

    public void checkOTP(String otp, String token, int insertedId) {
        OTP Otp = new OTP(Integer.parseInt(otp),token , insertedId);

        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ApiResponse> call = api.checkOTP(Otp);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    String message = apiResponse.getMessage();
                    String token = apiResponse.getToken();
                    Log.e(TAG, "message" + message);
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
                        startNextActivity(token);
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

    private void startNextActivity(String token) {
        Intent intent = new Intent(CheckOTP.this, NewPassword.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

}