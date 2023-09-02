package com.example.ceylincovip;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ceylincovip.Connection.RetrofitClient;
import com.example.ceylincovip.Interface.Methods;
import com.example.ceylincovip.Modal.Commision;
import com.example.ceylincovip.Modal.Dealer;
import com.example.ceylincovip.StartApp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerMain extends AppCompatActivity {

    TextView welcome_label, paid_commision, pending_commision;
    Button btn_add_policy;
    private static final String TAG = "Profile";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_main);

        welcome_label = findViewById(R.id.welcome_label);
        btn_add_policy = findViewById(R.id.btn_add_policy);



        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        welcome_label.setText("Hello! " + dealerFullname);

        getCommision(dealerId, token, new DealerMain.DealerCallBack() {
            @Override
            public void onSuccess(Commision commision) {
            }

            @Override
            public void onFailure() {
                Intent intent = new Intent(DealerMain.this, DealerLogin.class);
                startActivity(intent);
            }
        });

        welcome_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(DealerMain.this, Profile.class);
                intent1.putExtra("DEALER", dealerId);
                intent1.putExtra("TOKEN", token);
                intent1.putExtra("FULLNESS", dealerFullname);
                startActivity(intent1);
            }
        });

        btn_add_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(DealerMain.this, StartApp.class);
                intent1.putExtra("DEALER", dealerId);
                intent1.putExtra("TOKEN", token);
                intent1.putExtra("FULLNESS", dealerFullname);
                startActivity(intent1);
            }
        });
    }

    private void getCommision(int dealerId, String token,  final DealerMain.DealerCallBack callback) {
        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Commision> dealerCall = api.getCommision(token, dealerId);
        dealerCall.enqueue(new Callback<Commision>() {
            @Override
            public void onResponse(Call<Commision> call, Response<Commision> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Commision commision = response.body();

                    pending_commision = findViewById(R.id.pending_commision);
                    paid_commision = findViewById(R.id.paid_commision);

                    paid_commision.setText("Rs. " + commision.getPaidCommision());
                    pending_commision.setText( "Rs. " +  String.valueOf(commision.getPendingCommision()));

                } else {
                    Log.e(TAG, "Failed API call: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Commision> call, Throwable t) {

            }
        });
    }

    interface DealerCallBack {
        void onSuccess(Commision commision);
        void onFailure();
    }

    @Override
    public void onBackPressed() {
        // Block the default back button behavior
        // Remove this line to enable back button functionality
    }
}
