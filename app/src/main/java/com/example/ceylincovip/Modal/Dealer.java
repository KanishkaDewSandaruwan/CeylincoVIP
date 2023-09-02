package com.example.ceylincovip.Modal;

import java.util.Date;

public class Dealer {
    private int dealer_id;
    private String dealer_fullname;
    private String dealer_address;
    private String dealer_nic;
    private String dealer_phone;
    private String dealer_whatsapp_number;
    private String dealer_email;
    private String dealer_password;
    private String pin_number;  // Add this field
    private String reg_date;
    private int company_id;
    private int status;  // Add this field
    private int is_delete;  // Add this field

    public Dealer(int dealer_id, String dealer_fullname, String dealer_address, String dealer_nic, String dealer_phone, String dealer_whatsapp_number, String dealer_email, String dealer_password, int company_id) {
        this.dealer_id = dealer_id;
        this.dealer_fullname = dealer_fullname;
        this.dealer_address = dealer_address;
        this.dealer_nic = dealer_nic;
        this.dealer_phone = dealer_phone;
        this.dealer_whatsapp_number = dealer_whatsapp_number;
        this.dealer_email = dealer_email;
        this.dealer_password = dealer_password;
        this.company_id = company_id;
    }

    public Dealer(String dealer_fullname, String dealer_address, String dealer_nic, String dealer_phone, String dealer_whatsapp_number) {
        this.dealer_fullname = dealer_fullname;
        this.dealer_address = dealer_address;
        this.dealer_nic = dealer_nic;
        this.dealer_phone = dealer_phone;
        this.dealer_whatsapp_number = dealer_whatsapp_number;
    }

    public Dealer(String dealer_fullname, String dealer_address, String dealer_nic, String dealer_phone, String dealer_whatsapp_number, String dealer_email, String dealer_password, int company_id) {
        this.dealer_fullname = dealer_fullname;
        this.dealer_address = dealer_address;
        this.dealer_nic = dealer_nic;
        this.dealer_phone = dealer_phone;
        this.dealer_whatsapp_number = dealer_whatsapp_number;
        this.dealer_email = dealer_email;
        this.dealer_password = dealer_password;
        this.company_id = company_id;
    }

    public Dealer(int dealer_id, String dealer_fullname, String dealer_address, String dealer_nic, String dealer_phone, String dealer_whatsapp_number, String dealer_email, String dealer_password, String pin_number, String reg_date, int company_id, int status, int is_delete) {
        this.dealer_id = dealer_id;
        this.dealer_fullname = dealer_fullname;
        this.dealer_address = dealer_address;
        this.dealer_nic = dealer_nic;
        this.dealer_phone = dealer_phone;
        this.dealer_whatsapp_number = dealer_whatsapp_number;
        this.dealer_email = dealer_email;
        this.dealer_password = dealer_password;
        this.pin_number = pin_number;
        this.reg_date = reg_date;
        this.company_id = company_id;
        this.status = status;
        this.is_delete = is_delete;
    }

    public int getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(int dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getDealer_fullname() {
        return dealer_fullname;
    }

    public void setDealer_fullname(String dealer_fullname) {
        this.dealer_fullname = dealer_fullname;
    }

    public String getDealer_address() {
        return dealer_address;
    }

    public void setDealer_address(String dealer_address) {
        this.dealer_address = dealer_address;
    }

    public String getDealer_nic() {
        return dealer_nic;
    }

    public void setDealer_nic(String dealer_nic) {
        this.dealer_nic = dealer_nic;
    }

    public String getDealer_phone() {
        return dealer_phone;
    }

    public void setDealer_phone(String dealer_phone) {
        this.dealer_phone = dealer_phone;
    }

    public String getDealer_whatsapp_number() {
        return dealer_whatsapp_number;
    }

    public void setDealer_whatsapp_number(String dealer_whatsapp_number) {
        this.dealer_whatsapp_number = dealer_whatsapp_number;
    }

    public String getDealer_email() {
        return dealer_email;
    }

    public void setDealer_email(String dealer_email) {
        this.dealer_email = dealer_email;
    }

    public String getDealer_password() {
        return dealer_password;
    }

    public void setDealer_password(String dealer_password) {
        this.dealer_password = dealer_password;
    }

    public String getPin_number() {
        return pin_number;
    }

    public void setPin_number(String pin_number) {
        this.pin_number = pin_number;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }
}
