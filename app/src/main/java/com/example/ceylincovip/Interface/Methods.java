package com.example.ceylincovip.Interface;

import com.example.ceylincovip.Modal.ApiResponse;
import com.example.ceylincovip.Modal.Bank;
import com.example.ceylincovip.Modal.ChangeEmailModal;
import com.example.ceylincovip.Modal.ChangePasswordModal;
import com.example.ceylincovip.Modal.Commision;
import com.example.ceylincovip.Modal.Company;
import com.example.ceylincovip.Modal.Dealer;
import com.example.ceylincovip.Modal.Edit;
import com.example.ceylincovip.Modal.EmailRequestBody;
import com.example.ceylincovip.Modal.Login;
import com.example.ceylincovip.Modal.NewPasswordModal;
import com.example.ceylincovip.Modal.OTP;
import com.example.ceylincovip.Modal.Policy;
import com.example.ceylincovip.Modal.PolicyStatusUpdate;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Methods {

    @Headers({
            "Accept: application/json",
            "User-Agent: Your-App-Name",
            "Cache-Control: max-age=640000"
    })
    @GET("policy/all")
    Call<List<Policy>> getAllPolicy();

    @GET("policy/latest/{policyId}")
    Call<List<Policy>> getPolicy(@Path("policyId") int policyId);



    @POST("policy/create")
    Call<ApiResponse> addNewPolicy(@Body Policy policy);

    @Multipart
    @PUT("policy/upload/{field}/{policyId}")
    Call<ApiResponse> uploadImage(@Path("field") String field, @Path("policyId") int policyId, @Part MultipartBody.Part file);

    @PUT("policy/appstatus/{policyId}")
    Call<ApiResponse> updatePolicyStatus(@Path("policyId") int policyId, @Body PolicyStatusUpdate requestBody);

    //Dealer API

    @POST("dealer/login")
    Call<ApiResponse> getLogin(@Body Login login);

    @POST("dealer/create")
    Call<ApiResponse> createDealer(@Body Dealer dealer);

    @GET("dealer/all")
    Call<List<Policy>> getAllPolicy(@Header("x-token-dealer") String token);

    @GET("dealer/validate/{field}/{value}")
    Call<ApiResponse> checkDealer(@Path("field") String field, @Path("value") String value);

    //Company API
    @POST("company/create")
    Call<ApiResponse> createCompany(@Body Company company);

    //Reset Password
    @POST("dealer/forget-password")
    Call<ApiResponse> fogetPassword(@Body EmailRequestBody email);

    @POST("dealer/check-otp")
    Call<ApiResponse> checkOTP(@Body OTP otp);

    @POST("dealer/new-password")
    Call<ApiResponse> addPassword(@Body NewPasswordModal newPasswordModal);

    @GET("dealer/me/{dealer_id}")
    Call<List<Dealer>> getProfile(
            @Header("x-token-dealer") String token,
            @Path("dealer_id") int dealer_id
    );

    @PUT("dealer/me/update/{dealer_id}")
    Call<ApiResponse> updateProfile(
            @Header("x-token-dealer") String token,
            @Path("dealer_id") int dealer_id,
            @Body Dealer dealer
    );

    @PUT("dealer/me/changePassword/{dealer_id}")
    Call<ApiResponse> changePassword(
            @Header("x-token-dealer") String token,
            @Path("dealer_id") int dealer_id,
            @Body ChangePasswordModal changePasswordModal
    );

    @PUT("dealer/me/requestemailchange/{dealer_id}")
    Call<ApiResponse> changeEmail(
            @Header("x-token-dealer") String token,
            @Path("dealer_id") int dealer_id,
            @Body ChangeEmailModal changeEmailModal
    );

    @POST("dealer/me/changeEmail")
    Call<ApiResponse> checkEmailOTP(
            @Header("x-token-dealer") String token,
            @Body OTP otp
    );

    @PUT("dealer/me/delete/{dealer_id}")
    Call<ApiResponse> deactiveAccount(
            @Header("x-token-dealer") String token,
            @Path("dealer_id") int dealer_id
    );

    @GET("dealer/commision/{dealer_id}")
    Call<Commision> getCommision(
            @Header("x-token-dealer") String token,
            @Path("dealer_id") int dealer_id
    );

    @GET("bank/dealer/{dealerid}")
    Call<List<Bank>> getDealerBank(
            @Header("x-token-dealer") String token,
            @Path("dealerid") int dealerid
    );

    @POST("bank/create")
    Call<ApiResponse> bankCreate(
            @Header("x-token-dealer") String token,
            @Body Bank bank
    );

    @PUT("bank/update/single/{account_id}/{field}")
    Call<ApiResponse> changeValues(
            @Path("account_id") String accountId,
            @Path("field") String field,
            @Header("x-token-dealer") String token,
            @Body Edit edit
    );


}
