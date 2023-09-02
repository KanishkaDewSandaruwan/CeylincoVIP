package com.example.ceylincovip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ceylincovip.Connection.RetrofitClient;
import com.example.ceylincovip.Interface.Methods;
import com.example.ceylincovip.Modal.ApiResponse;
import com.example.ceylincovip.Modal.Bank;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBankAccount extends AppCompatActivity {

    EditText holder_name, account_number, bank, branch;
    Button addNew;

    private static final String TAG = "Profile";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_account);

        holder_name = findViewById(R.id.account_name);
        account_number = findViewById(R.id.account_number);
        bank = findViewById(R.id.account_bank);
        branch = findViewById(R.id.account_bank_branch);

        addNew = findViewById(R.id.btnsaveAddnewBank);

        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getholder_name = holder_name.getText().toString();
                String getaccount_number = account_number.getText().toString();
                String getbank = bank.getText().toString();
                String getbranch = branch.getText().toString();

                if (validateInputFields(getholder_name, getaccount_number, getbank, getbranch)) {

                    addbankAccount(dealerId, token, getholder_name, getaccount_number, getbank, getbranch, new AddBankAccount.DealerCallBack() {
                    @Override
                    public void onSuccess(String message) {

                    }

                    @Override
                    public void onFailure(String message) {
                        // Handle failure
                    }
                });

                }
            }
        });

    }

    private boolean validateInputFields(String name, String account_number, String bank, String branch) {
        if (name.isEmpty() || account_number.isEmpty() || bank.isEmpty() || branch.isEmpty()) {
            showErrorDialog("Please fill all the fields.");
            return false;
        }

        return true;
    }


    private void addbankAccount(int dealerId, String token, String holder_name, String accountnumber, String bank, String branch, final AddBankAccount.DealerCallBack callback) {

        Bank bank1 = new Bank(String.valueOf(dealerId), holder_name, accountnumber, bank, branch);

        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ApiResponse> dealerCall = api.bankCreate(token, bank1);

        dealerCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse response1 = response.body();
                    if (response1.isSuccess()) {
                        showResponseDialog("Bank Account Adding Success!");

                        callback.onSuccess(response1.getMessage()); // Notify the caller of success
                    } else {

                        Log.e(TAG, "No bank information available for the dealer." + response1.getError());
                        callback.onFailure("No bank information available.");
                    }
                } else {
                    showErrorDialog("Account already created or something went wrong. try again later or contacy us");
                    callback.onFailure("Failed API call: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, "API call failed with an exception: " + t.getMessage());
                callback.onFailure("API call failed with an exception: " + t.getMessage());
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

                        Intent intent = getIntent();
                        int dealerId = intent.getIntExtra("DEALER", 0);
                        String token = intent.getStringExtra("TOKEN");
                        String dealerFullname = intent.getStringExtra("FULLNESS");

                        Intent intent1 = new Intent(AddBankAccount.this, PaymentMethod.class);
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

    interface DealerCallBack {
        void onSuccess(String message);
        void onFailure(String message);
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        Intent intent1 = new Intent(AddBankAccount.this, PaymentMethod.class);
        intent1.putExtra("DEALER", dealerId);
        intent1.putExtra("TOKEN", token);
        intent1.putExtra("FULLNESS", dealerFullname);
        startActivity(intent1);
    }
}