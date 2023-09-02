package com.example.ceylincovip.Modal;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("filename")
    private String filename;

    @SerializedName("error")
    private String error;

    @SerializedName("policyId")
    private int policyId;

    @SerializedName("insertedId")
    private int insertedId;

    @SerializedName("dealer_id")
    private int dealer_id;

    @SerializedName("companyId")
    private int companyId;

    @SerializedName("dealer_fullname")
    private String dealer_fullname;

    @SerializedName("token")
    private String token;

    @SerializedName("newtoken")
    private String newtoken;

    @SerializedName("count")
    private int count;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getFilename() {
        return filename;
    }

    public int getPolicyId() {
        return policyId;
    }

    public int getInsertedId() {
        return insertedId;
    }

    public String getNewtoken() {
        return newtoken;
    }

    public int getDealer_id() {
        return dealer_id;
    }

    public String getToken() {
        return token;
    }

    public String getError() {
        return error;
    }
    public int getCount() {
        return count;
    }

    public int getinsertedId() {
        return insertedId;
    }

    public int dealer_id() {
        return dealer_id;
    }

    public int getCompany_id() {
        return companyId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getDealer_fullname() {
        return dealer_fullname;
    }
}
