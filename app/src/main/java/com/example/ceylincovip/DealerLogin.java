package com.example.ceylincovip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ceylincovip.Connection.RetrofitClient;
import com.example.ceylincovip.Interface.Methods;
import com.example.ceylincovip.Modal.ApiResponse;
import com.example.ceylincovip.Modal.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerLogin extends AppCompatActivity {

    EditText username, password;
    Button btnlogin, btnregister;
    private Methods api;

    ImageButton about_uss;
    TextView fogetPassword;
    private static final String TAG = "StartApp";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        btnregister = findViewById(R.id.btnregister);
        btnlogin = findViewById(R.id.btnlogin);

        about_uss = findViewById(R.id.about_us1);

        fogetPassword = findViewById(R.id.fogetpassword);

        about_uss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DealerLogin.this, AboutUS.class);
                startActivity(intent);
            }
        });

        fogetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ResetPassword.class));
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DealerLogin.this, DealerRegisterPersonalInformation.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dealer_username = DealerLogin.this.username.getText().toString();
                String dealer_password = DealerLogin.this.password.getText().toString();

                if(dealer_username.isEmpty()){
                    username.setError("Username is empty!");
                }else if(dealer_password.isEmpty()){
                    password.setError("Password is empty!");
                }else {
                    Login login = new Login(dealer_password, dealer_username);
                    Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
                    Call<ApiResponse> loginresponse = api.getLogin(login);
                    loginresponse.enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            if (response.isSuccessful()) {
                                ApiResponse apiResponse = response.body();
                                if (apiResponse != null) {
                                    int dealerId = apiResponse.getDealer_id();
                                    String token = apiResponse.getToken();
                                    String dealer_fullname = apiResponse.getDealer_fullname();

                                    Intent intent = new Intent(DealerLogin.this, DealerMain.class);
                                    intent.putExtra("DEALER", dealerId);
                                    intent.putExtra("TOKEN", token);
                                    intent.putExtra("FULLNESS", dealer_fullname);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(getApplicationContext(), "Email or Password wrong", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                if(response.code() == 401){
                                    Toast.makeText(getApplicationContext(), "Email or Password wrong", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Email or Password wrong", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            Log.e(TAG, "API call failed: " + t.getMessage());
                        }
                    });
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        // Block the default back button behavior
        // Remove this line to enable back button functionality
    }
}