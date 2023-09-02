package com.example.ceylincovip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ceylincovip.Connection.RetrofitClient;
import com.example.ceylincovip.Interface.Methods;
import com.example.ceylincovip.Modal.Bank;
import com.example.ceylincovip.Modal.Commision;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethod extends AppCompatActivity {

    Button addnew, edit;
    TextView account_name, account_number, account_bank, account_branch, created_date;

    TextView account_name_label, account_number_label, account_bank_label, account_branch_label;
    LinearLayout account_name_container, account_number_container, account_bank_container, account_bankbranch_container;
    private static final String TAG = "Profile";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        account_name = findViewById(R.id.account_name);
        account_number = findViewById(R.id.account_number);
        account_bank = findViewById(R.id.account_bank);
        account_branch = findViewById(R.id.account_branch);

        account_name_label = findViewById(R.id.account_name_label);
        account_number_label = findViewById(R.id.account_number_label);
        account_bank_label = findViewById(R.id.account_bank_label);
        account_branch_label = findViewById(R.id.account_branch_label);

        account_name_container = findViewById(R.id.account_name_container);
        account_number_container = findViewById(R.id.account_number_container);
        account_bank_container = findViewById(R.id.account_bank_container);
        account_bankbranch_container = findViewById(R.id.account_bankbranch_container);

        addnew = findViewById(R.id.btnAddnewBankAccount);

        account_name_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String account_name_fieldname = "account_name";

                Intent intent1 = new Intent(PaymentMethod.this, ChangeBankDetails.class);
                intent1.putExtra("DEALER", dealerId);
                intent1.putExtra("TOKEN", token);
                intent1.putExtra("FULLNESS", dealerFullname);

                intent1.putExtra("LABEL", account_name_label.getText().toString());
                intent1.putExtra("VALUE", account_name.getText().toString());
                intent1.putExtra("FIELD", account_name_fieldname);

                startActivity(intent1);
            }
        });


        account_number_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fieldname = "account_number";

                Intent intent1 = new Intent(PaymentMethod.this, ChangeBankDetails.class);
                intent1.putExtra("DEALER", dealerId);
                intent1.putExtra("TOKEN", token);
                intent1.putExtra("FULLNESS", dealerFullname);

                intent1.putExtra("LABEL", account_number_label.getText().toString());
                intent1.putExtra("VALUE", account_number.getText().toString());
                intent1.putExtra("FIELD", fieldname);

                startActivity(intent1);
            }
        });


        account_bank_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fieldname = "account_bank";

                Intent intent1 = new Intent(PaymentMethod.this, ChangeBankDetails.class);
                intent1.putExtra("DEALER", dealerId);
                intent1.putExtra("TOKEN", token);
                intent1.putExtra("FULLNESS", dealerFullname);

                intent1.putExtra("LABEL", account_bank_label.getText().toString());
                intent1.putExtra("VALUE", account_bank.getText().toString());
                intent1.putExtra("FIELD", fieldname);

                startActivity(intent1);
            }
        });


        account_bankbranch_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fieldname = "account_bank_branch";

                Intent intent1 = new Intent(PaymentMethod.this, ChangeBankDetails.class);
                intent1.putExtra("DEALER", dealerId);
                intent1.putExtra("TOKEN", token);
                intent1.putExtra("FULLNESS", dealerFullname);

                intent1.putExtra("LABEL", account_branch_label.getText().toString());
                intent1.putExtra("VALUE", account_branch.getText().toString());
                intent1.putExtra("FIELD", fieldname);

                startActivity(intent1);
            }
        });

        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(PaymentMethod.this, AddBankAccount.class);
                intent1.putExtra("DEALER", dealerId);
                intent1.putExtra("TOKEN", token);
                intent1.putExtra("FULLNESS", dealerFullname);
                startActivity(intent1);
            }
        });

        getPaymentMethod(dealerId, token, new PaymentMethod.DealerCallBack() {
            @Override
            public void onSuccess(Bank bank) {
                Log.e(TAG, "Success");
            }

            @Override
            public void onFailure(String message) {
                Log.e(TAG, "Failed API call: ");
            }
        });
    }

    private void getPaymentMethod(int dealerId, String token, final PaymentMethod.DealerCallBack callback) {
        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<List<Bank>> dealerCall = api.getDealerBank(token, dealerId);

        dealerCall.enqueue(new Callback<List<Bank>>() {
            @Override
            public void onResponse(Call<List<Bank>> call, Response<List<Bank>> response) {
                if (response.isSuccessful()) {
                    List<Bank> banks = response.body();
                    if (banks != null && !banks.isEmpty()) {
                        Bank bank = banks.get(0);

                        account_name = findViewById(R.id.account_name);
                        account_number = findViewById(R.id.account_number);
                        account_branch = findViewById(R.id.account_branch);
                        account_bank = findViewById(R.id.account_bank);

                        account_name.setText(bank.getAccount_name());
                        account_number.setText(bank.getAccount_number());
                        account_branch.setText(bank.getAccount_bank_branch());
                        account_bank.setText(bank.getAccount_bank());

                        LinearLayout vertical_layout_show = findViewById(R.id.vertical_layout_show);
                        vertical_layout_show.setVisibility(View.VISIBLE);

                        TextView bank_notify = findViewById(R.id.bank_notify);
                        bank_notify.setVisibility(View.GONE);

                        Button addnewBankAccount = findViewById(R.id.btnAddnewBankAccount);
                        addnewBankAccount.setVisibility(View.GONE);

                        callback.onSuccess(bank); // Notify the caller of success
                    } else {

                        LinearLayout vertical_layout_show = findViewById(R.id.vertical_layout_show);
                        vertical_layout_show.setVisibility(View.GONE);

                        TextView bank_notify = findViewById(R.id.bank_notify);
                        bank_notify.setVisibility(View.VISIBLE);
                        bank_notify.setText("Withdrawable Method is Not available. please add your Withdrawable details");

                        Button addnewBankAccount = findViewById(R.id.btnAddnewBankAccount);
                        addnewBankAccount.setVisibility(View.VISIBLE);

                        Log.e(TAG, "No bank information available for the dealer.");
                        callback.onFailure("No bank information available.");
                    }
                } else {
                    Log.e(TAG, "Failed API call: " + response.code());
                    callback.onFailure("Failed API call: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Bank>> call, Throwable t) {
                Log.e(TAG, "API call failed with an exception: " + t.getMessage());
                callback.onFailure("API call failed with an exception: " + t.getMessage());
            }
        });
    }


    interface DealerCallBack {
        void onSuccess(Bank bank);
        void onFailure(String message);
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        Intent intent1 = new Intent(PaymentMethod.this, Account.class);
        intent1.putExtra("DEALER", dealerId);
        intent1.putExtra("TOKEN", token);
        intent1.putExtra("FULLNESS", dealerFullname);
        startActivity(intent1);
    }
}