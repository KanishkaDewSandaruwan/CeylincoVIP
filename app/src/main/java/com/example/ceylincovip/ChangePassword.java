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
import com.example.ceylincovip.Modal.ChangePasswordModal;
import com.example.ceylincovip.Modal.Dealer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    private EditText currentpassword, newpassword, confirmpassword;
    private static final String TAG = "ChangePasswords";
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        save = findViewById(R.id.btnChangePassword);

        confirmpassword = findViewById(R.id.change_confirm_password);
        newpassword = findViewById(R.id.change_password_new);
        currentpassword = findViewById(R.id.change_password_current);

        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getconfirmpassword = confirmpassword.getText().toString();
                String getnewpassword = newpassword.getText().toString();
                String getcurrentpassword = currentpassword.getText().toString();

                if(getnewpassword.equals(getconfirmpassword)) {
                    // Call the editProfileData method
                    changePassword(dealerId, token, getcurrentpassword, getnewpassword, new ChangePassword.ChangePasswordCallback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure() {
                            // Handle failure
                        }
                    });
                }else{
                    showErrorDialog("Password is not match");
                }
            }
        });
    }

    private void changePassword(int dealerId, String token, String currentPassword, String newPassword, final ChangePassword.ChangePasswordCallback callback) {
        ChangePasswordModal changePasswordModal = new ChangePasswordModal(currentPassword, newPassword);

        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ApiResponse> dealerCall = api.changePassword(token, dealerId, changePasswordModal);
        dealerCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle success
                    callback.onSuccess();

                    // Show success dialog
                    showResponseDialog(response.body().getMessage(), token);
                } else {
                    // Handle failure
                    callback.onFailure();
                    // Show error dialog
                    showErrorDialog("Current Password is wrong");
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

    private void showResponseDialog(String message, String token) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Response")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        Intent intent1 = new Intent(ChangePassword.this, DealerLogin.class);
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



    interface ChangePasswordCallback {
        void onSuccess();

        void onFailure();
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        Intent intent1 = new Intent(ChangePassword.this, Account.class);
        intent1.putExtra("DEALER", dealerId);
        intent1.putExtra("TOKEN", token);
        intent1.putExtra("FULLNESS", dealerFullname);
        startActivity(intent1);
    }
}