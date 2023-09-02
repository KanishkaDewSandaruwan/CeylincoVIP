package com.example.ceylincovip.Modal;

import java.util.Date;

public class Bank {

    private int account_id;
    private String dealerid;
    private String account_name;
    private String account_number;
    private String account_bank;
    private String account_bank_branch;
    private Date trndate;
    private int status;

    public Bank(int account_id, String dealerid, String account_name, String account_number, String account_bank, String account_bank_branch, Date trndate, int status) {
        this.account_id = account_id;
        this.dealerid = dealerid;
        this.account_name = account_name;
        this.account_number = account_number;
        this.account_bank = account_bank;
        this.account_bank_branch = account_bank_branch;
        this.trndate = trndate;
        this.status = status;
    }

    public Bank(String dealerid, String account_name, String account_number, String account_bank, String account_bank_branch) {
        this.dealerid = dealerid;
        this.account_name = account_name;
        this.account_number = account_number;
        this.account_bank = account_bank;
        this.account_bank_branch = account_bank_branch;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getDealerid() {
        return dealerid;
    }

    public void setDealerid(String dealerid) {
        this.dealerid = dealerid;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_bank() {
        return account_bank;
    }

    public void setAccount_bank(String account_bank) {
        this.account_bank = account_bank;
    }

    public String getAccount_bank_branch() {
        return account_bank_branch;
    }

    public void setAccount_bank_branch(String account_bank_branch) {
        this.account_bank_branch = account_bank_branch;
    }

    public Date getTrndate() {
        return trndate;
    }

    public void setTrndate(Date trndate) {
        this.trndate = trndate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
