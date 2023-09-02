package com.example.ceylincovip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ceylincovip.Connection.RetrofitClient;
import com.example.ceylincovip.Interface.Methods;
import com.example.ceylincovip.Modal.ApiResponse;
import com.example.ceylincovip.Modal.OTP;
import com.example.ceylincovip.Modal.SpeacialModal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Account extends AppCompatActivity {

    private TextView profile_name;

    LinearLayout changeEmail, changePassword, deactiveAccount, paymentMethod;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        profile_name = findViewById(R.id.profile_name_account);

        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        changePassword = findViewById(R.id.change_password);
        changeEmail = findViewById(R.id.change_email);
        deactiveAccount = findViewById(R.id.deactive_account);
        paymentMethod = findViewById(R.id.bank_details);

        profile_name.setText("Hello! " + dealerFullname);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Account.this, ChangePassword.class);
                intent1.putExtra("DEALER", dealerId);
                intent1.putExtra("TOKEN", token);
                intent1.putExtra("FULLNESS", dealerFullname);
                startActivity(intent1);
            }
        });

        paymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Account.this, PaymentMethod.class);
                intent1.putExtra("DEALER", dealerId);
                intent1.putExtra("TOKEN", token);
                intent1.putExtra("FULLNESS", dealerFullname);
                startActivity(intent1);
            }
        });


        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Account.this, ChangeEmail.class);
                intent1.putExtra("DEALER", dealerId);
                intent1.putExtra("TOKEN", token);
                intent1.putExtra("FULLNESS", dealerFullname);
                startActivity(intent1);
            }
        });

        deactiveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Account.this); // Replace YourActivityName with the name of your activity
                builder.setTitle("Deactivate Account")
                        .setMessage("Are you sure you want to deactivate your account?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deactiveAccount(token, dealerId );
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Handle "No" button click here
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });


    }

    public void deactiveAccount(String token, int dealerId) {

        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ApiResponse> call = api.deactiveAccount(token, dealerId);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    String message = apiResponse.getMessage();
                    showResponseDialog(message);

                } else {
                    showErrorDialog("Fail to Deactivate Account");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                showErrorDialog("An error occurred. Please try again.");
            }
        });
    }

    private void showResponseDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Response")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent1 = new Intent(Account.this, DealerLogin.class);
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

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        Intent intent1 = new Intent(Account.this, Profile.class);
        intent1.putExtra("DEALER", dealerId);
        intent1.putExtra("TOKEN", token);
        intent1.putExtra("FULLNESS", dealerFullname);
        startActivity(intent1);
    }
}