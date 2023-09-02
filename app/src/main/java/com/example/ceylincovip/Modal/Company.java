package com.example.ceylincovip.Modal;

public class Company {
    private int company_id;
    private String company_name;
    private String company_branch;
    private String company_address;
    private String company_phone;
    private String company_email;
    private int status;

    public Company(int company_id, String company_name, String company_branch, String company_address, String company_phone, String company_email, int status) {
        this.company_id = company_id;
        this.company_name = company_name;
        this.company_branch = company_branch;
        this.company_address = company_address;
        this.company_phone = company_phone;
        this.company_email = company_email;
        this.status = status;
    }

    public Company(String company_name, String company_branch, String company_address, String company_phone, String company_email) {
        this.company_name = company_name;
        this.company_branch = company_branch;
        this.company_address = company_address;
        this.company_phone = company_phone;
        this.company_email = company_email;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_branch() {
        return company_branch;
    }

    public void setCompany_branch(String company_branch) {
        this.company_branch = company_branch;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getCompany_phone() {
        return company_phone;
    }

    public void setCompany_phone(String company_phone) {
        this.company_phone = company_phone;
    }

    public String getCompany_email() {
        return company_email;
    }

    public void setCompany_email(String company_email) {
        this.company_email = company_email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
