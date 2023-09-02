package com.example.ceylincovip.Modal;

public class SpeacialModal {
    private String token;
    private int dealer_id;

    public SpeacialModal(String token, int dealer_id) {
        this.token = token;
        this.dealer_id = dealer_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(int dealer_id) {
        this.dealer_id = dealer_id;
    }
}
