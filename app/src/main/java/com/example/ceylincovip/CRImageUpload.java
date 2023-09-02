package com.example.ceylincovip;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ceylincovip.Connection.RetrofitClient;
import com.example.ceylincovip.Interface.Methods;
import com.example.ceylincovip.Modal.ApiResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CRImageUpload extends AppCompatActivity {

    ImageButton upload;
    private ActivityResultLauncher<String> pickImageLauncher;
    private Methods api;
    private static final String TAG = "StartApp";
    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    Button back;
    private int policyId; // Declare the policy_id as an instance variable
    private String fieldImage; // Declare the field image name as an instance variable
    private ProgressBar loadingIndicator;

    private boolean isLoading = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crimageupload);

        back = findViewById(R.id.btnBacktoRegister02);
        upload = findViewById(R.id.uploadImageButton);
        loadingIndicator = findViewById(R.id.loadingIndicator);

        Intent intent = getIntent();
        policyId = intent.getIntExtra("POLICY_ID", 0);
        fieldImage = intent.getStringExtra("FIELD"); // Get the field image name from the intent
        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLoading) { // Check if a request is already in progress
                    isLoading = true;

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CRImageUpload.this, InsurenceInformation.class);
                intent.putExtra("POLICY_ID", policyId);
                intent.putExtra("FIELD", fieldImage);
                intent.putExtra("DEALER", dealerId);
                intent.putExtra("TOKEN", token);
                intent.putExtra("FULLNESS", dealerFullname);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            File file = new File(getRealPathFromUri(uri));

            api = RetrofitClient.getRetrofitInstance().create(Methods.class);

            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
            Call<ApiResponse> call = api.uploadImage(fieldImage, policyId, part); // Pass the field image name as a parameter

            // Show loading indicator
            loadingIndicator.setVisibility(View.VISIBLE);

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    // Hide loading indicator
                    loadingIndicator.setVisibility(View.GONE);
                    isLoading = false;

                    if (response.isSuccessful()) {
                        Intent intent1 = getIntent();
                        int dealerId = intent1.getIntExtra("DEALER", 0);
                        String token = intent1.getStringExtra("TOKEN");
                        String dealerFullname = intent1.getStringExtra("FULLNESS");

                        Toast.makeText(getApplicationContext(), "Image CRImageUpload Success " + response.body().getFilename(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: " + response.body());
                        Intent intent = new Intent(CRImageUpload.this, InsurenceInformation.class);
                        intent.putExtra("POLICY_ID", policyId);
                        intent.putExtra("FIELD", fieldImage);
                        intent.putExtra("DEALER", dealerId);
                        intent.putExtra("TOKEN", token);
                        intent.putExtra("FULLNESS", dealerFullname);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    // Hide loading indicator
                    loadingIndicator.setVisibility(View.GONE);
                    isLoading = false;

                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }

}
