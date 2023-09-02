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
import com.example.ceylincovip.Modal.Dealer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    private TextView name, email, address, nic, phone, whatsapp, regdate, profile_name;
    Button changeEmail, ChnagePassword, changeDetails, changeUsername;
    LinearLayout profileEdit;
    LinearLayout account;
    Button back;
    private static final String TAG = "Profile";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.profile_fullName);
        address = findViewById(R.id.profile_address);
        nic = findViewById(R.id.profile_nic);
        email = findViewById(R.id.profile_email);
        phone = findViewById(R.id.profile_phone);
        whatsapp = findViewById(R.id.profile_whatsapp);
        regdate = findViewById(R.id.profile_regdate);

        profile_name = findViewById(R.id.profile_name);

        profileEdit = findViewById(R.id.profile_profile_change);
        account = findViewById(R.id.profile_account_edit);


        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        profile_name.setText("Hello! " + dealerFullname);

        getProfileData(dealerId, token, new ProfileCallback() {
            @Override
            public void onSuccess(Dealer dealer) {
                updateUI(dealer);
            }

            @Override
            public void onFailure() {
                Intent intent = new Intent(Profile.this, DealerLogin.class);
                startActivity(intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Profile.this, Account.class);
                intent1.putExtra("DEALER", dealerId);
                intent1.putExtra("TOKEN", token);
                intent1.putExtra("FULLNESS", dealerFullname);
                startActivity(intent1);
            }
        });

        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Profile.this, ProfileEdit.class);
                intent1.putExtra("DEALER", dealerId);
                intent1.putExtra("TOKEN", token);
                intent1.putExtra("FULLNESS", dealerFullname);

                intent1.putExtra("NAME", name.getText().toString());
                intent1.putExtra("ADDRESS", address.getText().toString());
                intent1.putExtra("NIC", nic.getText().toString());
                intent1.putExtra("PHONE", phone.getText().toString());
                intent1.putExtra("WHATSAPP", whatsapp.getText().toString());
                startActivity(intent1);
            }
        });

    }

    private void getProfileData(int dealerId, String token, final ProfileCallback callback) {
        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<List<Dealer>> dealerCall = api.getProfile(token, dealerId);
        dealerCall.enqueue(new Callback<List<Dealer>>() {
            @Override
            public void onResponse(Call<List<Dealer>> call, Response<List<Dealer>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                    Dealer dealer = response.body().get(0); // Get the first dealer object from the array
                    callback.onSuccess(dealer);
                } else {
                    Log.e(TAG, "Failed API call: " + response.code());
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<List<Dealer>> call, Throwable t) {
                // Handle failure if needed
                callback.onFailure();
            }
        });
    }

    private void updateUI(Dealer dealer) {
        // Update your UI elements with dealer data
        name.setText(dealer.getDealer_fullname());
        address.setText(dealer.getDealer_address());
        nic.setText(dealer.getDealer_nic());
        email.setText(dealer.getDealer_email());
        phone.setText(dealer.getDealer_phone());
        whatsapp.setText(dealer.getDealer_whatsapp_number());
        regdate.setText(dealer.getReg_date());
    }

    interface ProfileCallback {
        void onSuccess(Dealer dealer);
        void onFailure();
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        Intent intent1 = new Intent(Profile.this, DealerMain.class);
        intent1.putExtra("DEALER", dealerId);
        intent1.putExtra("TOKEN", token);
        intent1.putExtra("FULLNESS", dealerFullname);
        startActivity(intent1);
    }
}
