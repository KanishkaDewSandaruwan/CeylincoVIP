package com.example.ceylincovip;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.ceylincovip.Modal.Edit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeBankDetails extends AppCompatActivity {

    EditText edit_fields;
    TextView field_label;
    Button save;

    private static final String TAG = "Profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_bank_details);

        save = findViewById(R.id.btnSaveChangeBank);
        edit_fields = findViewById(R.id.edit_fields);
        field_label = findViewById(R.id.field_label);

        Intent intent = getIntent();

        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");
        String label = intent.getStringExtra("LABEL");
        String value = intent.getStringExtra("VALUE");
        String field = intent.getStringExtra("FIELD");

        field_label.setText(label);
        edit_fields.setText(value);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getValue = edit_fields.getText().toString();

                if(getValue.isEmpty()){
                    Intent intent1 = new Intent(ChangeBankDetails.this, PaymentMethod.class);
                    intent1.putExtra("DEALER", dealerId);
                    intent1.putExtra("TOKEN", token);
                    intent1.putExtra("FULLNESS", dealerFullname);
                    startActivity(intent1);
                }else{
                    changeBankdetails(String.valueOf(dealerId), token, field,getValue );
                }

            }
        });
    }

    private void changeBankdetails(String dealerId, String token, String field, String value) {
        Edit edit = new Edit(field, value);

        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ApiResponse> dealerCall = api.changeValues(dealerId, field, token, edit);

        dealerCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null) {
                        Intent intent = getIntent();
                        int dealerId = intent.getIntExtra("DEALER", 0);
                        String token = intent.getStringExtra("TOKEN");
                        String dealerFullname = intent.getStringExtra("FULLNESS");

                        Intent intent1 = new Intent(ChangeBankDetails.this, PaymentMethod.class);
                        intent1.putExtra("DEALER", dealerId);
                        intent1.putExtra("TOKEN", token);
                        intent1.putExtra("FULLNESS", dealerFullname);
                        startActivity(intent1);
                    } else {
                        Log.e(TAG, "No bank information available for the dealer.");
                    }
                } else {
                    Log.e(TAG, "Failed API call: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, "API call failed with an exception: " + t.getMessage());
            }
        });
    }



    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        Intent intent1 = new Intent(ChangeBankDetails.this, PaymentMethod.class);
        intent1.putExtra("DEALER", dealerId);
        intent1.putExtra("TOKEN", token);
        intent1.putExtra("FULLNESS", dealerFullname);
        startActivity(intent1);
    }
}