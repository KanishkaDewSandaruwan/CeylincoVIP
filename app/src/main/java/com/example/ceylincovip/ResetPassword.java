package com.example.ceylincovip;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ceylincovip.Connection.RetrofitClient;
import com.example.ceylincovip.DealerLogin;
import com.example.ceylincovip.Interface.Methods;
import com.example.ceylincovip.Modal.ApiResponse;
import com.example.ceylincovip.Modal.EmailRequestBody;
import com.example.ceylincovip.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {

    TextView backLogin;
    Button resetPassword;

    EditText email;
    private static final String TAG = "ResetPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        backLogin = findViewById(R.id.fogetpasswordtobackLogin);
        resetPassword = findViewById(R.id.btnrestPassword);
        email = findViewById(R.id.email);

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DealerLogin.class));
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail = email.getText().toString();

                if (getEmail.isEmpty()) {
                    email.setError("Please Enter Email");
                } else {
                    resetPassword(getEmail);
                }
            }
        });
    }

    public void resetPassword(String email) {
        EmailRequestBody requestBody = new EmailRequestBody(email);

        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ApiResponse> call = api.fogetPassword(requestBody);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    String message = apiResponse.getMessage();
                    String token = apiResponse.getToken();
                    int insertedId = apiResponse.getinsertedId();
                    showResponseDialog(message, insertedId, token);

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

    private void showResponseDialog(String message, int insertedId, String token) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Response")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startNextActivity(insertedId, token);
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

    private void startNextActivity(int insertedId, String token) {
        Intent intent = new Intent(ResetPassword.this, CheckOTP.class);
        intent.putExtra("insertedId", insertedId);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        return email.matches(emailPattern);
    }
}
