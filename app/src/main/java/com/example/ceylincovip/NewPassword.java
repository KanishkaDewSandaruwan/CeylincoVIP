package com.example.ceylincovip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.example.ceylincovip.Modal.NewPasswordModal;
import com.example.ceylincovip.Modal.OTP;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class NewPassword extends AppCompatActivity {

    Button addnewPassword, back;

    EditText newpassword, confirmNewPassword;

    private static final String TAG = "CheckOTP";

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        addnewPassword = findViewById(R.id.btnchangePassword);
        back = findViewById(R.id.btnnBacktoResetopen);

        newpassword = findViewById(R.id.newpass);
        confirmNewPassword = findViewById(R.id.confirmnewpass);

        Intent intent = getIntent();
        String token = intent.getStringExtra("token");

        newpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String getNewPassword = editable.toString();

                if(!isPasswordValid(getNewPassword)) {
                    newpassword.setError("Password must contain at least 8 characters, one uppercase letter, one lowercase letter, and one digit or special character.");
                }else{
                    newpassword.setError(null);
                }
            }
        });

        confirmNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String getNewPassword = newpassword.getText().toString();
                String getNewConfirmPassword = editable.toString();

                if(!getNewPassword.equals(getNewConfirmPassword)) {
                    confirmNewPassword.setError("Passwords do not match.");
                }else{
                    confirmNewPassword.setError(null);
                }
            }
        });

        addnewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNewPassword = newpassword.getText().toString();
                String getNewConfirmPassword = confirmNewPassword.getText().toString();

                if (getNewPassword.isEmpty()) {
                    newpassword.setError("Please Enter New Password");
                }else if (getNewConfirmPassword.isEmpty()) {
                    confirmNewPassword.setError("Please Enter Confirm Password");
                } else if(!isPasswordValid(getNewPassword)) {
                    newpassword.setError("Password must contain at least 8 characters, one uppercase letter, one lowercase letter, and one digit or special character.");
                } else if(!getNewPassword.equals(getNewConfirmPassword)) {
                    confirmNewPassword.setError("Passwords do not match.");
                }else{
                    newPassword(getNewPassword, getNewConfirmPassword, token);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewPassword.this, ResetPassword.class));
            }
        });
    }

    public void newPassword(String password, String confirmNewPassword, String token) {
        NewPasswordModal newPassword = new NewPasswordModal(password, token, confirmNewPassword);

        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ApiResponse> call = api.addPassword(newPassword);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    String message = apiResponse.getMessage();

                    Log.e(TAG, "message: " + message);

                    showResponseDialog(message);
                } else {
                    Log.e(TAG, "Failed to parse response JSON");
                    showErrorDialog("Failed to reset password.");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, "API call failed", t);

                // Show the error response from the server if available
                if (t instanceof HttpException) {
                    ResponseBody errorBody = ((HttpException) t).response().errorBody();
                    if (errorBody != null) {
                        try {
                            String errorMessage = errorBody.string();
                            showErrorDialog(errorMessage);
                        } catch (IOException e) {
                            showErrorDialog("An error occurred. Please try again.");
                        }
                    } else {
                        showErrorDialog("An error occurred. Please try again.");
                    }
                } else {
                    showErrorDialog("An error occurred. Please try again.");
                }
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

    private boolean isPasswordValid(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d|[^\\da-zA-Z]).{8,}$";
        return password.matches(regex);
    }

    private void startNextActivity() {
        Intent intent = new Intent(NewPassword.this, DealerLogin.class);
        startActivity(intent);
    }
}