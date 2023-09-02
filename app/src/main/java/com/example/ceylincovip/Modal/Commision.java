package com.example.ceylincovip.Modal;

public class Commision {

    private String pendingCommision;
    private String paidCommision;


    public Commision(String pendingCommision, String paidCommision) {
        this.pendingCommision = pendingCommision;
        this.paidCommision = paidCommision;
    }

    public String getPendingCommision() {
        return pendingCommision;
    }

    public void setPendingCommision(String pendingCommision) {
        this.pendingCommision = pendingCommision;
    }

    public String getPaidCommision() {
        return paidCommision;
    }

    public void setPaidCommision(String paidCommision) {
        this.paidCommision = paidCommision;
    }
}
