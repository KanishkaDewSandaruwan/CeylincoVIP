package com.example.ceylincovip.Modal;

public class OTP {

    private int otp;
    private String token;
    private int insertedId;
    private int dealer_id;
    private String newEmail;

    public OTP(int otp, String token, int insertedId) {
        this.otp = otp;
        this.token = token;
        this.insertedId = insertedId;
    }

    public OTP(int otp, int insertedId, String newEmail, int dealer_id) {
        this.otp = otp;
        this.insertedId = insertedId;
        this.newEmail = newEmail;
        this.dealer_id = dealer_id;
    }

    public int getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(int dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getInsertedId() {
        return insertedId;
    }

    public void setInsertedId(int insertedId) {
        this.insertedId = insertedId;
    }
}
