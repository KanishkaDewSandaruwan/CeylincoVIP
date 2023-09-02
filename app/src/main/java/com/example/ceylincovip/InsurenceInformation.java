package com.example.ceylincovip;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Callback;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ceylincovip.Connection.RetrofitClient;
import com.example.ceylincovip.Interface.Methods;
import com.example.ceylincovip.Modal.ApiResponse;
import com.example.ceylincovip.Modal.Policy;
import com.example.ceylincovip.Modal.PolicyStatusUpdate;
import com.example.ceylincovip.Modal.PurposeItem;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class InsurenceInformation extends AppCompatActivity {

    private static final String TAG = "StartApp";
    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private int globalPolicyId;
    EditText vehicleColor, horsePower, valueOfVehicle;
    Button submit, back, addCrImage, addVehicleImage, addPreviousCardImage;

    private ActivityResultLauncher<String> pickImageLauncher;

    private Methods api;

    Spinner addPerposeOfUse;

    String crImage, vehicleImage, previousCardImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurence_information);

        addPerposeOfUse = findViewById(R.id.addPerposeOfUse);

        List<PurposeItem> purposeOptions = new ArrayList<>();
        purposeOptions.add(new PurposeItem("Private", 1));
        purposeOptions.add(new PurposeItem("Rent", 2));
        purposeOptions.add(new PurposeItem("Hiring", 3));

        ArrayAdapter<PurposeItem> purposeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, purposeOptions);
        purposeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addPerposeOfUse.setAdapter(purposeAdapter);

        PurposeItem selectedPurposeItem = (PurposeItem) addPerposeOfUse.getSelectedItem();
        int selectedValue = selectedPurposeItem.getValue();

        globalPolicyId = 0;

        submit = findViewById(R.id.btnSubmit);
        back = findViewById(R.id.btnPreviousSetp02);
        addCrImage = findViewById(R.id.btnUploadCrPhoto);
        addVehicleImage = findViewById(R.id.btnUploadVehiclePhoto);
        addPreviousCardImage = findViewById(R.id.btnUploadOldCard);

        Intent intent = getIntent();
        String fullName = intent.getStringExtra("FULL_NAME");
        String address = intent.getStringExtra("ADDRESS");
        String idNumber = intent.getStringExtra("NIC");
        String contactNumber = intent.getStringExtra("CONTACT");
        String customerEmail = intent.getStringExtra("EMAIL");
        int type = intent.getIntExtra("TYPE", 0);
        int vehicleType = intent.getIntExtra("VEHICLE_TYPE", 0);
        String engineNumber = intent.getStringExtra("ENGINE");
        String chassisNumber = intent.getStringExtra("CHASSIS");
        String modal = intent.getStringExtra("MODAL");
        String yearOfMake = intent.getStringExtra("YEAR");
        String regNo = intent.getStringExtra("RENO");
        String color = intent.getStringExtra("COLOR");
        String horsePower = intent.getStringExtra("HORSE_POWER");
        String value = intent.getStringExtra("VALUE");
        String leasingCompany = intent.getStringExtra("LEASING_COMPANY");

        int policyId = getIntent().getIntExtra("POLICY_ID", 0);

        int dealerId = intent.getIntExtra("DEALER", 0);
        String token = intent.getStringExtra("TOKEN");
        String dealerFullname = intent.getStringExtra("FULLNESS");

        if (policyId != 0) {
            globalPolicyId = policyId;
            Log.d(TAG, "policyId " + policyId);
        }

        back.setOnClickListener(v -> {
            Intent intent1 = new Intent(InsurenceInformation.this, VehicleAdditionalInformation.class);
            intent1.putExtra("FULL_NAME", fullName);
            intent1.putExtra("ADDRESS", address);
            intent1.putExtra("NIC", idNumber);
            intent1.putExtra("CONTACT", contactNumber);
            intent1.putExtra("EMAIL", customerEmail);
            intent1.putExtra("TYPE", type);
            intent1.putExtra("VEHICLE_TYPE", vehicleType);

            intent1.putExtra("ENGINE", engineNumber);
            intent1.putExtra("CHASSIS", chassisNumber);
            intent1.putExtra("MODAL", modal);
            intent1.putExtra("YEAR", yearOfMake);
            intent1.putExtra("RENO", regNo);

            intent1.putExtra("COLOR", color);
            intent1.putExtra("HORSE_POWER", horsePower);
            intent1.putExtra("VALUE", value);
            intent1.putExtra("LEASING_COMPANY", leasingCompany);

            intent1.putExtra("DEALER", dealerId);
            intent1.putExtra("TOKEN", token);
            intent1.putExtra("FULLNESS", dealerFullname);
            startActivity(intent1);
        });

        addCrImage.setOnClickListener(v -> {
            if (globalPolicyId != 0) {
                String fieldImage = "cr_image"; // Replace "crimage" with the desired field image

                Intent intent1 = new Intent(InsurenceInformation.this, CRImageUpload.class);
                intent1.putExtra("POLICY_ID", globalPolicyId);
                intent1.putExtra("FIELD", fieldImage);
                intent1.putExtra("DEALER", dealerId);
                intent1.putExtra("TOKEN", token);
                intent1.putExtra("FULLNESS", dealerFullname);
                startActivity(intent1);
                Log.e(TAG, "globalPolicyId " + globalPolicyId);
            } else {

                addPolicy(vehicleType, fullName, address, idNumber, Integer.parseInt(contactNumber), regNo, engineNumber, chassisNumber, modal, Integer.parseInt(yearOfMake), leasingCompany, color, horsePower, value, selectedValue, "", "", "", 0, type, 0, "", customerEmail , dealerId, new PolicyAdditionCallback() {
                    @Override
                    public void onPolicyAdded(int policyId) {
                        if (policyId != -1) {
                            // Policy added successfully
                            String fieldImage = "cr_image"; // Replace "crimage" with the desired field image

                            globalPolicyId = policyId;
                            Intent intent = new Intent(InsurenceInformation.this, CRImageUpload.class);
                            intent.putExtra("POLICY_ID", policyId);
                            intent.putExtra("FIELD", fieldImage);
                            intent.putExtra("DEALER", dealerId);
                            intent.putExtra("TOKEN", token);
                            intent.putExtra("FULLNESS", dealerFullname);

                            startActivity(intent);
                        } else {
                            // Failed to add policy
                            Toast.makeText(InsurenceInformation.this, "Failed to add policy", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });


        addVehicleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (globalPolicyId != 0) {
                String fieldImage = "vehicle_image"; // Replace "crimage" with the desired field image

                Intent intent1 = new Intent(InsurenceInformation.this, CRImageUpload.class);
                intent1.putExtra("POLICY_ID", globalPolicyId);
                intent1.putExtra("FIELD", fieldImage);
                intent1.putExtra("DEALER", dealerId);
                intent1.putExtra("TOKEN", token);
                intent1.putExtra("FULLNESS", dealerFullname);

                startActivity(intent1);
                    Log.e(TAG, "globalPolicyId " + globalPolicyId);
                } else {

                    addPolicy(vehicleType, fullName, address, idNumber, Integer.parseInt(contactNumber), regNo, engineNumber, chassisNumber, modal, Integer.parseInt(yearOfMake), leasingCompany, color, horsePower, value, selectedValue, "", "", "", 0, type, 0, "", customerEmail, dealerId ,new PolicyAdditionCallback() {
                        @Override
                        public void onPolicyAdded(int policyId) {
                            if (policyId != -1) {
                                String fieldImage = "vehicle_image"; // Replace "vehicle_image" with the desired field image

                                globalPolicyId = policyId;
                                Intent intent = new Intent(InsurenceInformation.this, CRImageUpload.class);
                                intent.putExtra("POLICY_ID", policyId);
                                intent.putExtra("FIELD", fieldImage);
                                intent.putExtra("DEALER", dealerId);
                                intent.putExtra("TOKEN", token);
                                intent.putExtra("FULLNESS", dealerFullname);

                                startActivity(intent);
                            } else {
                                // Failed to add policy
                                Toast.makeText(InsurenceInformation.this, "Failed to add policy", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        addPreviousCardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (globalPolicyId != 0) {
                    String fieldImage = "privious_insurence_card_image"; // Replace "privious_insurence_card_image" with the desired field image

                    Intent intent1 = new Intent(InsurenceInformation.this, CRImageUpload.class);
                    intent1.putExtra("POLICY_ID", globalPolicyId);
                    intent1.putExtra("FIELD", fieldImage);
                    intent1.putExtra("DEALER", dealerId);
                    intent1.putExtra("TOKEN", token);
                    intent1.putExtra("FULLNESS", dealerFullname);

                    startActivity(intent1);
                    Log.e(TAG, "globalPolicyId " + globalPolicyId);
                } else {

                    addPolicy(vehicleType, fullName, address, idNumber, Integer.parseInt(contactNumber), regNo, engineNumber, chassisNumber, modal, Integer.parseInt(yearOfMake), leasingCompany, color, horsePower, value, selectedValue, "", "", "", 0, type, 0, "", customerEmail, dealerId , new PolicyAdditionCallback() {
                        @Override
                        public void onPolicyAdded(int policyId) {
                            if (policyId != -1) {
                                String fieldImage = "privious_insurence_card_image"; // Replace "privious_insurence_card_image" with the desired field image

                                globalPolicyId = policyId;
                                Log.e(TAG, "policyId : " + policyId);
                                Intent intent = new Intent(InsurenceInformation.this, CRImageUpload.class);
                                intent.putExtra("POLICY_ID", policyId);
                                intent.putExtra("FIELD", fieldImage);
                                intent.putExtra("DEALER", dealerId);
                                intent.putExtra("TOKEN", token);
                                intent.putExtra("FULLNESS", dealerFullname);

                                startActivity(intent);
                            } else {
                                // Failed to add policy
                                Toast.makeText(InsurenceInformation.this, "Failed to add policy", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        submit.setOnClickListener(v -> {

            if (globalPolicyId != 0) {
                updatePolicyStatus(globalPolicyId, 1);
            } else {

                addPolicy(vehicleType, fullName, address, idNumber, Integer.parseInt(contactNumber), regNo, engineNumber, chassisNumber, modal, Integer.parseInt(yearOfMake), leasingCompany, color, horsePower, value, selectedValue, "", "", "", 0, type, 0, "", customerEmail, dealerId, new PolicyAdditionCallback() {
                    @Override
                    public void onPolicyAdded(int policyId) {
                        if (policyId != -1) {
                            globalPolicyId = policyId;
                            updatePolicyStatus(policyId, 1);

                        } else {
                            // Failed to add policy
                            Toast.makeText(InsurenceInformation.this, "Failed to add policy", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void addPolicy(int vehicle_type, String customer_fullname, String customer_address, String customer_nic, int customer_phone, String vehicle_reg_no, String engine_no, String chassis_no, String modal, int years_of_make, String leasing_company, String vehicle_color, String horse_power, String value_of_vehicle, int use_perpose, String cr_image, String vehicle_image, String previous_insurance_card_image, int policy_price, int policy_type, int policy_status, String policy_start_date, String customer_email, int dealer_id ,PolicyAdditionCallback callback) {

        Policy policy = new Policy(
                vehicle_type,
                customer_fullname,
                customer_address,
                customer_nic,
                customer_phone,
                vehicle_reg_no,
                engine_no,
                chassis_no,
                modal,
                years_of_make,
                leasing_company,
                vehicle_color,
                horse_power,
                value_of_vehicle,
                use_perpose,
                cr_image,
                vehicle_image,
                previous_insurance_card_image,
                policy_price,
                policy_type,
                policy_status,
                policy_start_date,
                customer_email,
                dealer_id
        );

        // Make the API call to add the policy using the policy object
        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ApiResponse> call = api.addNewPolicy(policy);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();

                    boolean success = apiResponse.isSuccess();
                    int policyId = apiResponse.getPolicyId();

                    if (success) {
                        // Show a toast message with the policy ID
                        Toast.makeText(InsurenceInformation.this, "Policy added successfully. Policy ID: " + policyId, Toast.LENGTH_SHORT).show();
                        globalPolicyId = policyId;
                    } else {
                        // Show a toast message indicating failure
                        Toast.makeText(InsurenceInformation.this, "Failed to add policy", Toast.LENGTH_SHORT).show();
                    }

                    // Invoke the callback with the policyId
                    callback.onPolicyAdded(policyId);
                } else {
                    Log.e(TAG, "Failed to parse response JSON");
                    // Invoke the callback with a default value in case of failure
                    callback.onPolicyAdded(-1);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d(TAG, "Policy addition failed");
                // Invoke the callback with a default value in case of failure
                callback.onPolicyAdded(-1);
            }
        });
    }

    private void updatePolicyStatus(int policyId, int newStatus) {
        PolicyStatusUpdate requestBody = new PolicyStatusUpdate(newStatus);

        Methods api = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ApiResponse> call = api.updatePolicyStatus(policyId, requestBody);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    String message = apiResponse.getMessage();

                    Intent intent2 = getIntent();
                    int dealerId = intent2.getIntExtra("DEALER", 0);
                    String token = intent2.getStringExtra("TOKEN");
                    String dealerFullname = intent2.getStringExtra("FULLNESS");


                    if(message != null){
                        Intent intent1 = new Intent(InsurenceInformation.this, Waiting.class);
                        intent1.putExtra("POLICY_ID", policyId);
                        intent1.putExtra("DEALER", dealerId);
                        intent1.putExtra("TOKEN", token);
                        intent1.putExtra("FULLNESS", dealerFullname);
                        startActivity(intent1);
                    }
                } else {
                    // Handle unsuccessful API call
                    Log.e(TAG, "Failed API call: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Handle API call failure
                Log.e(TAG, "API call failed: " + t.getMessage());
            }
        });
    }


    // Define the callback interface
    interface PolicyAdditionCallback {
        void onPolicyAdded(int policyId);
    }




    public void handleCreate(HashMap<String, String> values) {

    }

}