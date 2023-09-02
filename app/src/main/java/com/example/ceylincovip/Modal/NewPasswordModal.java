package com.example.ceylincovip.Modal;

public class NewPasswordModal {
    String newPassword;
    String token;
    String confirmPassword;

    public NewPasswordModal(String newPassword, String token, String confirmPassword) {
        this.newPassword = newPassword;
        this.token = token;
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
