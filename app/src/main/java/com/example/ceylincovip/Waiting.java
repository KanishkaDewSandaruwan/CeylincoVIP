package com.example.ceylincovip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ceylincovip.Connection.RetrofitClient;
import com.example.ceylincovip.Interface.Methods;
import com.example.ceylincovip.Modal.ApiResponse;
import com.example.ceylincovip.Modal.Policy;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Waiting extends AppCompatActivity {

    TextView detailText, textemailview;

    Button btnHomeRedirect;
    private static final String TAG = "StartApp";
    private Methods api;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        Intent intent = getIntent();
        int policyId = intent.getIntExtra("POLICY_ID", 0);
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");


        detailText = findViewById(R.id.detailText);
        textemailview = findViewById(R.id.textemailview);
        btnHomeRedirect = findViewById(R.id.btnHomeRedirect);

        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<List<Policy>> call = api.getPolicy(policyId);  // Modified to expect a list of policies
        call.enqueue(new Callback<List<Policy>>() {  // Modified to expect a list of policies
            @Override
            public void onResponse(Call<List<Policy>> call, Response<List<Policy>> response) {
                if (response.isSuccessful()) {
                    List<Policy> policies = response.body();
                    if (policies != null && !policies.isEmpty()) {
                        Policy policy = policies.get(0);  // Assuming you want the first policy from the array
                        detailText.setText("Hi! " + policy.getCustomer_fullname());
                        textemailview.setText(policy.getCustomer_email());
                        Log.e(TAG, "Name: " + policy.getCustomer_fullname());
                    } else {
                        Log.e(TAG, "Received empty or null response body");
                    }
                } else {
                    Log.e(TAG, "Failed to retrieve policies. Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Policy>> call, Throwable t) {
                Log.e(TAG, "Failed to retrieve policies. Error: " + t.getMessage());
            }
        });

        btnHomeRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Waiting.this, DealerMain.class);
                intent.putExtra("DEALER", dealerId);
                intent.putExtra("TOKEN", token);
                intent.putExtra("FULLNESS", dealerFullname);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        // Create an Intent to navigate to the desired activity
        Intent intent1 = getIntent();
        int dealerId = intent1.getIntExtra("DEALER", 0);
        String token = intent1.getStringExtra("TOKEN");
        String dealerFullname = intent1.getStringExtra("FULLNESS");

        Intent intent = new Intent(Waiting.this, DealerMain.class);
        intent.putExtra("DEALER", dealerId);
        intent.putExtra("TOKEN", token);
        intent.putExtra("FULLNESS", dealerFullname);
        startActivity(intent);
    }
}