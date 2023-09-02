package com.example.ceylincovip.Modal;

public class ChangeEmailModal {

    private String currentEmail;
    private String newEmail;

    public ChangeEmailModal(String currentEmail, String newEmail) {
        this.currentEmail = currentEmail;
        this.newEmail = newEmail;
    }

    public String getCurrentEmail() {
        return currentEmail;
    }

    public void setCurrentEmail(String currentEmail) {
        this.currentEmail = currentEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
