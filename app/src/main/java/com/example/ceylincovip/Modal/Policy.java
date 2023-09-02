package com.example.ceylincovip.Modal;

public class Policy {
    int policyId;
    int vehicle_type;
    String customer_fullname;
    String customer_address;
    String customer_nic;
    int customer_phone;
    String vehicle_reg_no;
    String engin_no;
    String chassis_no;
    String model;
    int years_of_make;
    String leasing_company;
    String vehicle_color;
    String horse_power;
    String value_of_vehicle;
    int use_perpose;
    String cr_image;
    String vehicle_image;
    String previous_insurance_card_image;
    int policy_price;
    int policy_status;
    int policy_type;
    String policy_start_date;
    String customer_email;

    int dealer_id;

    public Policy(int policyId, int vehicle_type, String customer_fullname, String customer_address, String customer_nic, int customer_phone, String vehicle_reg_no, String engin_no, String chassis_no, String model, int years_of_make, String leasing_company, String vehicle_color, String horse_power, String value_of_vehicle, int use_perpose, String cr_image, String vehicle_image, String previous_insurance_card_image, int policy_price, int policy_status, int policy_type, String policy_start_date, String customer_email, int dealer_id) {
        this.policyId = policyId;
        this.vehicle_type = vehicle_type;
        this.customer_fullname = customer_fullname;
        this.customer_address = customer_address;
        this.customer_nic = customer_nic;
        this.customer_phone = customer_phone;
        this.vehicle_reg_no = vehicle_reg_no;
        this.engin_no = engin_no;
        this.chassis_no = chassis_no;
        this.model = model;
        this.years_of_make = years_of_make;
        this.leasing_company = leasing_company;
        this.vehicle_color = vehicle_color;
        this.horse_power = horse_power;
        this.value_of_vehicle = value_of_vehicle;
        this.use_perpose = use_perpose;
        this.cr_image = cr_image;
        this.vehicle_image = vehicle_image;
        this.previous_insurance_card_image = previous_insurance_card_image;
        this.policy_price = policy_price;
        this.policy_status = policy_status;
        this.policy_type = policy_type;
        this.policy_start_date = policy_start_date;
        this.customer_email = customer_email;
        this.dealer_id = dealer_id;
    }

    public Policy(int vehicle_type, String customer_fullname, String customer_address, String customer_nic, int customer_phone, String vehicle_reg_no, String engin_no, String chassis_no, String model, int years_of_make, String leasing_company, String vehicle_color, String horse_power, String value_of_vehicle, int use_perpose, String cr_image, String vehicle_image, String previous_insurance_card_image, int policy_price, int policy_status, int policy_type, String customer_email, int dealer_id) {
        this.vehicle_type = vehicle_type;
        this.customer_fullname = customer_fullname;
        this.customer_address = customer_address;
        this.customer_nic = customer_nic;
        this.customer_phone = customer_phone;
        this.vehicle_reg_no = vehicle_reg_no;
        this.engin_no = engin_no;
        this.chassis_no = chassis_no;
        this.model = model;
        this.years_of_make = years_of_make;
        this.leasing_company = leasing_company;
        this.vehicle_color = vehicle_color;
        this.horse_power = horse_power;
        this.value_of_vehicle = value_of_vehicle;
        this.use_perpose = use_perpose;
        this.cr_image = cr_image;
        this.vehicle_image = vehicle_image;
        this.previous_insurance_card_image = previous_insurance_card_image;
        this.policy_price = policy_price;
        this.policy_status = policy_status;
        this.policy_type = policy_type;
        this.customer_email = customer_email;
        this.dealer_id = dealer_id;
    }

    public Policy(int vehicle_type, String customer_fullname, String customer_address, String customer_nic, int customer_phone, String vehicle_reg_no, String engin_no, String chassis_no, String model, int years_of_make, String leasing_company, String vehicle_color, String horse_power, String value_of_vehicle, int use_perpose, String cr_image, String vehicle_image, String previous_insurance_card_image, int policy_price, int policy_type, int policy_status, String policy_start_date, String customer_email, int  dealer_id) {
        this.vehicle_type = vehicle_type;
        this.customer_fullname = customer_fullname;
        this.customer_address = customer_address;
        this.customer_nic = customer_nic;
        this.customer_phone = customer_phone;
        this.vehicle_reg_no = vehicle_reg_no;
        this.engin_no = engin_no;
        this.chassis_no = chassis_no;
        this.model = model;
        this.years_of_make = years_of_make;
        this.leasing_company = leasing_company;
        this.vehicle_color = vehicle_color;
        this.horse_power = horse_power;
        this.value_of_vehicle = value_of_vehicle;
        this.use_perpose = use_perpose;
        this.cr_image = cr_image;
        this.vehicle_image = vehicle_image;
        this.previous_insurance_card_image = previous_insurance_card_image;
        this.policy_price = policy_price;
        this.policy_type = policy_type;
        this.policy_status = policy_status;
        this.policy_start_date = policy_start_date;
        this.customer_email = customer_email;
        this.dealer_id = dealer_id;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public int getPolicy_id() {
        return policyId;
    }

    public void setPolicy_id(int policyId) {
        this.policyId = policyId;
    }

    public int getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(int vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getCustomer_fullname() {
        return customer_fullname;
    }

    public void setCustomer_fullname(String customer_fullname) {
        this.customer_fullname = customer_fullname;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_nic() {
        return customer_nic;
    }

    public void setCustomer_nic(String customer_nic) {
        this.customer_nic = customer_nic;
    }

    public int getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(int customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getVehicle_reg_no() {
        return vehicle_reg_no;
    }

    public void setVehicle_reg_no(String vehicle_reg_no) {
        this.vehicle_reg_no = vehicle_reg_no;
    }

    public String getEngin_no() {
        return engin_no;
    }

    public void setEngin_no(String engin_no) {
        this.engin_no = engin_no;
    }

    public String getChassis_no() {
        return chassis_no;
    }

    public void setChassis_no(String chassis_no) {
        this.chassis_no = chassis_no;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYears_of_make() {
        return years_of_make;
    }

    public void setYears_of_make(int years_of_make) {
        this.years_of_make = years_of_make;
    }

    public String getLeasing_company() {
        return leasing_company;
    }

    public void setLeasing_company(String leasing_company) {
        this.leasing_company = leasing_company;
    }

    public String getVehicle_color() {
        return vehicle_color;
    }

    public void setVehicle_color(String vehicle_color) {
        this.vehicle_color = vehicle_color;
    }

    public String getHorse_power() {
        return horse_power;
    }

    public void setHorse_power(String horse_power) {
        this.horse_power = horse_power;
    }

    public String getValue_of_vehicle() {
        return value_of_vehicle;
    }

    public void setValue_of_vehicle(String value_of_vehicle) {
        this.value_of_vehicle = value_of_vehicle;
    }

    public int getUse_perpose() {
        return use_perpose;
    }

    public void setUse_perpose(int use_perpose) {
        this.use_perpose = use_perpose;
    }

    public String getCr_image() {
        return cr_image;
    }

    public void setCr_image(String cr_image) {
        this.cr_image = cr_image;
    }

    public String getVehicle_image() {
        return vehicle_image;
    }

    public void setVehicle_image(String vehicle_image) {
        this.vehicle_image = vehicle_image;
    }

    public String getPrevious_insurance_card_image() {
        return previous_insurance_card_image;
    }

    public void setPrevious_insurance_card_image(String previous_insurance_card_image) {
        this.previous_insurance_card_image = previous_insurance_card_image;
    }

    public int getPolicy_price() {
        return policy_price;
    }

    public void setPolicy_price(int policy_price) {
        this.policy_price = policy_price;
    }

    public int getPolicy_status() {
        return policy_status;
    }

    public void setPolicy_status(int policy_status) {
        this.policy_status = policy_status;
    }

    public int getPolicy_type() {
        return policy_type;
    }

    public void setPolicy_type(int policy_type) {
        this.policy_type = policy_type;
    }

    public String getPolicy_start_date() {
        return policy_start_date;
    }

    public void setPolicy_start_date(String policy_start_date) {
        this.policy_start_date = policy_start_date;
    }

    public int getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(int dealer_id) {
        this.dealer_id = dealer_id;
    }
}