package com.example.ceylincovip.Modal;

public class Login {
    String dealer_password;
    String dealer_email;

    public Login(String dealer_password, String dealer_email) {
        this.dealer_password = dealer_password;
        this.dealer_email = dealer_email;
    }

    public String getDealer_password() {
        return dealer_password;
    }

    public void setDealer_password(String dealer_password) {
        this.dealer_password = dealer_password;
    }

    public String getDealer_email() {
        return dealer_email;
    }

    public void setDealer_email(String dealer_email) {
        this.dealer_email = dealer_email;
    }
}
