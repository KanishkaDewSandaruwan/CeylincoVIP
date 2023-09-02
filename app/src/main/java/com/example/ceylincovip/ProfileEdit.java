package com.example.ceylincovip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ceylincovip.Connection.RetrofitClient;
import com.example.ceylincovip.Interface.Methods;
import com.example.ceylincovip.Modal.ApiResponse;
import com.example.ceylincovip.Modal.Dealer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEdit extends AppCompatActivity {

    private EditText fullname, address, nic, phone, whatsapp;
    private static final String TAG = "ProfileEdit";
    private Button save;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        save = findViewById(R.id.btneditdetails);

        fullname = findViewById(R.id.editFullName);
        address = findViewById(R.id.editAddress);
        nic = findViewById(R.id.editIDNumber);
        phone = findViewById(R.id.editContactNumber);
        whatsapp = findViewById(R.id.editwhatsappNumber);

        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        String intentname = intent.getStringExtra("NAME");
        String intentaddress = intent.getStringExtra("ADDRESS");
        String intentnic = intent.getStringExtra("NIC");
        String intentphone = intent.getStringExtra("PHONE");
        String intentwhatsapp = intent.getStringExtra("WHATSAPP");

        // Set received values in EditText views
        fullname.setText(intentname);
        address.setText(intentaddress);
        nic.setText(intentnic);
        phone.setText(intentphone);
        whatsapp.setText(intentwhatsapp);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getfullname = fullname.getText().toString();
                String getaddress = address.getText().toString();
                String getnic = nic.getText().toString();
                String getphone = phone.getText().toString();
                String getwhatsapp = whatsapp.getText().toString();

                // Call the editProfileData method
                editProfileData(dealerId, token, getfullname, getaddress, getnic, getphone, getwhatsapp, new ProfileCallback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure() {
                        // Handle failure
                    }
                });
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

                        Intent intent = getIntent();
                        int dealerId = intent.getIntExtra("DEALER", 0);
                        String token = intent.getStringExtra("TOKEN");
                        String dealerFullname = intent.getStringExtra("FULLNESS");

                        Intent intent1 = new Intent(ProfileEdit.this, Profile.class);
                        intent1.putExtra("DEALER", dealerId);
                        intent1.putExtra("TOKEN", token);
                        intent1.putExtra("FULLNESS", dealerFullname);
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

    private void editProfileData(int dealerId, String token, String fullname, String address, String nic, String phone, String whatsapp, final ProfileCallback callback) {
        Dealer dealer = new Dealer(fullname, address, nic, phone, whatsapp);

        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ApiResponse> dealerCall = api.updateProfile(token, dealerId, dealer);
        dealerCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle success
                    callback.onSuccess();

                    // Show success dialog
                    showResponseDialog("Profile updated successfully", token);
                } else {
                    // Handle failure
                    callback.onFailure();

                    // Show error dialog
                    showErrorDialog("Failed to update profile");
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

    interface ProfileCallback {
        void onSuccess();

        void onFailure();
    }


    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        Intent intent1 = new Intent(ProfileEdit.this, Profile.class);
        intent1.putExtra("DEALER", dealerId);
        intent1.putExtra("TOKEN", token);
        intent1.putExtra("FULLNESS", dealerFullname);
        startActivity(intent1);
    }
}
